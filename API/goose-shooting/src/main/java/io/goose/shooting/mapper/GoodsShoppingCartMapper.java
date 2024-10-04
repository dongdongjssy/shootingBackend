package io.goose.shooting.mapper;

import io.goose.shooting.domain.GoodsShoppingCart;
import java.util.List;	

/**
 * 购物车 数据层
 * 
 * @author goose
 * @date 2021-03-16
 */
public interface GoodsShoppingCartMapper 
{
	/**
     * 查询购物车信息
     * 
     * @param id 购物车ID
     * @return 购物车信息
     */
	public GoodsShoppingCart selectGoodsShoppingCartById(Long id);
	
	/**
     * 查询购物车信息 外键关联
     * 
     * @param id 购物车ID
     * @return 购物车信息
     */
	public GoodsShoppingCart selectGoodsShoppingCartByIdAssoc(Long id);	

	/**
     * 查询所有购物车列表
     * 
     * @param 
     * @return 购物车集合
     */
	public List<GoodsShoppingCart> selectGoodsShoppingCartAll();	
	
	/**
     * 查询所有购物车列表 外键关联
     * 
     * @param 
     * @return 购物车集合
     */
	public List<GoodsShoppingCart> selectGoodsShoppingCartAllAssoc();		

	
	/**
     * 查询购物车列表
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 购物车集合
     */
	public List<GoodsShoppingCart> selectGoodsShoppingCartList(GoodsShoppingCart goodsShoppingCart);
	
	/**
     * 查询购物车列表 外键关联
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 购物车集合
     */
	public List<GoodsShoppingCart> selectGoodsShoppingCartListAssoc(GoodsShoppingCart goodsShoppingCart);	
	
	/**
     * 新增购物车
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 结果
     */
	public int insertGoodsShoppingCart(GoodsShoppingCart goodsShoppingCart);
	
	/**
     * 修改购物车
     * 
     * @param goodsShoppingCart 购物车信息
     * @return 结果
     */
	public int updateGoodsShoppingCart(GoodsShoppingCart goodsShoppingCart);
	
	/**
     * 删除购物车
     * 
     * @param id 购物车ID
     * @return 结果
     */
	public int deleteGoodsShoppingCartById(Long id);
	
	/**
     * 批量删除购物车
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsShoppingCartByIds(String[] ids);
	
}