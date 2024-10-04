package io.goose.cloud.gateway.security.controller;

import cn.jmessage.api.JMessageClient;
import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.domain.ResultJson;
import io.goose.cloud.gateway.security.domain.auth.ResponseUserToken;
import io.goose.cloud.gateway.security.domain.auth.User;
import io.goose.cloud.gateway.security.repo.UserRepository;
import io.goose.cloud.gateway.security.service.AuthService;
import io.goose.cloud.gateway.security.utils.JwtConfig;
import io.goose.common.base.AjaxResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shooting/auth")
@Transactional
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Resource
    private JwtConfig jwtCfg;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/loginByPhone")
    @ApiOperation(value = "使用手机号和验证码登录")
    public ResultJson<ResponseUserToken> loginByPhone(@Valid @RequestBody User user) {
        log.info("[Shooting API Call] login by phone and sms code");

        if (!StringUtils.isBlank(user.getUserType()) && "mainland".equals(user.getUserType())) {
            final ResponseUserToken response = authService.loginBySmsCode(user.getPhone(), user.getSmsCode());
            log.info("[Shooting API Call End] login by phone and sms code");
            return ResultJson.ok(response);
        } else {
            final ResponseUserToken response = authService.loginByPassword(user.getPhone(), user.getPassword());
            log.info("[Shooting API Call End] login by phone and password");
            return ResultJson.ok(response);
        }
    }


    @PostMapping(value = {"/update/status"})
    @ApiOperation(value = "update status", notes = "")
    public ResultJson updateStatus(@Valid @RequestBody User user) {
        if (StringUtils.isBlank(user.getUserName())) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "Missing user name");
        }
        if (user.getStatus() == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "Invalid status");
        }

        User clientUser = userRepo.findByUserName(user.getUserName());
        if (clientUser == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "User doesn't exist");
        }

        clientUser.setStatus(user.getStatus());
        userRepo.save(clientUser);
        return ResultJson.ok();
    }



    @PostMapping(value = {"/latestStatus"})
    @ApiOperation(value = "latest status", notes = "")
    public ResultJson getLatestStatus(@Valid @RequestBody User user) {
        if (StringUtils.isBlank(user.getUserName())) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "Missing user name");
        }
        User clientUser = userRepo.findByUserName(user.getUserName());
        if (clientUser == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "User doesn't exist");
        }
        return ResultJson.ok(clientUser.getStatus());
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "登出", notes = "退出登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public ResultJson logout(HttpServletRequest request) {
        String token = request.getHeader(jwtCfg.getHeader());
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        authService.logout(token);
        return ResultJson.ok();
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "根据token获取用户信息", notes = "根据token获取用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public ResultJson getUser(HttpServletRequest request) {
        User user = authService.getUserByToken(request.getHeader(jwtCfg.getHeader()));
        return ResultJson.ok(user);
    }

    @PostMapping(value = "/validate")
    @ApiOperation(value = "调用API是验证是否token有效", notes = "调用API是验证是否token有效")
    public ResultJson validateToken(@RequestBody(required = true) String token) throws Exception {

        if (token.startsWith("\"") && token.endsWith("\"")) {
            token = token.substring(1, token.length() - 1);
        }
        System.out.println(token);
        User user = null;
        if (StringUtils.isNotBlank(token)) {
            user = authService.getUserByToken(token);
        }
        return user != null ? ResultJson.ok() : ResultJson.failure(ResultCode.UNAUTHORIZED);
    }

    /**
     * 更新密码
     */
    @PostMapping("/updatePassword")
    @ApiOperation(value = "更新密码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = true, dataType = "User")})
    public ResultJson validatePassword(@RequestBody User clientUser) {
        Optional<User> existingUser = userRepo.findById(clientUser.getId());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(clientUser.getPassword(), user.getPassword())) {
                return ResultJson.failure(ResultCode.EXCEPTION_LOGIN_ERROR);
            } else {
                user.setPassword(encoder.encode(clientUser.getSmsCode()));
                try {
                    userRepo.save(user);
                } catch (Exception e) {
                    return ResultJson.failure(ResultCode.SERVER_ERROR);
                }
                return ResultJson.ok();
            }
        } else {
            return ResultJson.failure(ResultCode.EXCEPTION_USER_NOT_EXISTS);
        }
    }

    @GetMapping(value = "/refresh")
    @ApiOperation(value = "刷新token")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public ResultJson refresh(HttpServletRequest request) {
        String token = request.getHeader(jwtCfg.getHeader());
        String refreshToken = request.getHeader(jwtCfg.getRefreshTokenHeader());
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED, "missing access token");
        }
        if (refreshToken == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "missing refresh token");
        }

        ResponseUserToken response = authService.refresh(token, refreshToken);
        if (response == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "invalid or expired token");
        } else {
            return ResultJson.ok(response);
        }
    }

    //    @PostMapping(value = "/update")
    //    @ApiOperation(value = "修改用户信息")
    //    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    //    public ResultJson update(HttpServletRequest request, @RequestBody User user) {
    //        User theUser = authService.getUserByToken(request.getHeader(jwtCfg.getHeader()));
    //        if (!theUser.getUserName().equals(user.getUserName())) {
    //            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "用户名不匹配，禁止修改"));
    //        }
    //
    //        return ResultJson.ok(authService.update(user));
    //    }
}
