package io.goose.shooting.service;

import io.goose.shooting.domain.UserIntegralDetail;
import java.util.List;

/**
 * 用户电子积分卡明细 服务层
 * 
 * @author goose
 * @date 2021-03-31
 */
public interface IUserIntegralDetailService 
{
	/**
     * 查询用户电子积分卡明细信息
     * 
     * @param id 用户电子积分卡明细ID
     * @return 用户电子积分卡明细信息
     */
	public UserIntegralDetail selectUserIntegralDetailById(Long id);
	
	/**
     * 查询用户电子积分卡明细信息 外键关联
     * 
     * @param id 用户电子积分卡明细ID
     * @return 用户电子积分卡明细信息
     */
	public UserIntegralDetail selectUserIntegralDetailByIdAssoc(Long id);	
	
	/**
     * 查询所有用户电子积分卡明细列表
     * 
     * @param 
     * @return 用户电子积分卡明细集合
     */
	public List<UserIntegralDetail> selectUserIntegralDetailAll();		
	
	/**
     * 查询所有用户电子积分卡明细列表  外键关联
     * 
     * @param 
     * @return 用户电子积分卡明细集合
     */
	public List<UserIntegralDetail> selectUserIntegralDetailAllAssoc();		
	
	/**
     * 查询用户电子积分卡明细列表
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 用户电子积分卡明细集合
     */
	public List<UserIntegralDetail> selectUserIntegralDetailList(UserIntegralDetail userIntegralDetail);
	
	/**
     * 查询用户电子积分卡明细列表 外键关联
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 用户电子积分卡明细集合
     */
	public List<UserIntegralDetail> selectUserIntegralDetailListAssoc(UserIntegralDetail userIntegralDetail);	
	
	/**
     * 新增用户电子积分卡明细
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 结果
     */
	public int insertUserIntegralDetail(UserIntegralDetail userIntegralDetail);
	
	/**
     * 修改用户电子积分卡明细
     * 
     * @param userIntegralDetail 用户电子积分卡明细信息
     * @return 结果
     */
	public int updateUserIntegralDetail(UserIntegralDetail userIntegralDetail);


	/**
	 * 修改用户电子积分卡明细根据射手编号
	 *
	 * @param userIntegralDetail 用户电子积分卡明细信息
	 * @return 结果
	 */
	public int updateUserIntegralDetailByMember(UserIntegralDetail userIntegralDetail);
		
	/**
     * 删除用户电子积分卡明细信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserIntegralDetailByIds(String ids);
	
	/**
     * 删除用户电子积分卡明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserIntegralDetailById(Long id);
	

  
}
