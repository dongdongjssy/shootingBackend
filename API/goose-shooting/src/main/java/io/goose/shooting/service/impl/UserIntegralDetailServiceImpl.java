package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.UserIntegralDetailMapper;
import io.goose.shooting.domain.UserIntegralDetail;

import io.goose.shooting.service.IUserIntegralDetailService;
import io.goose.common.support.Convert;

/**
 * 用户电子积分卡明细 服务层实现
 * 
 * @author goose
 * @date 2021-03-31
 */
@Service
public class UserIntegralDetailServiceImpl implements IUserIntegralDetailService 
{
	@Autowired
	private UserIntegralDetailMapper userIntegralDetailMapper;


	/**
     * 查询用户电子积分卡明细信息
     * 
     * @param id 用户电子积分卡明细ID
     * @return 用户电子积分卡明细信息
     */
    @Override
	public UserIntegralDetail selectUserIntegralDetailById(Long id)
	{
	    return userIntegralDetailMapper.selectUserIntegralDetailById(id);
	}
	
	/**
     * 查询用户电子积分卡明细信息 外键关联
     * 
     * @param id 用户电子积分卡明细ID
     * @return 用户电子积分卡明细信息
     */
    @Override
	public UserIntegralDetail selectUserIntegralDetailByIdAssoc(Long id)
	{
	    return userIntegralDetailMapper.selectUserIntegralDetailByIdAssoc(id);
	}	
	
	/**
     * 查询所有用户电子积分卡明细列表
     * 
     * @param 
     * @return 用户电子积分卡明细集合
     */
	@Override 
	public List<UserIntegralDetail> selectUserIntegralDetailAll()
	{
		return userIntegralDetailMapper.selectUserIntegralDetailAll();
	}	
	
	/**
     * 查询所有用户电子积分卡明细列表 外键关联
     * 
     * @param 
     * @return 用户电子积分卡明细集合
     */
	@Override 
	public List<UserIntegralDetail> selectUserIntegralDetailAllAssoc()
	{
		return userIntegralDetailMapper.selectUserIntegralDetailAllAssoc();
	}		
	
	/**
     * 查询用户电子积分卡明细列表
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 用户电子积分卡明细集合
     */
	@Override
	public List<UserIntegralDetail> selectUserIntegralDetailList(UserIntegralDetail userIntegralDetail)
	{
	    return userIntegralDetailMapper.selectUserIntegralDetailList(userIntegralDetail);
	}
	
	/**
     * 查询用户电子积分卡明细列表 外键关联
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 用户电子积分卡明细集合
     */
	@Override
	public List<UserIntegralDetail> selectUserIntegralDetailListAssoc(UserIntegralDetail userIntegralDetail)
	{
	    return userIntegralDetailMapper.selectUserIntegralDetailListAssoc(userIntegralDetail);
	}	
	
    /**
     * 新增用户电子积分卡明细
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 结果
     */
	@Override
	public int insertUserIntegralDetail(UserIntegralDetail userIntegralDetail)
	{
	    return userIntegralDetailMapper.insertUserIntegralDetail(userIntegralDetail);
	}
	
	/**
     * 修改用户电子积分卡明细
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 结果
     */
	@Override
	public int updateUserIntegralDetail(UserIntegralDetail userIntegralDetail)
	{
	    return userIntegralDetailMapper.updateUserIntegralDetail(userIntegralDetail);
	}

	@Override
	public int updateUserIntegralDetailByMember(UserIntegralDetail userIntegralDetail) {
		return userIntegralDetailMapper.updateUserIntegralDetailByMember(userIntegralDetail);
	}

	/**
     * 删除用户电子积分卡明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserIntegralDetailByIds(String ids)
	{
		return userIntegralDetailMapper.deleteUserIntegralDetailByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除用户电子积分卡明细对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserIntegralDetailById(Long id)
	{
		return userIntegralDetailMapper.deleteUserIntegralDetailById(id);
	}
	
	



}
