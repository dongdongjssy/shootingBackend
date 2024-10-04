package io.goose.shooting.service;

import io.goose.shooting.domain.Coach;
import java.util.List;

/**
 * 教官 服务层
 * 
 * @author goose
 * @date 2020-05-21
 */
public interface ICoachService 
{
	/**
     * 查询教官信息
     * 
     * @param id 教官ID
     * @return 教官信息
     */
	public Coach selectCoachById(Long id);
	
	/**
     * 查询教官信息 外键关联
     * 
     * @param id 教官ID
     * @return 教官信息
     */
	public Coach selectCoachByIdAssoc(Long id);	
	
	/**
     * 查询所有教官列表
     * 
     * @param 
     * @return 教官集合
     */
	public List<Coach> selectCoachAll();		
	
	/**
     * 查询所有教官列表  外键关联
     * 
     * @param 
     * @return 教官集合
     */
	public List<Coach> selectCoachAllAssoc();		
	
	/**
     * 查询教官列表
     * 
     * @param coach 教官信息
     * @return 教官集合
     */
	public List<Coach> selectCoachList(Coach coach);
	
	/**
     * 查询教官列表 外键关联
     * 
     * @param coach 教官信息
     * @return 教官集合
     */
	public List<Coach> selectCoachListAssoc(Coach coach);	
	
	/**
     * 新增教官
     * 
     * @param coach 教官信息
     * @return 结果
     */
	public int insertCoach(Coach coach);
	
	/**
     * 修改教官
     * 
     * @param coach 教官信息
     * @return 结果
     */
	public int updateCoach(Coach coach);
		
	/**
     * 删除教官信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCoachByIds(String ids);
	
	/**
     * 删除教官信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteCoachById(Long id);
	

  
}
