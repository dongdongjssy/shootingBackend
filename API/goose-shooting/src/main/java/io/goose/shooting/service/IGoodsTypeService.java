package io.goose.shooting.service;

import io.goose.shooting.domain.GoodsType;
import java.util.List;

/**
 * 商品类型 服务层
 * 
 * @author goose
 * @date 2021-03-03
 */
public interface IGoodsTypeService 
{
	/**
     * 查询商品类型信息
     * 
     * @param id 商品类型ID
     * @return 商品类型信息
     */
	public GoodsType selectGoodsTypeById(Long id);
	
	/**
     * 查询商品类型信息 外键关联
     * 
     * @param id 商品类型ID
     * @return 商品类型信息
     */
	public GoodsType selectGoodsTypeByIdAssoc(Long id);	
	
	/**
     * 查询所有商品类型列表
     * 
     * @param 
     * @return 商品类型集合
     */
	public List<GoodsType> selectGoodsTypeAll();		
	
	/**
     * 查询所有商品类型列表  外键关联
     * 
     * @param 
     * @return 商品类型集合
     */
	public List<GoodsType> selectGoodsTypeAllAssoc();		
	
	/**
     * 查询商品类型列表
     * 
     * @param goodsType 商品类型信息
     * @return 商品类型集合
     */
	public List<GoodsType> selectGoodsTypeList(GoodsType goodsType);
	
	/**
     * 查询商品类型列表 外键关联
     * 
     * @param goodsType 商品类型信息
     * @return 商品类型集合
     */
	public List<GoodsType> selectGoodsTypeListAssoc(GoodsType goodsType);	
	
	/**
     * 新增商品类型
     * 
     * @param goodsType 商品类型信息
     * @return 结果
     */
	public int insertGoodsType(GoodsType goodsType);
	
	/**
     * 修改商品类型
     * 
     * @param goodsType 商品类型信息
     * @return 结果
     */
	public int updateGoodsType(GoodsType goodsType);
		
	/**
     * 删除商品类型信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsTypeByIds(String ids);
	
	/**
     * 删除商品类型信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsTypeById(Long id);
	

  
}
