package io.goose.shooting.mapper;

import io.goose.shooting.domain.RecommendJudge;
import java.util.List;	

/**
 * 首页裁判 数据层
 * 
 * @author goose
 * @date 2020-06-23
 */
public interface RecommendJudgeMapper 
{
	/**
     * 查询首页裁判信息
     * 
     * @param id 首页裁判ID
     * @return 首页裁判信息
     */
	public RecommendJudge selectRecommendJudgeById(Long id);
	
	/**
     * 查询首页裁判信息 外键关联
     * 
     * @param id 首页裁判ID
     * @return 首页裁判信息
     */
	public RecommendJudge selectRecommendJudgeByIdAssoc(Long id);	

	/**
     * 查询所有首页裁判列表
     * 
     * @param 
     * @return 首页裁判集合
     */
	public List<RecommendJudge> selectRecommendJudgeAll();	
	
	/**
     * 查询所有首页裁判列表 外键关联
     * 
     * @param 
     * @return 首页裁判集合
     */
	public List<RecommendJudge> selectRecommendJudgeAllAssoc();		

	
	/**
     * 查询首页裁判列表
     * 
     * @param recommendJudge 首页裁判信息
     * @return 首页裁判集合
     */
	public List<RecommendJudge> selectRecommendJudgeList(RecommendJudge recommendJudge);
	
	/**
     * 查询首页裁判列表 外键关联
     * 
     * @param recommendJudge 首页裁判信息
     * @return 首页裁判集合
     */
	public List<RecommendJudge> selectRecommendJudgeListAssoc(RecommendJudge recommendJudge);	
	
	/**
     * 新增首页裁判
     * 
     * @param recommendJudge 首页裁判信息
     * @return 结果
     */
	public int insertRecommendJudge(RecommendJudge recommendJudge);
	
	/**
     * 修改首页裁判
     * 
     * @param recommendJudge 首页裁判信息
     * @return 结果
     */
	public int updateRecommendJudge(RecommendJudge recommendJudge);
	
	/**
     * 删除首页裁判
     * 
     * @param id 首页裁判ID
     * @return 结果
     */
	public int deleteRecommendJudgeById(Long id);
	
	/**
     * 批量删除首页裁判
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRecommendJudgeByIds(String[] ids);
	
}