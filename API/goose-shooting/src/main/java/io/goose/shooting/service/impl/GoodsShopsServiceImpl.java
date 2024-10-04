package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsShopsMapper;
import io.goose.shooting.domain.GoodsShops;

import io.goose.shooting.service.IGoodsShopsService;
import io.goose.common.support.Convert;

/**
 * 商铺 服务层实现
 * 
 * @author goose
 * @date 2021-03-19
 */
@Service
public class GoodsShopsServiceImpl implements IGoodsShopsService 
{
	@Autowired
	private GoodsShopsMapper goodsShopsMapper;


	/**
     * 查询商铺信息
     * 
     * @param id 商铺ID
     * @return 商铺信息
     */
    @Override
	public GoodsShops selectGoodsShopsById(Long id)
	{
	    return goodsShopsMapper.selectGoodsShopsById(id);
	}
	
	/**
     * 查询商铺信息 外键关联
     * 
     * @param id 商铺ID
     * @return 商铺信息
     */
    @Override
	public GoodsShops selectGoodsShopsByIdAssoc(Long id)
	{
	    return goodsShopsMapper.selectGoodsShopsByIdAssoc(id);
	}	
	
	/**
     * 查询所有商铺列表
     * 
     * @param 
     * @return 商铺集合
     */
	@Override 
	public List<GoodsShops> selectGoodsShopsAll()
	{
		return goodsShopsMapper.selectGoodsShopsAll();
	}	
	
	/**
     * 查询所有商铺列表 外键关联
     * 
     * @param 
     * @return 商铺集合
     */
	@Override 
	public List<GoodsShops> selectGoodsShopsAllAssoc()
	{
		return goodsShopsMapper.selectGoodsShopsAllAssoc();
	}		
	
	/**
     * 查询商铺列表
     * 
     * @param goodsShops 商铺信息
     * @return 商铺集合
     */
	@Override
	public List<GoodsShops> selectGoodsShopsList(GoodsShops goodsShops)
	{
	    return goodsShopsMapper.selectGoodsShopsList(goodsShops);
	}
	
	/**
     * 查询商铺列表 外键关联
     * 
     * @param goodsShops 商铺信息
     * @return 商铺集合
     */
	@Override
	public List<GoodsShops> selectGoodsShopsListAssoc(GoodsShops goodsShops)
	{
	    return goodsShopsMapper.selectGoodsShopsListAssoc(goodsShops);
	}	
	
    /**
     * 新增商铺
     * 
     * @param goodsShops 商铺信息
     * @return 结果
     */
	@Override
	public int insertGoodsShops(GoodsShops goodsShops)
	{
	    return goodsShopsMapper.insertGoodsShops(goodsShops);
	}
	
	/**
     * 修改商铺
     * 
     * @param goodsShops 商铺信息
     * @return 结果
     */
	@Override
	public int updateGoodsShops(GoodsShops goodsShops)
	{
	    return goodsShopsMapper.updateGoodsShops(goodsShops);
	}

	/**
     * 删除商铺对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsShopsByIds(String ids)
	{
		return goodsShopsMapper.deleteGoodsShopsByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商铺对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsShopsById(Long id)
	{
		return goodsShopsMapper.deleteGoodsShopsById(id);
	}
	
	



}
