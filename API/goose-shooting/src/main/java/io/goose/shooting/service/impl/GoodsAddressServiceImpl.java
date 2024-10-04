package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsAddressMapper;
import io.goose.shooting.domain.GoodsAddress;

import io.goose.shooting.service.IGoodsAddressService;
import io.goose.common.support.Convert;

/**
 * 收货地址 服务层实现
 * 
 * @author goose
 * @date 2021-02-23
 */
@Service
public class GoodsAddressServiceImpl implements IGoodsAddressService 
{
	@Autowired
	private GoodsAddressMapper goodsAddressMapper;


	/**
     * 查询收货地址信息
     * 
     * @param id 收货地址ID
     * @return 收货地址信息
     */
    @Override
	public GoodsAddress selectGoodsAddressById(Long id)
	{
	    return goodsAddressMapper.selectGoodsAddressById(id);
	}
	
	/**
     * 查询收货地址信息 外键关联
     * 
     * @param id 收货地址ID
     * @return 收货地址信息
     */
    @Override
	public GoodsAddress selectGoodsAddressByIdAssoc(Long id)
	{
	    return goodsAddressMapper.selectGoodsAddressByIdAssoc(id);
	}	
	
	/**
     * 查询所有收货地址列表
     * 
     * @param 
     * @return 收货地址集合
     */
	@Override 
	public List<GoodsAddress> selectGoodsAddressAll()
	{
		return goodsAddressMapper.selectGoodsAddressAll();
	}	
	
	/**
     * 查询所有收货地址列表 外键关联
     * 
     * @param 
     * @return 收货地址集合
     */
	@Override 
	public List<GoodsAddress> selectGoodsAddressAllAssoc()
	{
		return goodsAddressMapper.selectGoodsAddressAllAssoc();
	}		
	
	/**
     * 查询收货地址列表
     * 
     * @param goodsAddress 收货地址信息
     * @return 收货地址集合
     */
	@Override
	public List<GoodsAddress> selectGoodsAddressList(GoodsAddress goodsAddress)
	{
	    return goodsAddressMapper.selectGoodsAddressList(goodsAddress);
	}
	
	/**
     * 查询收货地址列表 外键关联
     * 
     * @param goodsAddress 收货地址信息
     * @return 收货地址集合
     */
	@Override
	public List<GoodsAddress> selectGoodsAddressListAssoc(GoodsAddress goodsAddress)
	{
	    return goodsAddressMapper.selectGoodsAddressListAssoc(goodsAddress);
	}	
	
    /**
     * 新增收货地址
     * 
     * @param goodsAddress 收货地址信息
     * @return 结果
     */
	@Override
	public int insertGoodsAddress(GoodsAddress goodsAddress)
	{
	    return goodsAddressMapper.insertGoodsAddress(goodsAddress);
	}
	
	/**
     * 修改收货地址
     * 
     * @param goodsAddress 收货地址信息
     * @return 结果
     */
	@Override
	public int updateGoodsAddress(GoodsAddress goodsAddress)
	{
		if(goodsAddress.getDefaultAddress()!=null&&goodsAddress.getDefaultAddress()==0){
			GoodsAddress ga=new GoodsAddress();
			ga.setClientUserId(goodsAddress.getClientUserId());
			goodsAddressMapper.updateGoodsAddressDefaultAddress(ga);
		}
	    return goodsAddressMapper.updateGoodsAddress(goodsAddress);
	}

	/**
     * 删除收货地址对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsAddressByIds(String ids)
	{
		return goodsAddressMapper.deleteGoodsAddressByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除收货地址对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsAddressById(Long id)
	{
		return goodsAddressMapper.deleteGoodsAddressById(id);
	}
	
	



}
