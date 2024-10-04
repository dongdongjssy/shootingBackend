package io.goose.shooting.service;

import io.goose.shooting.domain.Gt;
import java.util.List;

/**
 * 商品属性关联 服务层
 * 
 * @author goose
 * @date 2021-03-18
 */
public interface IGtService 
{
	/**
     * 查询商品属性关联信息
     * 
     * @param id 商品属性关联ID
     * @return 商品属性关联信息
     */
	public Gt selectGtById(Long id);
	
	/**
     * 查询商品属性关联信息 外键关联
     * 
     * @param id 商品属性关联ID
     * @return 商品属性关联信息
     */
	public Gt selectGtByIdAssoc(Long id);	
	
	/**
     * 查询所有商品属性关联列表
     * 
     * @param 
     * @return 商品属性关联集合
     */
	public List<Gt> selectGtAll();		
	
	/**
     * 查询所有商品属性关联列表  外键关联
     * 
     * @param 
     * @return 商品属性关联集合
     */
	public List<Gt> selectGtAllAssoc();		
	
	/**
     * 查询商品属性关联列表
     * 
     * @param gt 商品属性关联信息
     * @return 商品属性关联集合
     */
	public List<Gt> selectGtList(Gt gt);
	
	/**
     * 查询商品属性关联列表 外键关联
     * 
     * @param gt 商品属性关联信息
     * @return 商品属性关联集合
     */
	public List<Gt> selectGtListAssoc(Gt gt);	
	
	/**
     * 新增商品属性关联
     * 
     * @param gt 商品属性关联信息
     * @return 结果
     */
	public int insertGt(Gt gt);
	
	/**
     * 修改商品属性关联
     * 
     * @param gt 商品属性关联信息
     * @return 结果
     */
	public int updateGt(Gt gt);
		
	/**
     * 删除商品属性关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGtByIds(String ids);
	
	/**
     * 删除商品属性关联信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGtById(Long id);



	List<Gt> selectGtByGoodsIdAndAttribute(Gt gt);


	public int updateGtNum(Gt gt);
}
