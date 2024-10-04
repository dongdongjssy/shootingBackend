package io.goose.shooting.service;

import io.goose.shooting.domain.Goods;
import java.util.List;

/**
 * 商品 服务层
 * 
 * @author goose
 * @date 2021-03-03
 */
public interface IGoodsService 
{
	/**
     * 查询商品信息
     * 
     * @param id 商品ID
     * @return 商品信息
     */
	public Goods selectGoodsById(Long id);
	
	/**
     * 查询商品信息 外键关联
     * 
     * @param id 商品ID
     * @return 商品信息
     */
	public Goods selectGoodsByIdAssoc(Long id);	
	
	/**
     * 查询所有商品列表
     * 
     * @param 
     * @return 商品集合
     */
	public List<Goods> selectGoodsAll();		
	
	/**
     * 查询所有商品列表  外键关联
     * 
     * @param 
     * @return 商品集合
     */
	public List<Goods> selectGoodsAllAssoc();		
	
	/**
     * 查询商品列表
     * 
     * @param goods 商品信息
     * @return 商品集合
     */
	public List<Goods> selectGoodsList(Goods goods);
	
	/**
     * 查询商品列表 外键关联
     * 
     * @param goods 商品信息
     * @return 商品集合
     */
	public List<Goods> selectGoodsListAssoc(Goods goods);	
	
	/**
     * 新增商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	public int insertGoods(Goods goods);
	
	/**
     * 修改商品
     * 
     * @param goods 商品信息
     * @return 结果
     */
	public int updateGoods(Goods goods);


	public int updateGoodsStatus(Goods goods);
		
	/**
     * 删除商品信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsByIds(String ids);
	
	/**
     * 删除商品信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsById(Long id);

	/**
	 * 删除指定商品类型的商品信息
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteGoodsByGoodsTypeIds(String ids);
}
