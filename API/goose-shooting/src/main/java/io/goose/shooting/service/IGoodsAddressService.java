package io.goose.shooting.service;

import io.goose.shooting.domain.GoodsAddress;
import java.util.List;

/**
 * 收货地址 服务层
 * 
 * @author goose
 * @date 2021-02-23
 */
public interface IGoodsAddressService 
{
	/**
     * 查询收货地址信息
     * 
     * @param id 收货地址ID
     * @return 收货地址信息
     */
	public GoodsAddress selectGoodsAddressById(Long id);
	
	/**
     * 查询收货地址信息 外键关联
     * 
     * @param id 收货地址ID
     * @return 收货地址信息
     */
	public GoodsAddress selectGoodsAddressByIdAssoc(Long id);	
	
	/**
     * 查询所有收货地址列表
     * 
     * @param 
     * @return 收货地址集合
     */
	public List<GoodsAddress> selectGoodsAddressAll();		
	
	/**
     * 查询所有收货地址列表  外键关联
     * 
     * @param 
     * @return 收货地址集合
     */
	public List<GoodsAddress> selectGoodsAddressAllAssoc();		
	
	/**
     * 查询收货地址列表
     * 
     * @param goodsAddress 收货地址信息
     * @return 收货地址集合
     */
	public List<GoodsAddress> selectGoodsAddressList(GoodsAddress goodsAddress);
	
	/**
     * 查询收货地址列表 外键关联
     * 
     * @param goodsAddress 收货地址信息
     * @return 收货地址集合
     */
	public List<GoodsAddress> selectGoodsAddressListAssoc(GoodsAddress goodsAddress);	
	
	/**
     * 新增收货地址
     * 
     * @param goodsAddress 收货地址信息
     * @return 结果
     */
	public int insertGoodsAddress(GoodsAddress goodsAddress);
	
	/**
     * 修改收货地址
     * 
     * @param goodsAddress 收货地址信息
     * @return 结果
     */
	public int updateGoodsAddress(GoodsAddress goodsAddress);
		
	/**
     * 删除收货地址信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsAddressByIds(String ids);
	
	/**
     * 删除收货地址信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsAddressById(Long id);
	

  
}
