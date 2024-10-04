package io.goose.shooting.service;

import io.goose.shooting.domain.UserFollow;
import java.util.List;

/**
 * 用户关注 服务层
 * 
 * @author goose
 * @date 2020-05-22
 */
public interface IUserFollowService 
{
	/**
     * 查询用户关注信息
     * 
     * @param id 用户关注ID
     * @return 用户关注信息
     */
	public UserFollow selectUserFollowById(Long id);
	
	/**
     * 查询用户关注信息 外键关联
     * 
     * @param id 用户关注ID
     * @return 用户关注信息
     */
	public UserFollow selectUserFollowByIdAssoc(Long id);	
	
	/**
     * 查询所有用户关注列表
     * 
     * @param 
     * @return 用户关注集合
     */
	public List<UserFollow> selectUserFollowAll();		
	
	/**
     * 查询所有用户关注列表  外键关联
     * 
     * @param 
     * @return 用户关注集合
     */
	public List<UserFollow> selectUserFollowAllAssoc();		
	
	/**
     * 查询用户关注列表
     * 
     * @param userFollow 用户关注信息
     * @return 用户关注集合
     */
	public List<UserFollow> selectUserFollowList(UserFollow userFollow);
	
	/**
     * 查询用户关注列表 外键关联
     * 
     * @param userFollow 用户关注信息
     * @return 用户关注集合
     */
	public List<UserFollow> selectUserFollowListAssoc(UserFollow userFollow);	
	
	/**
     * 新增用户关注
     * 
     * @param userFollow 用户关注信息
     * @return 结果
     */
	public int insertUserFollow(UserFollow userFollow);
	
	/**
     * 修改用户关注
     * 
     * @param userFollow 用户关注信息
     * @return 结果
     */
	public int updateUserFollow(UserFollow userFollow);
		
	/**
     * 删除用户关注信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserFollowByIds(String ids);
	
	/**
     * 删除用户关注信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserFollowById(Long id);
	

  
}
