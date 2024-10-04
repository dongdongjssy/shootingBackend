package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsMapper;
import io.goose.shooting.domain.Goods;

import io.goose.shooting.service.IGoodsService;
import io.goose.common.support.Convert;

/**
 * 商品 服务层实现
 * 
 * @author goose
 * @date 2021-03-03
 */
@Service
public class GoodsServiceImpl implements IGoodsService 
{
	@Autowired
	private GoodsMapper goodsMapper;


	/**
     * 查询商品信息
     * 
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
	public Goods selectGoodsById(Long id)
	{
	    return goodsMapper.selectGoodsById(id);
	}
	
	/**
     * 查询商品信息 外键关联
     * 
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
	public Goods selectGoodsByIdAssoc(Long id)
	{
	    return goodsMapper.selectGoodsByIdAssoc(id);
	}	
	
	/**
     * 查询所有商品列表
     * 
     * @param 
     * @return 商品集合
     */
	@Override 
	public List<Goods> selectGoodsAll()
	{
		return goodsMapper.selectGoodsAll();
	}	
	
	/**
     * 查询所有商品列表 外键关联
     * 
     * @param 
     * @return 商品集合
     */
	@Override 
	public List<Goods> selectGoodsAllAssoc()
	{
		return goodsMapper.selectGoodsAllAssoc();
	}		
	
	/**
     * 查询商品列表
     * 
     * @param goods 商品信息
     * @return 商品集合
     */
	@Override
	public List<Goods> selectGoodsList(Goods goods)
	{
	    return goodsMapper.selectGoodsList(goods);
	}
	
	/**
     * 查询商品列表 外键关联
     * 
     * @param goods 商品信息
     * @return 商品集合
     */
	@Override
	public List<Goods> selectGoodsListAssoc(Goods goods)
	{
	    return goodsMapper.selectGoodsListAssoc(goods);
	}	
	
    /**
     * 新增商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	@Override
	public int insertGoods(Goods goods)
	{
	    return goodsMapper.insertGoods(goods);
	}
	
	/**
     * 修改商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	@Override
	public int updateGoods(Goods goods)
	{
	    return goodsMapper.updateGoods(goods);
	}

	@Override
	public int updateGoodsStatus(Goods goods) {
		return goodsMapper.updateGoodsStatus(goods);
	}

	/**
     * 删除商品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsByIds(String ids)
	{
		return goodsMapper.deleteGoodsByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商品对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsById(Long id)
	{
		return goodsMapper.deleteGoodsById(id);
	}

	@Override
	public int deleteGoodsByGoodsTypeIds(String ids) {
		return goodsMapper.deleteGoodsByGoodsTypeIds(Convert.toStrArray(ids));
	}


}
