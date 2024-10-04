package io.goose.shooting.service;

import io.goose.shooting.domain.GoodsAttribute;
import java.util.List;

/**
 * 属性 服务层
 * 
 * @author goose
 * @date 2021-02-23
 */
public interface IGoodsAttributeService 
{
	/**
     * 查询属性信息
     * 
     * @param id 属性ID
     * @return 属性信息
     */
	public GoodsAttribute selectGoodsAttributeById(Long id);
	
	/**
     * 查询属性信息 外键关联
     * 
     * @param id 属性ID
     * @return 属性信息
     */
	public GoodsAttribute selectGoodsAttributeByIdAssoc(Long id);	
	
	/**
     * 查询所有属性列表
     * 
     * @param 
     * @return 属性集合
     */
	public List<GoodsAttribute> selectGoodsAttributeAll();		
	
	/**
     * 查询所有属性列表  外键关联
     * 
     * @param 
     * @return 属性集合
     */
	public List<GoodsAttribute> selectGoodsAttributeAllAssoc();		
	
	/**
     * 查询属性列表
     * 
     * @param goodsAttribute 属性信息
     * @return 属性集合
     */
	public List<GoodsAttribute> selectGoodsAttributeList(GoodsAttribute goodsAttribute);
	
	/**
     * 查询属性列表 外键关联
     * 
     * @param goodsAttribute 属性信息
     * @return 属性集合
     */
	public List<GoodsAttribute> selectGoodsAttributeListAssoc(GoodsAttribute goodsAttribute);	
	
	/**
     * 新增属性
     * 
     * @param goodsAttribute 属性信息
     * @return 结果
     */
	public int insertGoodsAttribute(GoodsAttribute goodsAttribute);
	
	/**
     * 修改属性
     * 
     * @param goodsAttribute 属性信息
     * @return 结果
     */
	public int updateGoodsAttribute(GoodsAttribute goodsAttribute);
		
	/**
     * 删除属性信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsAttributeByIds(String ids);
	
	/**
     * 删除属性信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsAttributeById(Long id);



	public List<GoodsAttribute> selectGoodsAttributeByGoodsId(Long goodsId);
	

  
}
