package io.goose.api.controller.shooting;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.UserPayload;
import io.goose.api.utils.MobileUtils;
import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;
import io.goose.common.enums.ClientUserStatus;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.page.TableDataInfo;
import io.goose.common.support.Convert;
import io.goose.common.utils.StringUtils;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 客户端用户 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/client/clientUser")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "客户端用户 ", description = "客户端用户")
public class ClientUserRestController extends ClubRestBaseController {

    private static final Logger log = LoggerFactory.getLogger(ClientUserRestController.class);

    // TODO - should move the constants to config file
    private static final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private static final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    // 极光API
    private final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private IClubService clubService;

    @Autowired
    private IContestService contestService;

    @Autowired
    private ITrainingService trainingService;

    @Autowired
    private IRecommendService recommendService;

    @Autowired
    private IRecommendCoachService recommendCoachService;

    @Autowired
    private IRecommendJudgeService recommendJudgeService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private Global global;

    @Autowired
    private FileUploadDownloadOSSService fileUploadService;

    /**
     * 查询客户端用户列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询客户端用户列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = false, dataType = "ClientUser")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody ClientUser clientUser) {
        startPage();

        List<ClientUser> list = clientUserService.selectClientUserList(clientUser);
        return getDataTable(list);
    }


    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ClientUser getById(@PathVariable("id") Long id) {
        ClientUser user = clientUserService.selectClientUserById(id);
        setUserRoles(user);
        return user;
    }

    private void setUserRoles(ClientUser user) {
        if (!StringUtils.isBlank(user.getRoleIds())) {
            String[] roleIds = StringUtils.split(user.getRoleIds(), ",");
            for (String roleId : roleIds) {
                try {
                    Role role = roleService.selectRoleById(Integer.parseInt(roleId));
                    if (role != null) {
                        if (user.getRoles() == null) {
                            List<Role> roles = new LinkedList<>();
                            roles.add(role);
                            user.setRoles(roles);
                        } else {
                            user.getRoles().add(role);
                        }
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
    }

    /**
     * 导出客户端用户列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出客户端用户列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "clientUser", value = "客户端用户", required = false,
     * dataType = "ClientUser") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * export(ClientUser clientUser) { List<ClientUser> list =
     * clientUserService.selectClientUserList(clientUser); ExcelUtil<ClientUser> util = new
     * ExcelUtil<ClientUser>(ClientUser.class); return util.exportExcel(list, "clientUser"); }
     */

    /**
     * 导入客户端用户列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入客户端用户列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "客户端用户文件", required = false,
     * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<ClientUser> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(),
     * ClientUser.class); for(ClientUser obj : list) { clientUserService.insertClientUser(obj); } }
     * catch(ExcelUtilException | IOException e) { return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存客户端用户
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存客户端用户")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = true, dataType = "ClientUser")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody ClientUser clientUser) {
        if (clientUser.getUserName() == null) {
            clientUser.setUserName(clientUser.getPhone());
        }

        if (clientUser.getJgUsername() == null) {
            clientUser.setJgUsername(clientUser.getUserName());
        }
        clientUser = setClubId(clientUser);
        clientUserService.insertClientUser(clientUser);

        if (clientUser.getId() != null) {
            // 添加用户到极光
            try {
                RegisterInfo registerInfo = RegisterInfo.newBuilder()
                        .setUsername(clientUser.getJgUsername())
                        .setPassword(clientUser.getJgUsername())
                        .setNickname(clientUser.getNickname())
                        .setAvatar(clientUser.getAvatar())
                        .setAddress(clientUser.getAddress())
                        .addExtra("phone", clientUser.getPhone())
                        .addExtra("avatar", clientUser.getAvatar())
                        .build();

                JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
                log.debug("***【极光】*** 添加用户到极光成功: " + registerInfo);
            } catch (Exception e) {
                log.error("***【极光】*** 添加用户到极光失败!");
                log.error("***【极光】*** " + e.getMessage());
            }

            return AjaxResult.success("成功添加用户：" + clientUser.getUserName());
        } else {
            return AjaxResult.error("添加用户失败！");
        }
    }


    /**
     * 修改保存客户端用户
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存客户端用户")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = true, dataType = "ClientUser")})
    public AjaxResult edit(@RequestBody ClientUser clientUser) {

        if (clientUser.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            clientUser.setPassword(encoder.encode(clientUser.getPassword()));
        }
        int result = clientUserService.updateClientUser(clientUser);

        if (result == 1) {
            // 更新用户到极光
            ClientUser user = clientUserService.selectClientUserById(clientUser.getId());
            try {
                UserPayload userPayload = UserPayload.newBuilder()
                        .setNickname(user.getNickname())
                        .setAvatar(user.getAvatar() != null ? user.getAvatar() : "")
                        .setAddress(user.getAddress() != null ? user.getAddress() : "")
                        .setRegion(user.getCity() != null ? user.getCity() : "")
                        .addExtra("phone", user.getPhone())
                        .addExtra("avatar", user.getAvatar() != null ? user.getAvatar() : "")
                        .build();
                JMSG_CLIENT.updateUserInfo(user.getJgUsername(), userPayload);
                log.debug("***【极光】*** 更新极光用户信息成功: " + userPayload);
            } catch (APIConnectionException e) {
                log.error("***【极光】*** 更新极光用户信息失败." + user.getJgUsername());
                e.printStackTrace();
            } catch (APIRequestException e) {
                log.error("***【极光】*** 更新极光用户信息失败." + user.getJgUsername());
                log.error("***【极光】*** Error Code: " + e.getErrorCode() +
                        " Error Message: " + e.getErrorMessage() +
                        " Status: " + e.getStatus());
                e.printStackTrace();
            }

            return AjaxResult.success("更新用户成功。");
        } else {
            return AjaxResult.error("更新用户失败！");
        }
    }


    /**
     * 删除客户端用户
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除客户端用户")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        // 从极光中删除用户
        String[] idArr = Convert.toStrArray(ids);
        Arrays.stream(idArr).forEach(id -> {
            ClientUser user = clientUserService.selectClientUserById(Long.parseLong(id));

            if (user != null) {
                try {
                    JMSG_CLIENT.deleteUser(user.getJgUsername());
                    log.debug("***【极光】*** 删除极光用户成功");
                } catch (Exception e) {
                    log.error("***【极光】*** 删除极光用户失败。");
                    log.error("***【极光】*** " + e.getMessage());
                }
            }
        });

        return toAjax(clientUserService.deleteClientUserByIds(ids));
    }


    /**
     * 根据id删除客户端用户
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除客户端用户")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        // 从极光中删除用户
        ClientUser user = clientUserService.selectClientUserById(id);

        if (user != null) {
            try {
                JMSG_CLIENT.deleteUser(user.getUserName());
                log.debug("***【极光】*** 删除极光用户成功");
            } catch (Exception e) {
                log.error("***【极光】*** 删除极光用户失败。");
                log.error("***【极光】*** " + e.getMessage());
            }
        }

        return toAjax(clientUserService.deleteClientUserById(id));
    }


    /**
     * 查询客户端用户分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询客户端用户分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = true, dataType = "ClientUser")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody ClientUser clientUser) {
        startPage(clientUser.getPd());
        List<ClientUser> list = clientUserService.selectClientUserList(clientUser);
        return getDataTable(list);
    }


    /**
     * 检查UserName惟一性
     */
    @PostMapping("/checkUserNameUnique")
    @ApiOperation(value = "检查UserName惟一性")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = true, dataType = "ClientUser")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String checkUserNameUnique(@RequestBody ClientUser clientUser) {
        return clientUserService.checkUserNameUnique(clientUser);
    }

    /**
     * 根据手机号或用户名查询用户
     */
    @GetMapping("/getByPhoneOrUsername/{searchParam}")
    @ApiOperation(value = "根据手机号或用户名查询用户")
    public AjaxResult getByIdOrUsername(@PathVariable("searchParam") String searchParam) {
        AjaxResult result = new AjaxResult();
        List<ClientUser> clientUsers = new ArrayList<>();

        ClientUser clientUser = clientUserService.selectClientUserByPhone(searchParam);

        if (clientUser == null) {
            clientUsers = clientUserService.selectClientUserByRealName(searchParam);
        } else {
            clientUsers.add(clientUser);
        }

        if (clientUsers.size() == 0) {
            return AjaxResult.error();
        } else {
            clientUsers.forEach(u -> setUserRoles(u));
            result.put("code", 0);
            result.put("data", clientUsers.get(0));
            return result;
        }
    }

    @PostMapping("/search")
    public AjaxResult searchByParam(@RequestBody String searchParam) {
        AjaxResult result = new AjaxResult();

        String param = searchParam.replaceAll("\"", "");

        List<Club> clubs = clubService.selectClubAllAssoc()
                .stream()
                .filter(c -> c.getTitle().contains(param))
                .collect(Collectors.toList());

        List<Training> trainings = trainingService.selectTrainingAllAssoc()
                .stream()
                .filter(t -> t.getTitle().contains(param))
                .collect(Collectors.toList());

        List<Contest> contests = contestService.selectContestAllAssoc()
                .stream()
                .filter(c -> c.getTitle().contains(param))
                .collect(Collectors.toList());

        Optional<Long> userId = getUserIdFromHeader();
        List<Recommend> recommends = new LinkedList<>();
        if (userId.isPresent()) {
            recommends = recommendService.selectRecommendSummaryList(userId.get())
                    .stream()
                    .filter(r -> r.getTitle().contains(param))
                    .collect(Collectors.toList());
        }

        List<RecommendCoach> recommendCoaches = recommendCoachService.selectRecommendCoachAll()
                .stream()
                .filter(rc -> rc.getTitle().contains(param))
                .collect(Collectors.toList());

        List<RecommendJudge> recommendJudges = recommendJudgeService.selectRecommendJudgeAll()
                .stream()
                .filter(rj -> rj.getTitle().contains(param))
                .collect(Collectors.toList());

        List<Post> posts = new LinkedList<>();
        if (userId.isPresent()) {
            posts = postService.selectPostSummaryList(userId.get())
                    .stream()
                    .filter(p -> p.getTitle() != null ? p.getTitle().contains(param) : (p.getContent() != null ? p.getContent().contains(param) : false))
                    .collect(Collectors.toList());
        }

        result.put("code", 0);
        result.put("clubs", clubs);
        result.put("trainings", trainings);
        result.put("contests", contests);
        result.put("recommends", recommends);
        result.put("coachs", recommendCoaches);
        result.put("judges", recommendJudges);
        result.put("posts", posts);

        return result;
    }

    /**
     * 上传身份证照片
     */
    @PostMapping("/uploadId")
    @ApiOperation(value = "上传身份证信息")
    public AjaxResult uploadId(
            @RequestParam("idFront") MultipartFile idFront,
            @RequestParam("idBack") MultipartFile idBack,
            @RequestParam("userId") Long userId,
            @RequestParam("realName") String realName,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("age") Integer age,
            @RequestParam("city") String city,
            @RequestParam("memberId") String memberId) {
        log.debug("【上传身份证信息】 开始...");

        if (idFront == null) {
            return AjaxResult.error("缺少身份证正面照");
        }

        if (idBack == null) {
            return AjaxResult.error("缺少身份证背面照");
        }

        if (StringUtils.isBlank(realName)) {
            return AjaxResult.error("缺少真实姓名信息");
        }

        if (StringUtils.isBlank(idNumber)) {
            return AjaxResult.error("缺少身份证号码信息");
        }
        Integer num = clientUserService.selectClientUserCountByMemberId(memberId);
        if (num > 0) {
            return AjaxResult.error("射手号已存在");
        }
        ClientUser user = clientUserService.selectClientUserById(userId);

        if (user != null) {
            String filePath = global.getProfile() + userId + "/";

            // 如果已经存在文件，删除
            log.debug("删除已存在证件照");
            File profileFolder = new File(global.getProfile());
            File personalFolder = new File(filePath);
            File[] fileList = personalFolder.listFiles();
            if (fileList != null) {
                for (File existingFile : fileList) {
                    new File(existingFile.toURI()).delete();
                }
            }

            // 保存上传图片
            try {
                log.debug("正在保存新上传证件照...");

                log.debug("检查系统profile文件夹 " + profileFolder.getAbsolutePath() + " 是否存在...");
                if (!profileFolder.exists()) {
                    log.debug("创建profile文件夹...");
                    profileFolder.mkdirs();
                }

                log.debug("检查用户个人目录 " + personalFolder.getAbsolutePath() + " 是否存在...");
                if (!personalFolder.exists()) {
                    log.debug("创建个人文件夹...");
                    personalFolder.mkdirs();
                }

                log.debug("正在上传正面照...");
                String idFrontFileName = FileUploadUtils.uploadMediaFile(filePath, idFront);
                user.setIdFront(idFrontFileName);
                log.debug("正面照上传完成: " + idFrontFileName);

                log.debug("正在上传背面照...");
                String idBackFileName = FileUploadUtils.uploadMediaFile(filePath, idBack);
                user.setIdBack(idBackFileName);
                log.debug("背面照上传完成: " + idBackFileName);

            } catch (Exception e) {
                log.error("图像上传失败", e);
                return AjaxResult.error("证件上传遇到错误");
            }

            log.debug("更新用户数据...");
            user.setRealName(realName);
            user.setIdNumber(idNumber);
            user.setStatus(ClientUserStatus.IN_REVIEW.getCode());
            clientUserService.updateClientUser(user);

            log.debug("【上传身份证信息】 完成");
            return AjaxResult.success("认证信息上传成功");
        } else {
            log.debug("错误，用户不存在");
            return AjaxResult.error("用户不存在");
        }
    }

    /**
     * 上传头像
     */
    @PostMapping("/uploadAvatar")
    @ApiOperation(value = "上传头像")
    public AjaxResult uploadAvatar(
            @RequestParam("avatar") MultipartFile avatar,
            @RequestParam("userId") Long userId) {
        log.debug("【上传头像】 开始...");

        if (avatar == null) {
            return AjaxResult.error("缺少头像照片");
        }

        ClientUser user = clientUserService.selectClientUserById(userId);

        if (user != null) {
            // 如果已经存在文件，删除
//            if (user.getAvatar() != null) {
//                log.debug("删除已存在头像");
//                File existingAvatarFile = new File(filePath + user.getAvatar());
//                if (existingAvatarFile.exists()) {
//                    existingAvatarFile.delete();
//                }
//            }

            // 保存上传图片
            String avatarFileName;
            try {
                log.debug("正在保存新上传头像...");
                avatarFileName = fileUploadService.upload(avatar, null, UploadTypeEnums.ClientUser.getValue(), true);
                user.setAvatar(avatarFileName);
                log.debug("头像上传完成: " + avatarFileName);
            } catch (Exception e) {
                log.error("头像上传失败", e);
                return AjaxResult.error("头像上传遇到错误");
            }

            log.debug("更新用户数据...");
            clientUserService.updateClientUser(user);

            // 更新用户到极光
            try {
                UserPayload userPayload = UserPayload.newBuilder()
                        .setNickname(user.getNickname())
                        .setAvatar(user.getAvatar() != null ? user.getAvatar() : "")
                        .setAddress(user.getAddress() != null ? user.getAddress() : "")
                        .setRegion(user.getCity() != null ? user.getCity() : "")
                        .addExtra("phone", user.getPhone())
                        .addExtra("avatar", user.getAvatar() != null ? user.getAvatar() : "")
                        .build();
                JMSG_CLIENT.updateUserInfo(user.getUserName(), userPayload);
                log.debug("***【极光】*** 更新极光用户信息成功: " + userPayload);
            } catch (Exception e) {
                log.error("***【极光】*** 更新极光用户信息失败: " + user.getUserName());
                log.error("***【极光】*** " + e.getMessage());
            }

            log.debug("【上传头像】 完成");
            return AjaxResult.success("头像上传成功").put("avatar", avatarFileName);
        } else {
            log.debug("错误，用户不存在");
            return AjaxResult.error("用户不存在");
        }
    }


    /**
     * 境外用户修改密码
     */
    @PostMapping("/editPassword")
    @ApiOperation(value = "境外用户修改密码")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clientUser",
            value = "客户端用户", required = true, dataType = "ClientUser")})
    public AjaxResult editPassword(@RequestBody ClientUser clientUser) {
        ClientUser user = clientUserService.selectClientUserById(clientUser.getId());
        if (MobileUtils.isMobileNO(user.getPhone())) {
            return AjaxResult.error("该用户不是境外用户。");
        }
        if (clientUser.getPassword() != null && user.getPassword() != null && !user.getPassword().equals("undefined")) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(clientUser.getPassword(), user.getPassword())) {
                return AjaxResult.error("旧密码不匹配，请检查输入是否正确。");
            }
        }
        if (clientUser.getNewPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            clientUser.setPassword(encoder.encode(clientUser.getNewPassword()));
        }
        int result = clientUserService.updateClientUser(clientUser);

        if (result == 1) {
            try {
                UserPayload userPayload = UserPayload.newBuilder()
                        .setNickname(user.getNickname())
                        .setAvatar(user.getAvatar() != null ? user.getAvatar() : "")
                        .setAddress(user.getAddress() != null ? user.getAddress() : "")
                        .setRegion(user.getCity() != null ? user.getCity() : "")
                        .addExtra("phone", user.getPhone())
                        .addExtra("avatar", user.getAvatar() != null ? user.getAvatar() : "")
                        .build();
                JMSG_CLIENT.updateUserInfo(user.getJgUsername(), userPayload);
                log.debug("***【极光】*** 更新极光用户信息成功: " + userPayload);
            } catch (APIConnectionException e) {
                log.error("***【极光】*** 更新极光用户信息失败." + user.getJgUsername());
                e.printStackTrace();
            } catch (APIRequestException e) {
                log.error("***【极光】*** 更新极光用户信息失败." + user.getJgUsername());
                log.error("***【极光】*** Error Code: " + e.getErrorCode() +
                        " Error Message: " + e.getErrorMessage() +
                        " Status: " + e.getStatus());
                e.printStackTrace();
            }

            return AjaxResult.success("更新用户成功。");
        } else {
            return AjaxResult.error("更新用户失败！");
        }
    }
}
