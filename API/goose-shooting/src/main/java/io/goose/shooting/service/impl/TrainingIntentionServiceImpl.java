package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.TrainingIntentionMapper;
import io.goose.shooting.domain.TrainingIntention;

import io.goose.shooting.service.ITrainingIntentionService;
import io.goose.common.support.Convert;

/**
 * 培训意向管理 服务层实现
 * 
 * @author goose
 * @date 2021-02-02
 */
@Service
public class TrainingIntentionServiceImpl implements ITrainingIntentionService 
{
	@Autowired
	private TrainingIntentionMapper trainingIntentionMapper;


	/**
     * 查询培训意向管理信息
     * 
     * @param id 培训意向管理ID
     * @return 培训意向管理信息
     */
    @Override
	public TrainingIntention selectTrainingIntentionById(Long id)
	{
	    return trainingIntentionMapper.selectTrainingIntentionById(id);
	}
	
	/**
     * 查询培训意向管理信息 外键关联
     * 
     * @param id 培训意向管理ID
     * @return 培训意向管理信息
     */
    @Override
	public TrainingIntention selectTrainingIntentionByIdAssoc(Long id)
	{
	    return trainingIntentionMapper.selectTrainingIntentionByIdAssoc(id);
	}	
	
	/**
     * 查询所有培训意向管理列表
     * 
     * @param 
     * @return 培训意向管理集合
     */
	@Override 
	public List<TrainingIntention> selectTrainingIntentionAll()
	{
		return trainingIntentionMapper.selectTrainingIntentionAll();
	}	
	
	/**
     * 查询所有培训意向管理列表 外键关联
     * 
     * @param 
     * @return 培训意向管理集合
     */
	@Override 
	public List<TrainingIntention> selectTrainingIntentionAllAssoc()
	{
		return trainingIntentionMapper.selectTrainingIntentionAllAssoc();
	}		
	
	/**
     * 查询培训意向管理列表
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 培训意向管理集合
     */
	@Override
	public List<TrainingIntention> selectTrainingIntentionList(TrainingIntention trainingIntention)
	{
	    return trainingIntentionMapper.selectTrainingIntentionList(trainingIntention);
	}
	
	/**
     * 查询培训意向管理列表 外键关联
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 培训意向管理集合
     */
	@Override
	public List<TrainingIntention> selectTrainingIntentionListAssoc(TrainingIntention trainingIntention)
	{
	    return trainingIntentionMapper.selectTrainingIntentionListAssoc(trainingIntention);
	}	
	
    /**
     * 新增培训意向管理
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 结果
     */
	@Override
	public int insertTrainingIntention(TrainingIntention trainingIntention)
	{
	    return trainingIntentionMapper.insertTrainingIntention(trainingIntention);
	}
	
	/**
     * 修改培训意向管理
     * 
     * @param trainingIntention 培训意向管理信息
     * @return 结果
     */
	@Override
	public int updateTrainingIntention(TrainingIntention trainingIntention)
	{
	    return trainingIntentionMapper.updateTrainingIntention(trainingIntention);
	}

	/**
     * 删除培训意向管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTrainingIntentionByIds(String ids)
	{
		return trainingIntentionMapper.deleteTrainingIntentionByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除培训意向管理对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTrainingIntentionById(Long id)
	{
		return trainingIntentionMapper.deleteTrainingIntentionById(id);
	}
	
	



}
