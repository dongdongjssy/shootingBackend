package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.TypeMapper;
import io.goose.shooting.domain.Type;

import io.goose.shooting.service.ITypeService;
import io.goose.common.support.Convert;

/**
 * 类别 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class TypeServiceImpl implements ITypeService 
{
	@Autowired
	private TypeMapper typeMapper;


	/**
     * 查询类别信息
     * 
     * @param id 类别ID
     * @return 类别信息
     */
    @Override
	public Type selectTypeById(Long id)
	{
	    return typeMapper.selectTypeById(id);
	}
	
	/**
     * 查询类别信息 外键关联
     * 
     * @param id 类别ID
     * @return 类别信息
     */
    @Override
	public Type selectTypeByIdAssoc(Long id)
	{
	    return typeMapper.selectTypeByIdAssoc(id);
	}	
	
	/**
     * 查询所有类别列表
     * 
     * @param 
     * @return 类别集合
     */
	@Override 
	public List<Type> selectTypeAll()
	{
		return typeMapper.selectTypeAll();
	}	
	
	/**
     * 查询所有类别列表 外键关联
     * 
     * @param 
     * @return 类别集合
     */
	@Override 
	public List<Type> selectTypeAllAssoc()
	{
		return typeMapper.selectTypeAllAssoc();
	}		
	
	/**
     * 查询类别列表
     * 
     * @param type 类别信息
     * @return 类别集合
     */
	@Override
	public List<Type> selectTypeList(Type type)
	{
	    return typeMapper.selectTypeList(type);
	}
	
	/**
     * 查询类别列表 外键关联
     * 
     * @param type 类别信息
     * @return 类别集合
     */
	@Override
	public List<Type> selectTypeListAssoc(Type type)
	{
	    return typeMapper.selectTypeListAssoc(type);
	}	
	
    /**
     * 新增类别
     * 
     * @param type 类别信息
     * @return 结果
     */
	@Override
	public int insertType(Type type)
	{
	    return typeMapper.insertType(type);
	}
	
	/**
     * 修改类别
     * 
     * @param type 类别信息
     * @return 结果
     */
	@Override
	public int updateType(Type type)
	{
	    return typeMapper.updateType(type);
	}

	/**
     * 删除类别对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTypeByIds(String ids)
	{
		return typeMapper.deleteTypeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除类别对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTypeById(Long id)
	{
		return typeMapper.deleteTypeById(id);
	}
	
	



}
