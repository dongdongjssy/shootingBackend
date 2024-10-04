package io.goose.shooting.mapper;

import io.goose.shooting.domain.Type;
import java.util.List;	

/**
 * 类别 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface TypeMapper 
{
	/**
     * 查询类别信息
     * 
     * @param id 类别ID
     * @return 类别信息
     */
	public Type selectTypeById(Long id);
	
	/**
     * 查询类别信息 外键关联
     * 
     * @param id 类别ID
     * @return 类别信息
     */
	public Type selectTypeByIdAssoc(Long id);	

	/**
     * 查询所有类别列表
     * 
     * @param 
     * @return 类别集合
     */
	public List<Type> selectTypeAll();	
	
	/**
     * 查询所有类别列表 外键关联
     * 
     * @param 
     * @return 类别集合
     */
	public List<Type> selectTypeAllAssoc();		

	
	/**
     * 查询类别列表
     * 
     * @param type 类别信息
     * @return 类别集合
     */
	public List<Type> selectTypeList(Type type);
	
	/**
     * 查询类别列表 外键关联
     * 
     * @param type 类别信息
     * @return 类别集合
     */
	public List<Type> selectTypeListAssoc(Type type);	
	
	/**
     * 新增类别
     * 
     * @param type 类别信息
     * @return 结果
     */
	public int insertType(Type type);
	
	/**
     * 修改类别
     * 
     * @param type 类别信息
     * @return 结果
     */
	public int updateType(Type type);
	
	/**
     * 删除类别
     * 
     * @param id 类别ID
     * @return 结果
     */
	public int deleteTypeById(Long id);
	
	/**
     * 批量删除类别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTypeByIds(String[] ids);
	
}