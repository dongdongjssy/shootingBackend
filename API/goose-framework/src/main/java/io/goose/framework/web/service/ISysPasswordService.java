package io.goose.framework.web.service;

import io.goose.system.domain.SysUser;

public interface ISysPasswordService {
	
	public void validate(SysUser user, String password);
	
	public boolean matches(SysUser user, String newPassword);
	
	public String encryptPassword(String username, String password, String salt);

}
