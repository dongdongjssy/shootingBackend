package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsAttributeMapper;
import io.goose.shooting.domain.GoodsAttribute;

import io.goose.shooting.service.IGoodsAttributeService;
import io.goose.common.support.Convert;

/**
 * 属性 服务层实现
 * 
 * @author goose
 * @date 2021-02-23
 */
@Service
public class GoodsAttributeServiceImpl implements IGoodsAttributeService 
{
	@Autowired
	private GoodsAttributeMapper goodsAttributeMapper;


	/**
     * 查询属性信息
     * 
     * @param id 属性ID
     * @return 属性信息
     */
    @Override
	public GoodsAttribute selectGoodsAttributeById(Long id)
	{
	    return goodsAttributeMapper.selectGoodsAttributeById(id);
	}
	
	/**
     * 查询属性信息 外键关联
     * 
     * @param id 属性ID
     * @return 属性信息
     */
    @Override
	public GoodsAttribute selectGoodsAttributeByIdAssoc(Long id)
	{
	    return goodsAttributeMapper.selectGoodsAttributeByIdAssoc(id);
	}	
	
	/**
     * 查询所有属性列表
     * 
     * @param 
     * @return 属性集合
     */
	@Override 
	public List<GoodsAttribute> selectGoodsAttributeAll()
	{
		return goodsAttributeMapper.selectGoodsAttributeAll();
	}	
	
	/**
     * 查询所有属性列表 外键关联
     * 
     * @param 
     * @return 属性集合
     */
	@Override 
	public List<GoodsAttribute> selectGoodsAttributeAllAssoc()
	{
		return goodsAttributeMapper.selectGoodsAttributeAllAssoc();
	}		
	
	/**
     * 查询属性列表
     * 
     * @param goodsAttribute 属性信息
     * @return 属性集合
     */
	@Override
	public List<GoodsAttribute> selectGoodsAttributeList(GoodsAttribute goodsAttribute)
	{
	    return goodsAttributeMapper.selectGoodsAttributeList(goodsAttribute);
	}
	
	/**
     * 查询属性列表 外键关联
     * 
     * @param goodsAttribute 属性信息
     * @return 属性集合
     */
	@Override
	public List<GoodsAttribute> selectGoodsAttributeListAssoc(GoodsAttribute goodsAttribute)
	{
	    return goodsAttributeMapper.selectGoodsAttributeListAssoc(goodsAttribute);
	}	
	
    /**
     * 新增属性
     * 
     * @param goodsAttribute 属性信息
     * @return 结果
     */
	@Override
	public int insertGoodsAttribute(GoodsAttribute goodsAttribute)
	{
	    return goodsAttributeMapper.insertGoodsAttribute(goodsAttribute);
	}
	
	/**
     * 修改属性
     * 
     * @param goodsAttribute 属性信息
     * @return 结果
     */
	@Override
	public int updateGoodsAttribute(GoodsAttribute goodsAttribute)
	{
	    return goodsAttributeMapper.updateGoodsAttribute(goodsAttribute);
	}

	/**
     * 删除属性对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsAttributeByIds(String ids)
	{
		return goodsAttributeMapper.deleteGoodsAttributeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除属性对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsAttributeById(Long id)
	{
		return goodsAttributeMapper.deleteGoodsAttributeById(id);
	}

	@Override
	public List<GoodsAttribute> selectGoodsAttributeByGoodsId(Long goodsId) {
		return goodsAttributeMapper.selectGoodsAttributeByGoodsId(goodsId);
	}


}
