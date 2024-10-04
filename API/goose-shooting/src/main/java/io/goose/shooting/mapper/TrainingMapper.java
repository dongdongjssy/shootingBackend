package io.goose.shooting.mapper;

import io.goose.shooting.domain.Training;
import java.util.List;	

/**
 * 培训 数据层
 * 
 * @author goose
 * @date 2020-05-03
 */
public interface TrainingMapper 
{
	/**
     * 查询培训信息
     * 
     * @param id 培训ID
     * @return 培训信息
     */
	public Training selectTrainingById(Long id);
	
	/**
     * 查询培训信息 外键关联
     * 
     * @param id 培训ID
     * @return 培训信息
     */
	public Training selectTrainingByIdAssoc(Long id);	

	/**
     * 查询所有培训列表
     * 
     * @param 
     * @return 培训集合
     */
	public List<Training> selectTrainingAll();	
	
	/**
     * 查询所有培训列表 外键关联
     * 
     * @param 
     * @return 培训集合
     */
	public List<Training> selectTrainingAllAssoc();		

	
	/**
     * 查询培训列表
     * 
     * @param training 培训信息
     * @return 培训集合
     */
	public List<Training> selectTrainingList(Training training);
	
	/**
     * 查询培训列表 外键关联
     * 
     * @param training 培训信息
     * @return 培训集合
     */
	public List<Training> selectTrainingListAssoc(Training training);	
	
	/**
     * 新增培训
     * 
     * @param training 培训信息
     * @return 结果
     */
	public int insertTraining(Training training);
	
	/**
     * 修改培训
     * 
     * @param training 培训信息
     * @return 结果
     */
	public int updateTraining(Training training);
	
	/**
     * 删除培训
     * 
     * @param id 培训ID
     * @return 结果
     */
	public int deleteTrainingById(Long id);
	
	/**
     * 批量删除培训
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTrainingByIds(String[] ids);
	
}