package io.goose.shooting.service;

import io.goose.shooting.domain.TrainingIntention;
import java.util.List;

/**
 * 培训意向管理 服务层
 * 
 * @author goose
 * @date 2021-02-02
 */
public interface ITrainingIntentionService 
{
	/**
     * 查询培训意向管理信息
     * 
     * @param id 培训意向管理ID
     * @return 培训意向管理信息
     */
	public TrainingIntention selectTrainingIntentionById(Long id);
	
	/**
     * 查询培训意向管理信息 外键关联
     * 
     * @param id 培训意向管理ID
     * @return 培训意向管理信息
     */
	public TrainingIntention selectTrainingIntentionByIdAssoc(Long id);	
	
	/**
     * 查询所有培训意向管理列表
     * 
     * @param 
     * @return 培训意向管理集合
     */
	public List<TrainingIntention> selectTrainingIntentionAll();		
	
	/**
     * 查询所有培训意向管理列表  外键关联
     * 
     * @param 
     * @return 培训意向管理集合
     */
	public List<TrainingIntention> selectTrainingIntentionAllAssoc();		
	
	/**
     * 查询培训意向管理列表
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 培训意向管理集合
     */
	public List<TrainingIntention> selectTrainingIntentionList(TrainingIntention trainingIntention);
	
	/**
     * 查询培训意向管理列表 外键关联
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 培训意向管理集合
     */
	public List<TrainingIntention> selectTrainingIntentionListAssoc(TrainingIntention trainingIntention);	
	
	/**
     * 新增培训意向管理
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 结果
     */
	public int insertTrainingIntention(TrainingIntention trainingIntention);
	
	/**
     * 修改培训意向管理
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 结果
     */
	public int updateTrainingIntention(TrainingIntention trainingIntention);
		
	/**
     * 删除培训意向管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTrainingIntentionByIds(String ids);
	
	/**
     * 删除培训意向管理信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteTrainingIntentionById(Long id);
	

  
}
