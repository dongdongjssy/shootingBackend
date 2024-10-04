package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.UserFollowMapper;
import io.goose.shooting.domain.UserFollow;

import io.goose.shooting.service.IUserFollowService;
import io.goose.common.support.Convert;

/**
 * 用户关注 服务层实现
 * 
 * @author goose
 * @date 2020-05-22
 */
@Service
public class UserFollowServiceImpl implements IUserFollowService 
{
	@Autowired
	private UserFollowMapper userFollowMapper;


	/**
     * 查询用户关注信息
     * 
     * @param id 用户关注ID
     * @return 用户关注信息
     */
    @Override
	public UserFollow selectUserFollowById(Long id)
	{
	    return userFollowMapper.selectUserFollowById(id);
	}
	
	/**
     * 查询用户关注信息 外键关联
     * 
     * @param id 用户关注ID
     * @return 用户关注信息
     */
    @Override
	public UserFollow selectUserFollowByIdAssoc(Long id)
	{
	    return userFollowMapper.selectUserFollowByIdAssoc(id);
	}	
	
	/**
     * 查询所有用户关注列表
     * 
     * @param 
     * @return 用户关注集合
     */
	@Override 
	public List<UserFollow> selectUserFollowAll()
	{
		return userFollowMapper.selectUserFollowAll();
	}	
	
	/**
     * 查询所有用户关注列表 外键关联
     * 
     * @param 
     * @return 用户关注集合
     */
	@Override 
	public List<UserFollow> selectUserFollowAllAssoc()
	{
		return userFollowMapper.selectUserFollowAllAssoc();
	}		
	
	/**
     * 查询用户关注列表
     * 
     * @param userFollow 用户关注信息
     * @return 用户关注集合
     */
	@Override
	public List<UserFollow> selectUserFollowList(UserFollow userFollow)
	{
	    return userFollowMapper.selectUserFollowList(userFollow);
	}
	
	/**
     * 查询用户关注列表 外键关联
     * 
     * @param userFollow 用户关注信息
     * @return 用户关注集合
     */
	@Override
	public List<UserFollow> selectUserFollowListAssoc(UserFollow userFollow)
	{
	    return userFollowMapper.selectUserFollowListAssoc(userFollow);
	}	
	
    /**
     * 新增用户关注
     * 
     * @param userFollow 用户关注信息
     * @return 结果
     */
	@Override
	public int insertUserFollow(UserFollow userFollow)
	{
	    return userFollowMapper.insertUserFollow(userFollow);
	}
	
	/**
     * 修改用户关注
     * 
     * @param userFollow 用户关注信息
     * @return 结果
     */
	@Override
	public int updateUserFollow(UserFollow userFollow)
	{
	    return userFollowMapper.updateUserFollow(userFollow);
	}

	/**
     * 删除用户关注对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserFollowByIds(String ids)
	{
		return userFollowMapper.deleteUserFollowByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除用户关注对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserFollowById(Long id)
	{
		return userFollowMapper.deleteUserFollowById(id);
	}
	
	



}
