package io.goose.shooting.service;

import io.goose.shooting.domain.ClubActivity;
import java.util.List;

/**
 * 俱乐部活动 服务层
 * 
 * @author goose
 * @date 2020-05-03
 */
public interface IClubActivityService 
{
	/**
     * 查询俱乐部活动信息
     * 
     * @param id 俱乐部活动ID
     * @return 俱乐部活动信息
     */
	public ClubActivity selectClubActivityById(Long id);
	
	/**
     * 查询俱乐部活动信息 外键关联
     * 
     * @param id 俱乐部活动ID
     * @return 俱乐部活动信息
     */
	public ClubActivity selectClubActivityByIdAssoc(Long id);	
	
	/**
     * 查询所有俱乐部活动列表
     * 
     * @param 
     * @return 俱乐部活动集合
     */
	public List<ClubActivity> selectClubActivityAll();		
	
	/**
     * 查询所有俱乐部活动列表  外键关联
     * 
     * @param 
     * @return 俱乐部活动集合
     */
	public List<ClubActivity> selectClubActivityAllAssoc();		
	
	/**
     * 查询俱乐部活动列表
     * 
     * @param clubActivity 俱乐部活动信息
     * @return 俱乐部活动集合
     */
	public List<ClubActivity> selectClubActivityList(ClubActivity clubActivity);
	
	/**
     * 查询俱乐部活动列表 外键关联
     * 
     * @param clubActivity 俱乐部活动信息
     * @return 俱乐部活动集合
     */
	public List<ClubActivity> selectClubActivityListAssoc(ClubActivity clubActivity);	
	
	/**
     * 新增俱乐部活动
     * 
     * @param clubActivity 俱乐部活动信息
     * @return 结果
     */
	public int insertClubActivity(ClubActivity clubActivity);
	
	/**
     * 修改俱乐部活动
     * 
     * @param clubActivity 俱乐部活动信息
     * @return 结果
     */
	public int updateClubActivity(ClubActivity clubActivity);
		
	/**
     * 删除俱乐部活动信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClubActivityByIds(String ids);
	
	/**
     * 删除俱乐部活动信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteClubActivityById(Long id);
	

  
}
