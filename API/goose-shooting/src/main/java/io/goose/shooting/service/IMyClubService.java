package io.goose.shooting.service;

import io.goose.shooting.domain.MyClub;
import java.util.List;

/**
 * 我关注的俱乐部 服务层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface IMyClubService 
{
	/**
     * 查询我关注的俱乐部信息
     * 
     * @param id 我关注的俱乐部ID
     * @return 我关注的俱乐部信息
     */
	public MyClub selectMyClubById(Long id);
	
	/**
     * 查询我关注的俱乐部信息 外键关联
     * 
     * @param id 我关注的俱乐部ID
     * @return 我关注的俱乐部信息
     */
	public MyClub selectMyClubByIdAssoc(Long id);	
	
	/**
     * 查询所有我关注的俱乐部列表
     * 
     * @param 
     * @return 我关注的俱乐部集合
     */
	public List<MyClub> selectMyClubAll();		
	
	/**
     * 查询所有我关注的俱乐部列表  外键关联
     * 
     * @param 
     * @return 我关注的俱乐部集合
     */
	public List<MyClub> selectMyClubAllAssoc();		
	
	/**
     * 查询我关注的俱乐部列表
     * 
     * @param myClub 我关注的俱乐部信息
     * @return 我关注的俱乐部集合
     */
	public List<MyClub> selectMyClubList(MyClub myClub);
	
	/**
     * 查询我关注的俱乐部列表 外键关联
     * 
     * @param myClub 我关注的俱乐部信息
     * @return 我关注的俱乐部集合
     */
	public List<MyClub> selectMyClubListAssoc(MyClub myClub);	
	
	/**
     * 新增我关注的俱乐部
     * 
     * @param myClub 我关注的俱乐部信息
     * @return 结果
     */
	public int insertMyClub(MyClub myClub);
	
	/**
     * 修改我关注的俱乐部
     * 
     * @param myClub 我关注的俱乐部信息
     * @return 结果
     */
	public int updateMyClub(MyClub myClub);
		
	/**
     * 删除我关注的俱乐部信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMyClubByIds(String ids);
	
	/**
     * 删除我关注的俱乐部信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteMyClubById(Long id);

	public int deleteMyClubByClientUserId(Long clientUserId);
	

  
}
