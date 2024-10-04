package io.goose.shooting.mapper;

import io.goose.shooting.domain.UserIntegral;
import java.util.List;	

/**
 * 用户电子积分卡 数据层
 * 
 * @author goose
 * @date 2021-03-31
 */
public interface UserIntegralMapper 
{
	/**
     * 查询用户电子积分卡信息
     * 
     * @param id 用户电子积分卡ID
     * @return 用户电子积分卡信息
     */
	public UserIntegral selectUserIntegralById(Long id);
	
	/**
     * 查询用户电子积分卡信息 外键关联
     * 
     * @param id 用户电子积分卡ID
     * @return 用户电子积分卡信息
     */
	public UserIntegral selectUserIntegralByIdAssoc(Long id);	

	/**
     * 查询所有用户电子积分卡列表
     * 
     * @param 
     * @return 用户电子积分卡集合
     */
	public List<UserIntegral> selectUserIntegralAll();	
	
	/**
     * 查询所有用户电子积分卡列表 外键关联
     * 
     * @param 
     * @return 用户电子积分卡集合
     */
	public List<UserIntegral> selectUserIntegralAllAssoc();		

	
	/**
     * 查询用户电子积分卡列表
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 用户电子积分卡集合
     */
	public List<UserIntegral> selectUserIntegralList(UserIntegral userIntegral);
	
	/**
     * 查询用户电子积分卡列表 外键关联
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 用户电子积分卡集合
     */
	public List<UserIntegral> selectUserIntegralListAssoc(UserIntegral userIntegral);	
	
	/**
     * 新增用户电子积分卡
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 结果
     */
	public int insertUserIntegral(UserIntegral userIntegral);
	
	/**
     * 修改用户电子积分卡
     * 
     * @param userIntegral 用户电子积分卡信息
     * @return 结果
     */
	public int updateUserIntegral(UserIntegral userIntegral);

	/**
	 * 修改用户电子积分卡
	 *
	 * @param userIntegral 用户电子积分卡信息
	 * @return 结果
	 */
	public int  updateUserIntegralByMember(UserIntegral userIntegral);
	
	/**
     * 删除用户电子积分卡
     * 
     * @param id 用户电子积分卡ID
     * @return 结果
     */
	public int deleteUserIntegralById(Long id);
	
	/**
     * 批量删除用户电子积分卡
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserIntegralByIds(String[] ids);
	
}