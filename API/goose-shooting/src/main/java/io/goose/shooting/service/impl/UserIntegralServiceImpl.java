package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.UserIntegralMapper;
import io.goose.shooting.domain.UserIntegral;

import io.goose.shooting.service.IUserIntegralService;
import io.goose.common.support.Convert;

/**
 * 用户电子积分卡 服务层实现
 * 
 * @author goose
 * @date 2021-03-31
 */
@Service
public class UserIntegralServiceImpl implements IUserIntegralService 
{
	@Autowired
	private UserIntegralMapper userIntegralMapper;


	/**
     * 查询用户电子积分卡信息
     * 
     * @param id 用户电子积分卡ID
     * @return 用户电子积分卡信息
     */
    @Override
	public UserIntegral selectUserIntegralById(Long id)
	{
	    return userIntegralMapper.selectUserIntegralById(id);
	}
	
	/**
     * 查询用户电子积分卡信息 外键关联
     * 
     * @param id 用户电子积分卡ID
     * @return 用户电子积分卡信息
     */
    @Override
	public UserIntegral selectUserIntegralByIdAssoc(Long id)
	{
	    return userIntegralMapper.selectUserIntegralByIdAssoc(id);
	}	
	
	/**
     * 查询所有用户电子积分卡列表
     * 
     * @param 
     * @return 用户电子积分卡集合
     */
	@Override 
	public List<UserIntegral> selectUserIntegralAll()
	{
		return userIntegralMapper.selectUserIntegralAll();
	}	
	
	/**
     * 查询所有用户电子积分卡列表 外键关联
     * 
     * @param 
     * @return 用户电子积分卡集合
     */
	@Override 
	public List<UserIntegral> selectUserIntegralAllAssoc()
	{
		return userIntegralMapper.selectUserIntegralAllAssoc();
	}		
	
	/**
     * 查询用户电子积分卡列表
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 用户电子积分卡集合
     */
	@Override
	public List<UserIntegral> selectUserIntegralList(UserIntegral userIntegral)
	{
	    return userIntegralMapper.selectUserIntegralList(userIntegral);
	}
	
	/**
     * 查询用户电子积分卡列表 外键关联
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 用户电子积分卡集合
     */
	@Override
	public List<UserIntegral> selectUserIntegralListAssoc(UserIntegral userIntegral)
	{
	    return userIntegralMapper.selectUserIntegralListAssoc(userIntegral);
	}	
	
    /**
     * 新增用户电子积分卡
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 结果
     */
	@Override
	public int insertUserIntegral(UserIntegral userIntegral)
	{
	    return userIntegralMapper.insertUserIntegral(userIntegral);
	}
	
	/**
     * 修改用户电子积分卡
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 结果
     */
	@Override
	public int updateUserIntegral(UserIntegral userIntegral)
	{
	    return userIntegralMapper.updateUserIntegral(userIntegral);
	}

	@Override
	public int updateUserIntegralByMember(UserIntegral userIntegral) {
		return userIntegralMapper.updateUserIntegralByMember(userIntegral);
	}

	/**
     * 删除用户电子积分卡对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserIntegralByIds(String ids)
	{
		return userIntegralMapper.deleteUserIntegralByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除用户电子积分卡对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserIntegralById(Long id)
	{
		return userIntegralMapper.deleteUserIntegralById(id);
	}
	
	



}
