package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsEvaluationMapper;
import io.goose.shooting.domain.GoodsEvaluation;

import io.goose.shooting.service.IGoodsEvaluationService;
import io.goose.common.support.Convert;

/**
 * 商品评价 服务层实现
 * 
 * @author goose
 * @date 2021-02-23
 */
@Service
public class GoodsEvaluationServiceImpl implements IGoodsEvaluationService 
{
	@Autowired
	private GoodsEvaluationMapper goodsEvaluationMapper;


	/**
     * 查询商品评价信息
     * 
     * @param id 商品评价ID
     * @return 商品评价信息
     */
    @Override
	public GoodsEvaluation selectGoodsEvaluationById(Long id)
	{
	    return goodsEvaluationMapper.selectGoodsEvaluationById(id);
	}
	
	/**
     * 查询商品评价信息 外键关联
     * 
     * @param id 商品评价ID
     * @return 商品评价信息
     */
    @Override
	public GoodsEvaluation selectGoodsEvaluationByIdAssoc(Long id)
	{
	    return goodsEvaluationMapper.selectGoodsEvaluationByIdAssoc(id);
	}	
	
	/**
     * 查询所有商品评价列表
     * 
     * @param 
     * @return 商品评价集合
     */
	@Override 
	public List<GoodsEvaluation> selectGoodsEvaluationAll()
	{
		return goodsEvaluationMapper.selectGoodsEvaluationAll();
	}	
	
	/**
     * 查询所有商品评价列表 外键关联
     * 
     * @param 
     * @return 商品评价集合
     */
	@Override 
	public List<GoodsEvaluation> selectGoodsEvaluationAllAssoc()
	{
		return goodsEvaluationMapper.selectGoodsEvaluationAllAssoc();
	}		
	
	/**
     * 查询商品评价列表
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 商品评价集合
     */
	@Override
	public List<GoodsEvaluation> selectGoodsEvaluationList(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.selectGoodsEvaluationList(goodsEvaluation);
	}
	
	/**
     * 查询商品评价列表 外键关联
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 商品评价集合
     */
	@Override
	public List<GoodsEvaluation> selectGoodsEvaluationListAssoc(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.selectGoodsEvaluationListAssoc(goodsEvaluation);
	}	
	
    /**
     * 新增商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	@Override
	public int insertGoodsEvaluation(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.insertGoodsEvaluation(goodsEvaluation);
	}
	
	/**
     * 修改商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	@Override
	public int updateGoodsEvaluation(GoodsEvaluation goodsEvaluation)
	{
	    return goodsEvaluationMapper.updateGoodsEvaluation(goodsEvaluation);
	}

	/**
     * 删除商品评价对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsEvaluationByIds(String ids)
	{
		return goodsEvaluationMapper.deleteGoodsEvaluationByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商品评价对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsEvaluationById(Long id)
	{
		return goodsEvaluationMapper.deleteGoodsEvaluationById(id);
	}
	
	



}
