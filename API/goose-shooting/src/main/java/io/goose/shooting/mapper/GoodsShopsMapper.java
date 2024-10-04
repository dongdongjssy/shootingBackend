package io.goose.shooting.mapper;

import io.goose.shooting.domain.GoodsShops;
import java.util.List;	

/**
 * 商铺 数据层
 * 
 * @author goose
 * @date 2021-03-19
 */
public interface GoodsShopsMapper 
{
	/**
     * 查询商铺信息
     * 
     * @param id 商铺ID
     * @return 商铺信息
     */
	public GoodsShops selectGoodsShopsById(Long id);
	
	/**
     * 查询商铺信息 外键关联
     * 
     * @param id 商铺ID
     * @return 商铺信息
     */
	public GoodsShops selectGoodsShopsByIdAssoc(Long id);	

	/**
     * 查询所有商铺列表
     * 
     * @param 
     * @return 商铺集合
     */
	public List<GoodsShops> selectGoodsShopsAll();	
	
	/**
     * 查询所有商铺列表 外键关联
     * 
     * @param 
     * @return 商铺集合
     */
	public List<GoodsShops> selectGoodsShopsAllAssoc();		

	
	/**
     * 查询商铺列表
     * 
     * @param goodsShops 商铺信息
     * @return 商铺集合
     */
	public List<GoodsShops> selectGoodsShopsList(GoodsShops goodsShops);
	
	/**
     * 查询商铺列表 外键关联
     * 
     * @param goodsShops 商铺信息
     * @return 商铺集合
     */
	public List<GoodsShops> selectGoodsShopsListAssoc(GoodsShops goodsShops);	
	
	/**
     * 新增商铺
     * 
     * @param goodsShops 商铺信息
     * @return 结果
     */
	public int insertGoodsShops(GoodsShops goodsShops);
	
	/**
     * 修改商铺
     * 
     * @param goodsShops 商铺信息
     * @return 结果
     */
	public int updateGoodsShops(GoodsShops goodsShops);
	
	/**
     * 删除商铺
     * 
     * @param id 商铺ID
     * @return 结果
     */
	public int deleteGoodsShopsById(Long id);
	
	/**
     * 批量删除商铺
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsShopsByIds(String[] ids);
	
}