package io.goose.shooting.service;

import io.goose.shooting.domain.RecommendCoach;
import java.util.List;

/**
 * 首页教官 服务层
 * 
 * @author goose
 * @date 2020-06-23
 */
public interface IRecommendCoachService 
{
	/**
     * 查询首页教官信息
     * 
     * @param id 首页教官ID
     * @return 首页教官信息
     */
	public RecommendCoach selectRecommendCoachById(Long id);
	
	/**
     * 查询首页教官信息 外键关联
     * 
     * @param id 首页教官ID
     * @return 首页教官信息
     */
	public RecommendCoach selectRecommendCoachByIdAssoc(Long id);	
	
	/**
     * 查询所有首页教官列表
     * 
     * @param 
     * @return 首页教官集合
     */
	public List<RecommendCoach> selectRecommendCoachAll();		
	
	/**
     * 查询所有首页教官列表  外键关联
     * 
     * @param 
     * @return 首页教官集合
     */
	public List<RecommendCoach> selectRecommendCoachAllAssoc();		
	
	/**
     * 查询首页教官列表
     * 
     * @param recommendCoach 首页教官信息
     * @return 首页教官集合
     */
	public List<RecommendCoach> selectRecommendCoachList(RecommendCoach recommendCoach);
	
	/**
     * 查询首页教官列表 外键关联
     * 
     * @param recommendCoach 首页教官信息
     * @return 首页教官集合
     */
	public List<RecommendCoach> selectRecommendCoachListAssoc(RecommendCoach recommendCoach);	
	
	/**
     * 新增首页教官
     * 
     * @param recommendCoach 首页教官信息
     * @return 结果
     */
	public int insertRecommendCoach(RecommendCoach recommendCoach);
	
	/**
     * 修改首页教官
     * 
     * @param recommendCoach 首页教官信息
     * @return 结果
     */
	public int updateRecommendCoach(RecommendCoach recommendCoach);
		
	/**
     * 删除首页教官信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRecommendCoachByIds(String ids);
	
	/**
     * 删除首页教官信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteRecommendCoachById(Long id);
	

  
}
