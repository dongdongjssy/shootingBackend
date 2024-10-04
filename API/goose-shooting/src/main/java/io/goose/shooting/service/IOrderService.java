package io.goose.shooting.service;

import io.goose.shooting.domain.GoodsOrder;
import io.goose.shooting.domain.Order;
import java.util.List;

/**
 * 订单 服务层
 * 
 * @author goose
 * @date 2021-02-23
 */
public interface IOrderService 
{
	/**
     * 查询订单信息
     * 
     * @param id 订单ID
     * @return 订单信息
     */
	public Order selectOrderById(Long id);
	
	/**
     * 查询订单信息 外键关联
     * 
     * @param id 订单ID
     * @return 订单信息
     */
	public Order selectOrderByIdAssoc(Long id);	
	
	/**
     * 查询所有订单列表
     * 
     * @param 
     * @return 订单集合
     */
	public List<Order> selectOrderAll();		
	
	/**
     * 查询所有订单列表  外键关联
     * 
     * @param 
     * @return 订单集合
     */
	public List<Order> selectOrderAllAssoc();		
	
	/**
     * 查询订单列表
     * 
     * @param order 订单信息
     * @return 订单集合
     */
	public List<Order> selectOrderList(Order order);
	
	/**
     * 查询订单列表 外键关联
     * 
     * @param order 订单信息
     * @return 订单集合
     */
	public List<Order> selectOrderListAssoc(Order order);	
	
	/**
     * 新增订单
     * 
     * @param order 订单信息
     * @return 结果
     */
	public int insertOrder(Order order);
	
	/**
     * 修改订单
     * 
     * @param order 订单信息
     * @return 结果
     */
	public int updateOrder(Order order);
		
	/**
     * 删除订单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOrderByIds(String ids);
	
	/**
     * 删除订单信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteOrderById(Long id);
	

}
