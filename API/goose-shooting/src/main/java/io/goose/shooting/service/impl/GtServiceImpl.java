package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GtMapper;
import io.goose.shooting.domain.Gt;

import io.goose.shooting.service.IGtService;
import io.goose.common.support.Convert;

/**
 * 商品属性关联 服务层实现
 * 
 * @author goose
 * @date 2021-03-18
 */
@Service
public class GtServiceImpl implements IGtService 
{
	@Autowired
	private GtMapper gtMapper;


	/**
     * 查询商品属性关联信息
     * 
     * @param id 商品属性关联ID
     * @return 商品属性关联信息
     */
    @Override
	public Gt selectGtById(Long id)
	{
	    return gtMapper.selectGtById(id);
	}
	
	/**
     * 查询商品属性关联信息 外键关联
     * 
     * @param id 商品属性关联ID
     * @return 商品属性关联信息
     */
    @Override
	public Gt selectGtByIdAssoc(Long id)
	{
	    return gtMapper.selectGtByIdAssoc(id);
	}	
	
	/**
     * 查询所有商品属性关联列表
     * 
     * @param 
     * @return 商品属性关联集合
     */
	@Override 
	public List<Gt> selectGtAll()
	{
		return gtMapper.selectGtAll();
	}	
	
	/**
     * 查询所有商品属性关联列表 外键关联
     * 
     * @param 
     * @return 商品属性关联集合
     */
	@Override 
	public List<Gt> selectGtAllAssoc()
	{
		return gtMapper.selectGtAllAssoc();
	}		
	
	/**
     * 查询商品属性关联列表
     * 
     * @param gt 商品属性关联信息
     * @return 商品属性关联集合
     */
	@Override
	public List<Gt> selectGtList(Gt gt)
	{
	    return gtMapper.selectGtList(gt);
	}
	
	/**
     * 查询商品属性关联列表 外键关联
     * 
     * @param gt 商品属性关联信息
     * @return 商品属性关联集合
     */
	@Override
	public List<Gt> selectGtListAssoc(Gt gt)
	{
	    return gtMapper.selectGtListAssoc(gt);
	}	
	
    /**
     * 新增商品属性关联
     * 
     * @param gt 商品属性关联信息
     * @return 结果
     */
	@Override
	public int insertGt(Gt gt)
	{
	    return gtMapper.insertGt(gt);
	}
	
	/**
     * 修改商品属性关联
     * 
     * @param gt 商品属性关联信息
     * @return 结果
     */
	@Override
	public int updateGt(Gt gt)
	{
	    return gtMapper.updateGt(gt);
	}

	/**
     * 删除商品属性关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGtByIds(String ids)
	{
		return gtMapper.deleteGtByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商品属性关联对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGtById(Long id)
	{
		return gtMapper.deleteGtById(id);
	}

	@Override
	public List<Gt> selectGtByGoodsIdAndAttribute(Gt gt) {
		return gtMapper.selectGtByGoodsIdAndAttribute(gt);
	}

	@Override
	public int updateGtNum(Gt gt) {
		return gtMapper.updateGtNum(gt);
	}


}
