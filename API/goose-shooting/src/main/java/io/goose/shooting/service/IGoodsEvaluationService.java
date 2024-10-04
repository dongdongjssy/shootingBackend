package io.goose.shooting.service;

import io.goose.shooting.domain.GoodsEvaluation;
import java.util.List;

/**
 * 商品评价 服务层
 * 
 * @author goose
 * @date 2021-02-23
 */
public interface IGoodsEvaluationService 
{
	/**
     * 查询商品评价信息
     * 
     * @param id 商品评价ID
     * @return 商品评价信息
     */
	public GoodsEvaluation selectGoodsEvaluationById(Long id);
	
	/**
     * 查询商品评价信息 外键关联
     * 
     * @param id 商品评价ID
     * @return 商品评价信息
     */
	public GoodsEvaluation selectGoodsEvaluationByIdAssoc(Long id);	
	
	/**
     * 查询所有商品评价列表
     * 
     * @param 
     * @return 商品评价集合
     */
	public List<GoodsEvaluation> selectGoodsEvaluationAll();		
	
	/**
     * 查询所有商品评价列表  外键关联
     * 
     * @param 
     * @return 商品评价集合
     */
	public List<GoodsEvaluation> selectGoodsEvaluationAllAssoc();		
	
	/**
     * 查询商品评价列表
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 商品评价集合
     */
	public List<GoodsEvaluation> selectGoodsEvaluationList(GoodsEvaluation goodsEvaluation);
	
	/**
     * 查询商品评价列表 外键关联
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 商品评价集合
     */
	public List<GoodsEvaluation> selectGoodsEvaluationListAssoc(GoodsEvaluation goodsEvaluation);	
	
	/**
     * 新增商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	public int insertGoodsEvaluation(GoodsEvaluation goodsEvaluation);
	
	/**
     * 修改商品评价
     * 
     * @param goodsEvaluation 商品评价信息
     * @return 结果
     */
	public int updateGoodsEvaluation(GoodsEvaluation goodsEvaluation);
		
	/**
     * 删除商品评价信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsEvaluationByIds(String ids);
	
	/**
     * 删除商品评价信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsEvaluationById(Long id);
	

  
}
