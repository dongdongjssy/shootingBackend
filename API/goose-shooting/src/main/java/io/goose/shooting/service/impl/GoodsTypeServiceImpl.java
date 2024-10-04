package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsTypeMapper;
import io.goose.shooting.domain.GoodsType;

import io.goose.shooting.service.IGoodsTypeService;
import io.goose.common.support.Convert;

/**
 * 商品类型 服务层实现
 * 
 * @author goose
 * @date 2021-03-03
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService 
{
	@Autowired
	private GoodsTypeMapper goodsTypeMapper;


	/**
     * 查询商品类型信息
     * 
     * @param id 商品类型ID
     * @return 商品类型信息
     */
    @Override
	public GoodsType selectGoodsTypeById(Long id)
	{
	    return goodsTypeMapper.selectGoodsTypeById(id);
	}
	
	/**
     * 查询商品类型信息 外键关联
     * 
     * @param id 商品类型ID
     * @return 商品类型信息
     */
    @Override
	public GoodsType selectGoodsTypeByIdAssoc(Long id)
	{
	    return goodsTypeMapper.selectGoodsTypeByIdAssoc(id);
	}	
	
	/**
     * 查询所有商品类型列表
     * 
     * @param 
     * @return 商品类型集合
     */
	@Override 
	public List<GoodsType> selectGoodsTypeAll()
	{
		return goodsTypeMapper.selectGoodsTypeAll();
	}	
	
	/**
     * 查询所有商品类型列表 外键关联
     * 
     * @param 
     * @return 商品类型集合
     */
	@Override 
	public List<GoodsType> selectGoodsTypeAllAssoc()
	{
		return goodsTypeMapper.selectGoodsTypeAllAssoc();
	}		
	
	/**
     * 查询商品类型列表
     * 
     * @param goodsType 商品类型信息
     * @return 商品类型集合
     */
	@Override
	public List<GoodsType> selectGoodsTypeList(GoodsType goodsType)
	{
	    return goodsTypeMapper.selectGoodsTypeList(goodsType);
	}
	
	/**
     * 查询商品类型列表 外键关联
     * 
     * @param goodsType 商品类型信息
     * @return 商品类型集合
     */
	@Override
	public List<GoodsType> selectGoodsTypeListAssoc(GoodsType goodsType)
	{
	    return goodsTypeMapper.selectGoodsTypeListAssoc(goodsType);
	}	
	
    /**
     * 新增商品类型
     * 
     * @param goodsType 商品类型信息
     * @return 结果
     */
	@Override
	public int insertGoodsType(GoodsType goodsType)
	{
	    return goodsTypeMapper.insertGoodsType(goodsType);
	}
	
	/**
     * 修改商品类型
     * 
     * @param goodsType 商品类型信息
     * @return 结果
     */
	@Override
	public int updateGoodsType(GoodsType goodsType)
	{
	    return goodsTypeMapper.updateGoodsType(goodsType);
	}

	/**
     * 删除商品类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsTypeByIds(String ids)
	{
		return goodsTypeMapper.deleteGoodsTypeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商品类型对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsTypeById(Long id)
	{
		return goodsTypeMapper.deleteGoodsTypeById(id);
	}
	
	



}
