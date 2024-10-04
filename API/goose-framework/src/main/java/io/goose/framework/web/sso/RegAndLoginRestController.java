package io.goose.framework.web.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.framework.shiro.service.SysLoginService;
import io.goose.framework.shiro.service.SysPasswordService;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.service.ISysPasswordService;
import io.goose.system.domain.SysUser;
import io.goose.system.service.ISysUserService;

@RestController
@RequestMapping("/system")
public class RegAndLoginRestController extends BaseController{
	
	@Autowired
	private SysLoginService sysLoginService;
	
	@Autowired
	private ISysUserService sysuserservice;
	
	@Autowired
    private ISysPasswordService passwordService;
	
	@PostMapping("/login")
	public SysUser login(@RequestParam String username, @RequestParam String password)
	{
		SysUser user=sysLoginService.login(username, password);
	    return user;
	}
	@PostMapping("/register")
	public SysUser addSave(SysUser user)
    {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(user.getLoginName());
        toAjax(sysuserservice.insertUser(user));
        return user;
    }
}
