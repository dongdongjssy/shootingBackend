package io.goose.shooting.mapper;

import io.goose.shooting.domain.GoodsCarousel;
import java.util.List;	

/**
 * 商城轮播图 数据层
 * 
 * @author goose
 * @date 2021-03-03
 */
public interface GoodsCarouselMapper 
{
	/**
     * 查询商城轮播图信息
     * 
     * @param id 商城轮播图ID
     * @return 商城轮播图信息
     */
	public GoodsCarousel selectGoodsCarouselById(Long id);
	
	/**
     * 查询商城轮播图信息 外键关联
     * 
     * @param id 商城轮播图ID
     * @return 商城轮播图信息
     */
	public GoodsCarousel selectGoodsCarouselByIdAssoc(Long id);	

	/**
     * 查询所有商城轮播图列表
     * 
     * @param 
     * @return 商城轮播图集合
     */
	public List<GoodsCarousel> selectGoodsCarouselAll();	
	
	/**
     * 查询所有商城轮播图列表 外键关联
     * 
     * @param 
     * @return 商城轮播图集合
     */
	public List<GoodsCarousel> selectGoodsCarouselAllAssoc();		

	
	/**
     * 查询商城轮播图列表
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 商城轮播图集合
     */
	public List<GoodsCarousel> selectGoodsCarouselList(GoodsCarousel goodsCarousel);
	
	/**
     * 查询商城轮播图列表 外键关联
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 商城轮播图集合
     */
	public List<GoodsCarousel> selectGoodsCarouselListAssoc(GoodsCarousel goodsCarousel);	
	
	/**
     * 新增商城轮播图
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 结果
     */
	public int insertGoodsCarousel(GoodsCarousel goodsCarousel);
	
	/**
     * 修改商城轮播图
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 结果
     */
	public int updateGoodsCarousel(GoodsCarousel goodsCarousel);
	
	/**
     * 删除商城轮播图
     * 
     * @param id 商城轮播图ID
     * @return 结果
     */
	public int deleteGoodsCarouselById(Long id);
	
	/**
     * 批量删除商城轮播图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsCarouselByIds(String[] ids);
	
}