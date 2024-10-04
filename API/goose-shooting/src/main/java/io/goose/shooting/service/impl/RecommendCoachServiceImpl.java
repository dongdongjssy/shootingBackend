package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RecommendCoachMapper;
import io.goose.shooting.domain.RecommendCoach;

import io.goose.shooting.service.IRecommendCoachService;
import io.goose.common.support.Convert;

/**
 * 首页教官 服务层实现
 * 
 * @author goose
 * @date 2020-06-23
 */
@Service
public class RecommendCoachServiceImpl implements IRecommendCoachService 
{
	@Autowired
	private RecommendCoachMapper recommendCoachMapper;


	/**
     * 查询首页教官信息
     * 
     * @param id 首页教官ID
     * @return 首页教官信息
     */
    @Override
	public RecommendCoach selectRecommendCoachById(Long id)
	{
	    return recommendCoachMapper.selectRecommendCoachById(id);
	}
	
	/**
     * 查询首页教官信息 外键关联
     * 
     * @param id 首页教官ID
     * @return 首页教官信息
     */
    @Override
	public RecommendCoach selectRecommendCoachByIdAssoc(Long id)
	{
	    return recommendCoachMapper.selectRecommendCoachByIdAssoc(id);
	}	
	
	/**
     * 查询所有首页教官列表
     * 
     * @param 
     * @return 首页教官集合
     */
	@Override 
	public List<RecommendCoach> selectRecommendCoachAll()
	{
		return recommendCoachMapper.selectRecommendCoachAll();
	}	
	
	/**
     * 查询所有首页教官列表 外键关联
     * 
     * @param 
     * @return 首页教官集合
     */
	@Override 
	public List<RecommendCoach> selectRecommendCoachAllAssoc()
	{
		return recommendCoachMapper.selectRecommendCoachAllAssoc();
	}		
	
	/**
     * 查询首页教官列表
     * 
     * @param recommendCoach 首页教官信息
     * @return 首页教官集合
     */
	@Override
	public List<RecommendCoach> selectRecommendCoachList(RecommendCoach recommendCoach)
	{
	    return recommendCoachMapper.selectRecommendCoachList(recommendCoach);
	}
	
	/**
     * 查询首页教官列表 外键关联
     * 
     * @param recommendCoach 首页教官信息
     * @return 首页教官集合
     */
	@Override
	public List<RecommendCoach> selectRecommendCoachListAssoc(RecommendCoach recommendCoach)
	{
	    return recommendCoachMapper.selectRecommendCoachListAssoc(recommendCoach);
	}	
	
    /**
     * 新增首页教官
     * 
     * @param recommendCoach 首页教官信息
     * @return 结果
     */
	@Override
	public int insertRecommendCoach(RecommendCoach recommendCoach)
	{
	    return recommendCoachMapper.insertRecommendCoach(recommendCoach);
	}
	
	/**
     * 修改首页教官
     * 
     * @param recommendCoach 首页教官信息
     * @return 结果
     */
	@Override
	public int updateRecommendCoach(RecommendCoach recommendCoach)
	{
	    return recommendCoachMapper.updateRecommendCoach(recommendCoach);
	}

	/**
     * 删除首页教官对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRecommendCoachByIds(String ids)
	{
		return recommendCoachMapper.deleteRecommendCoachByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除首页教官对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRecommendCoachById(Long id)
	{
		return recommendCoachMapper.deleteRecommendCoachById(id);
	}
	
	



}
