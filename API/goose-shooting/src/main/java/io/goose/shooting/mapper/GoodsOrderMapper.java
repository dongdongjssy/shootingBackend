package io.goose.shooting.mapper;

import io.goose.shooting.domain.GoodsOrder;
import java.util.List;	

/**
 * 商品订单 数据层
 * 
 * @author goose
 * @date 2021-03-19
 */
public interface GoodsOrderMapper 
{
	/**
     * 查询商品订单信息
     * 
     * @param id 商品订单ID
     * @return 商品订单信息
     */
	public GoodsOrder selectGoodsOrderById(Long id);
	
	/**
     * 查询商品订单信息 外键关联
     * 
     * @param id 商品订单ID
     * @return 商品订单信息
     */
	public GoodsOrder selectGoodsOrderByIdAssoc(Long id);	

	/**
     * 查询所有商品订单列表
     * 
     * @param 
     * @return 商品订单集合
     */
	public List<GoodsOrder> selectGoodsOrderAll();	
	
	/**
     * 查询所有商品订单列表 外键关联
     * 
     * @param 
     * @return 商品订单集合
     */
	public List<GoodsOrder> selectGoodsOrderAllAssoc();		

	
	/**
     * 查询商品订单列表
     * 
     * @param goodsOrder 商品订单信息
     * @return 商品订单集合
     */
	public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder);
	
	/**
     * 查询商品订单列表 外键关联
     * 
     * @param goodsOrder 商品订单信息
     * @return 商品订单集合
     */
	public List<GoodsOrder> selectGoodsOrderListAssoc(GoodsOrder goodsOrder);	
	
	/**
     * 新增商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	public int insertGoodsOrder(GoodsOrder goodsOrder);
	
	/**
     * 修改商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	public int updateGoodsOrder(GoodsOrder goodsOrder);
	
	/**
     * 删除商品订单
     * 
     * @param id 商品订单ID
     * @return 结果
     */
	public int deleteGoodsOrderById(Long id);
	
	/**
     * 批量删除商品订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsOrderByIds(String[] ids);

	/**
	 * 批量删除商品订单根据购买订单
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */

	public int deleteGoodsOrderByOrderIds(String[] ids);

	List<GoodsOrder> selectGoodStatusByUserId(Long userId);

	List<GoodsOrder> selectGoodsOrdersByOrderId(Long orderId);


}