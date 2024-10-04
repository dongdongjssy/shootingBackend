package io.goose.shooting.mapper;

import io.goose.shooting.domain.Carousel;
import java.util.List;	

/**
 * 轮播媒体 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface CarouselMapper 
{
	/**
     * 查询轮播媒体信息
     * 
     * @param id 轮播媒体ID
     * @return 轮播媒体信息
     */
	public Carousel selectCarouselById(Long id);
	
	/**
     * 查询轮播媒体信息 外键关联
     * 
     * @param id 轮播媒体ID
     * @return 轮播媒体信息
     */
	public Carousel selectCarouselByIdAssoc(Long id);	

	/**
     * 查询所有轮播媒体列表
     * 
     * @param 
     * @return 轮播媒体集合
     */
	public List<Carousel> selectCarouselAll();	
	
	/**
     * 查询所有轮播媒体列表 外键关联
     * 
     * @param 
     * @return 轮播媒体集合
     */
	public List<Carousel> selectCarouselAllAssoc();		

	
	/**
     * 查询轮播媒体列表
     * 
     * @param carousel 轮播媒体信息
     * @return 轮播媒体集合
     */
	public List<Carousel> selectCarouselList(Carousel carousel);
	
	/**
     * 查询轮播媒体列表 外键关联
     * 
     * @param carousel 轮播媒体信息
     * @return 轮播媒体集合
     */
	public List<Carousel> selectCarouselListAssoc(Carousel carousel);	
	
	/**
     * 新增轮播媒体
     * 
     * @param carousel 轮播媒体信息
     * @return 结果
     */
	public int insertCarousel(Carousel carousel);
	
	/**
     * 修改轮播媒体
     * 
     * @param carousel 轮播媒体信息
     * @return 结果
     */
	public int updateCarousel(Carousel carousel);
	
	/**
     * 删除轮播媒体
     * 
     * @param id 轮播媒体ID
     * @return 结果
     */
	public int deleteCarouselById(Long id);
	
	/**
     * 批量删除轮播媒体
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCarouselByIds(String[] ids);
	
}