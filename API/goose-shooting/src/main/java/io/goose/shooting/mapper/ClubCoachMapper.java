package io.goose.shooting.mapper;

import io.goose.shooting.domain.ClubCoach;
import java.util.List;	

/**
 * 俱乐部教官 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface ClubCoachMapper 
{
	/**
     * 查询俱乐部教官信息
     * 
     * @param id 俱乐部教官ID
     * @return 俱乐部教官信息
     */
	public ClubCoach selectClubCoachById(Long id);
	
	/**
     * 查询俱乐部教官信息 外键关联
     * 
     * @param id 俱乐部教官ID
     * @return 俱乐部教官信息
     */
	public ClubCoach selectClubCoachByIdAssoc(Long id);	

	/**
     * 查询所有俱乐部教官列表
     * 
     * @param 
     * @return 俱乐部教官集合
     */
	public List<ClubCoach> selectClubCoachAll();	
	
	/**
     * 查询所有俱乐部教官列表 外键关联
     * 
     * @param 
     * @return 俱乐部教官集合
     */
	public List<ClubCoach> selectClubCoachAllAssoc();		

	
	/**
     * 查询俱乐部教官列表
     * 
     * @param clubCoach 俱乐部教官信息
     * @return 俱乐部教官集合
     */
	public List<ClubCoach> selectClubCoachList(ClubCoach clubCoach);
	
	/**
     * 查询俱乐部教官列表 外键关联
     * 
     * @param clubCoach 俱乐部教官信息
     * @return 俱乐部教官集合
     */
	public List<ClubCoach> selectClubCoachListAssoc(ClubCoach clubCoach);	
	
	/**
     * 新增俱乐部教官
     * 
     * @param clubCoach 俱乐部教官信息
     * @return 结果
     */
	public int insertClubCoach(ClubCoach clubCoach);
	
	/**
     * 修改俱乐部教官
     * 
     * @param clubCoach 俱乐部教官信息
     * @return 结果
     */
	public int updateClubCoach(ClubCoach clubCoach);
	
	/**
     * 删除俱乐部教官
     * 
     * @param id 俱乐部教官ID
     * @return 结果
     */
	public int deleteClubCoachById(Long id);
	
	/**
     * 批量删除俱乐部教官
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClubCoachByIds(String[] ids);
	
}