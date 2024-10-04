package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.UserReadMapper;
import io.goose.shooting.domain.UserRead;

import io.goose.shooting.service.IUserReadService;
import io.goose.common.support.Convert;

/**
 * 用户阅读 服务层实现
 * 
 * @author goose
 * @date 2020-05-28
 */
@Service
public class UserReadServiceImpl implements IUserReadService 
{
	@Autowired
	private UserReadMapper userReadMapper;


	/**
     * 查询用户阅读信息
     * 
     * @param id 用户阅读ID
     * @return 用户阅读信息
     */
    @Override
	public UserRead selectUserReadById(Long id)
	{
	    return userReadMapper.selectUserReadById(id);
	}
	
	/**
     * 查询用户阅读信息 外键关联
     * 
     * @param id 用户阅读ID
     * @return 用户阅读信息
     */
    @Override
	public UserRead selectUserReadByIdAssoc(Long id)
	{
	    return userReadMapper.selectUserReadByIdAssoc(id);
	}	
	
	/**
     * 查询所有用户阅读列表
     * 
     * @param 
     * @return 用户阅读集合
     */
	@Override 
	public List<UserRead> selectUserReadAll()
	{
		return userReadMapper.selectUserReadAll();
	}	
	
	/**
     * 查询所有用户阅读列表 外键关联
     * 
     * @param 
     * @return 用户阅读集合
     */
	@Override 
	public List<UserRead> selectUserReadAllAssoc()
	{
		return userReadMapper.selectUserReadAllAssoc();
	}		
	
	/**
     * 查询用户阅读列表
     * 
     * @param userRead 用户阅读信息
     * @return 用户阅读集合
     */
	@Override
	public List<UserRead> selectUserReadList(UserRead userRead)
	{
	    return userReadMapper.selectUserReadList(userRead);
	}
	
	/**
     * 查询用户阅读列表 外键关联
     * 
     * @param userRead 用户阅读信息
     * @return 用户阅读集合
     */
	@Override
	public List<UserRead> selectUserReadListAssoc(UserRead userRead)
	{
	    return userReadMapper.selectUserReadListAssoc(userRead);
	}	
	
    /**
     * 新增用户阅读
     * 
     * @param userRead 用户阅读信息
     * @return 结果
     */
	@Override
	public int insertUserRead(UserRead userRead)
	{
	    return userReadMapper.insertUserRead(userRead);
	}
	
	/**
     * 修改用户阅读
     * 
     * @param userRead 用户阅读信息
     * @return 结果
     */
	@Override
	public int updateUserRead(UserRead userRead)
	{
	    return userReadMapper.updateUserRead(userRead);
	}

	/**
     * 删除用户阅读对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserReadByIds(String ids)
	{
		return userReadMapper.deleteUserReadByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除用户阅读对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserReadById(Long id)
	{
		return userReadMapper.deleteUserReadById(id);
	}
	
	



}
