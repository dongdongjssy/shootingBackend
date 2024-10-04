package io.goose.shooting.mapper;

import io.goose.shooting.domain.UserRead;
import java.util.List;	

/**
 * 用户阅读 数据层
 * 
 * @author goose
 * @date 2020-05-28
 */
public interface UserReadMapper 
{
	/**
     * 查询用户阅读信息
     * 
     * @param id 用户阅读ID
     * @return 用户阅读信息
     */
	public UserRead selectUserReadById(Long id);
	
	/**
     * 查询用户阅读信息 外键关联
     * 
     * @param id 用户阅读ID
     * @return 用户阅读信息
     */
	public UserRead selectUserReadByIdAssoc(Long id);	

	/**
     * 查询所有用户阅读列表
     * 
     * @param 
     * @return 用户阅读集合
     */
	public List<UserRead> selectUserReadAll();	
	
	/**
     * 查询所有用户阅读列表 外键关联
     * 
     * @param 
     * @return 用户阅读集合
     */
	public List<UserRead> selectUserReadAllAssoc();		

	
	/**
     * 查询用户阅读列表
     * 
     * @param userRead 用户阅读信息
     * @return 用户阅读集合
     */
	public List<UserRead> selectUserReadList(UserRead userRead);
	
	/**
     * 查询用户阅读列表 外键关联
     * 
     * @param userRead 用户阅读信息
     * @return 用户阅读集合
     */
	public List<UserRead> selectUserReadListAssoc(UserRead userRead);	
	
	/**
     * 新增用户阅读
     * 
     * @param userRead 用户阅读信息
     * @return 结果
     */
	public int insertUserRead(UserRead userRead);
	
	/**
     * 修改用户阅读
     * 
     * @param userRead 用户阅读信息
     * @return 结果
     */
	public int updateUserRead(UserRead userRead);
	
	/**
     * 删除用户阅读
     * 
     * @param id 用户阅读ID
     * @return 结果
     */
	public int deleteUserReadById(Long id);
	
	/**
     * 批量删除用户阅读
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserReadByIds(String[] ids);
	
}