package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RecommendJudgeMapper;
import io.goose.shooting.domain.RecommendJudge;

import io.goose.shooting.service.IRecommendJudgeService;
import io.goose.common.support.Convert;

/**
 * 首页裁判 服务层实现
 * 
 * @author goose
 * @date 2020-06-23
 */
@Service
public class RecommendJudgeServiceImpl implements IRecommendJudgeService 
{
	@Autowired
	private RecommendJudgeMapper recommendJudgeMapper;


	/**
     * 查询首页裁判信息
     * 
     * @param id 首页裁判ID
     * @return 首页裁判信息
     */
    @Override
	public RecommendJudge selectRecommendJudgeById(Long id)
	{
	    return recommendJudgeMapper.selectRecommendJudgeById(id);
	}
	
	/**
     * 查询首页裁判信息 外键关联
     * 
     * @param id 首页裁判ID
     * @return 首页裁判信息
     */
    @Override
	public RecommendJudge selectRecommendJudgeByIdAssoc(Long id)
	{
	    return recommendJudgeMapper.selectRecommendJudgeByIdAssoc(id);
	}	
	
	/**
     * 查询所有首页裁判列表
     * 
     * @param 
     * @return 首页裁判集合
     */
	@Override 
	public List<RecommendJudge> selectRecommendJudgeAll()
	{
		return recommendJudgeMapper.selectRecommendJudgeAll();
	}	
	
	/**
     * 查询所有首页裁判列表 外键关联
     * 
     * @param 
     * @return 首页裁判集合
     */
	@Override 
	public List<RecommendJudge> selectRecommendJudgeAllAssoc()
	{
		return recommendJudgeMapper.selectRecommendJudgeAllAssoc();
	}		
	
	/**
     * 查询首页裁判列表
     * 
     * @param recommendJudge 首页裁判信息
     * @return 首页裁判集合
     */
	@Override
	public List<RecommendJudge> selectRecommendJudgeList(RecommendJudge recommendJudge)
	{
	    return recommendJudgeMapper.selectRecommendJudgeList(recommendJudge);
	}
	
	/**
     * 查询首页裁判列表 外键关联
     * 
     * @param recommendJudge 首页裁判信息
     * @return 首页裁判集合
     */
	@Override
	public List<RecommendJudge> selectRecommendJudgeListAssoc(RecommendJudge recommendJudge)
	{
	    return recommendJudgeMapper.selectRecommendJudgeListAssoc(recommendJudge);
	}	
	
    /**
     * 新增首页裁判
     * 
     * @param recommendJudge 首页裁判信息
     * @return 结果
     */
	@Override
	public int insertRecommendJudge(RecommendJudge recommendJudge)
	{
	    return recommendJudgeMapper.insertRecommendJudge(recommendJudge);
	}
	
	/**
     * 修改首页裁判
     * 
     * @param recommendJudge 首页裁判信息
     * @return 结果
     */
	@Override
	public int updateRecommendJudge(RecommendJudge recommendJudge)
	{
	    return recommendJudgeMapper.updateRecommendJudge(recommendJudge);
	}

	/**
     * 删除首页裁判对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRecommendJudgeByIds(String ids)
	{
		return recommendJudgeMapper.deleteRecommendJudgeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除首页裁判对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRecommendJudgeById(Long id)
	{
		return recommendJudgeMapper.deleteRecommendJudgeById(id);
	}
	
	



}
