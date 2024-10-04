package io.goose.shooting.service.impl;

import java.util.List;

import io.goose.shooting.domain.GoodsOrder;
import io.goose.shooting.mapper.GoodsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.OrderMapper;
import io.goose.shooting.domain.Order;

import io.goose.shooting.service.IOrderService;
import io.goose.common.support.Convert;

/**
 * 订单 服务层实现
 * 
 * @author goose
 * @date 2021-02-23
 */
@Service
public class OrderServiceImpl implements IOrderService 
{
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private GoodsOrderMapper goodsOrderMapper;
	/**
     * 查询订单信息
     * 
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
	public Order selectOrderById(Long id)
	{
	    return orderMapper.selectOrderById(id);
	}
	
	/**
     * 查询订单信息 外键关联
     * 
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
	public Order selectOrderByIdAssoc(Long id)
	{
	    return orderMapper.selectOrderByIdAssoc(id);
	}	
	
	/**
     * 查询所有订单列表
     * 
     * @param 
     * @return 订单集合
     */
	@Override 
	public List<Order> selectOrderAll()
	{
		return orderMapper.selectOrderAll();
	}	
	
	/**
     * 查询所有订单列表 外键关联
     * 
     * @param 
     * @return 订单集合
     */
	@Override 
	public List<Order> selectOrderAllAssoc()
	{
		return orderMapper.selectOrderAllAssoc();
	}		
	
	/**
     * 查询订单列表
     * 
     * @param order 订单信息
     * @return 订单集合
     */
	@Override
	public List<Order> selectOrderList(Order order)
	{
	    return orderMapper.selectOrderList(order);
	}
	
	/**
     * 查询订单列表 外键关联
     * 
     * @param order 订单信息
     * @return 订单集合
     */
	@Override
	public List<Order> selectOrderListAssoc(Order order)
	{
	    return orderMapper.selectOrderListAssoc(order);
	}	
	
    /**
     * 新增订单
     * 
     * @param order 订单信息
     * @return 结果
     */
	@Override
	public int insertOrder(Order order)
	{
	    return orderMapper.insertOrder(order);
	}
	
	/**
     * 修改订单
     * 
     * @param order 订单信息
     * @return 结果
     */
	@Override
	public int updateOrder(Order order)
	{
	    return orderMapper.updateOrder(order);
	}

	/**
     * 删除订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOrderByIds(String ids)
	{
		goodsOrderMapper.deleteGoodsOrderByOrderIds(Convert.toStrArray(ids));
		return orderMapper.deleteOrderByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除订单对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOrderById(Long id)
	{
		return orderMapper.deleteOrderById(id);
	}




}
