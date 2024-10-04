package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsShoppingCartMapper;
import io.goose.shooting.domain.GoodsShoppingCart;

import io.goose.shooting.service.IGoodsShoppingCartService;
import io.goose.common.support.Convert;

/**
 * 购物车 服务层实现
 * 
 * @author goose
 * @date 2021-03-16
 */
@Service
public class GoodsShoppingCartServiceImpl implements IGoodsShoppingCartService 
{
	@Autowired
	private GoodsShoppingCartMapper goodsShoppingCartMapper;


	/**
     * 查询购物车信息
     * 
     * @param id 购物车ID
     * @return 购物车信息
     */
    @Override
	public GoodsShoppingCart selectGoodsShoppingCartById(Long id)
	{
	    return goodsShoppingCartMapper.selectGoodsShoppingCartById(id);
	}
	
	/**
     * 查询购物车信息 外键关联
     * 
     * @param id 购物车ID
     * @return 购物车信息
     */
    @Override
	public GoodsShoppingCart selectGoodsShoppingCartByIdAssoc(Long id)
	{
	    return goodsShoppingCartMapper.selectGoodsShoppingCartByIdAssoc(id);
	}	
	
	/**
     * 查询所有购物车列表
     * 
     * @param 
     * @return 购物车集合
     */
	@Override 
	public List<GoodsShoppingCart> selectGoodsShoppingCartAll()
	{
		return goodsShoppingCartMapper.selectGoodsShoppingCartAll();
	}	
	
	/**
     * 查询所有购物车列表 外键关联
     * 
     * @param 
     * @return 购物车集合
     */
	@Override 
	public List<GoodsShoppingCart> selectGoodsShoppingCartAllAssoc()
	{
		return goodsShoppingCartMapper.selectGoodsShoppingCartAllAssoc();
	}		
	
	/**
     * 查询购物车列表
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 购物车集合
     */
	@Override
	public List<GoodsShoppingCart> selectGoodsShoppingCartList(GoodsShoppingCart goodsShoppingCart)
	{
	    return goodsShoppingCartMapper.selectGoodsShoppingCartList(goodsShoppingCart);
	}
	
	/**
     * 查询购物车列表 外键关联
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 购物车集合
     */
	@Override
	public List<GoodsShoppingCart> selectGoodsShoppingCartListAssoc(GoodsShoppingCart goodsShoppingCart)
	{
	    return goodsShoppingCartMapper.selectGoodsShoppingCartListAssoc(goodsShoppingCart);
	}	
	
    /**
     * 新增购物车
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 结果
     */
	@Override
	public int insertGoodsShoppingCart(GoodsShoppingCart goodsShoppingCart)
	{
	    return goodsShoppingCartMapper.insertGoodsShoppingCart(goodsShoppingCart);
	}
	
	/**
     * 修改购物车
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 结果
     */
	@Override
	public int updateGoodsShoppingCart(GoodsShoppingCart goodsShoppingCart)
	{
	    return goodsShoppingCartMapper.updateGoodsShoppingCart(goodsShoppingCart);
	}

	/**
     * 删除购物车对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsShoppingCartByIds(String ids)
	{
		return goodsShoppingCartMapper.deleteGoodsShoppingCartByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除购物车对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsShoppingCartById(Long id)
	{
		return goodsShoppingCartMapper.deleteGoodsShoppingCartById(id);
	}
	
	



}
