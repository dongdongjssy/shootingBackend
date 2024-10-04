package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.CarouselMapper;
import io.goose.shooting.domain.Carousel;

import io.goose.shooting.service.ICarouselService;
import io.goose.common.support.Convert;

/**
 * 轮播媒体 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class CarouselServiceImpl implements ICarouselService 
{
	@Autowired
	private CarouselMapper carouselMapper;


	/**
     * 查询轮播媒体信息
     * 
     * @param id 轮播媒体ID
     * @return 轮播媒体信息
     */
    @Override
	public Carousel selectCarouselById(Long id)
	{
	    return carouselMapper.selectCarouselById(id);
	}
	
	/**
     * 查询轮播媒体信息 外键关联
     * 
     * @param id 轮播媒体ID
     * @return 轮播媒体信息
     */
    @Override
	public Carousel selectCarouselByIdAssoc(Long id)
	{
	    return carouselMapper.selectCarouselByIdAssoc(id);
	}	
	
	/**
     * 查询所有轮播媒体列表
     * 
     * @param 
     * @return 轮播媒体集合
     */
	@Override 
	public List<Carousel> selectCarouselAll()
	{
		return carouselMapper.selectCarouselAll();
	}	
	
	/**
     * 查询所有轮播媒体列表 外键关联
     * 
     * @param 
     * @return 轮播媒体集合
     */
	@Override 
	public List<Carousel> selectCarouselAllAssoc()
	{
		return carouselMapper.selectCarouselAllAssoc();
	}		
	
	/**
     * 查询轮播媒体列表
     * 
     * @param carousel 轮播媒体信息
     * @return 轮播媒体集合
     */
	@Override
	public List<Carousel> selectCarouselList(Carousel carousel)
	{
	    return carouselMapper.selectCarouselList(carousel);
	}
	
	/**
     * 查询轮播媒体列表 外键关联
     * 
     * @param carousel 轮播媒体信息
     * @return 轮播媒体集合
     */
	@Override
	public List<Carousel> selectCarouselListAssoc(Carousel carousel)
	{
	    return carouselMapper.selectCarouselListAssoc(carousel);
	}	
	
    /**
     * 新增轮播媒体
     * 
     * @param carousel 轮播媒体信息
     * @return 结果
     */
	@Override
	public int insertCarousel(Carousel carousel)
	{
	    return carouselMapper.insertCarousel(carousel);
	}
	
	/**
     * 修改轮播媒体
     * 
     * @param carousel 轮播媒体信息
     * @return 结果
     */
	@Override
	public int updateCarousel(Carousel carousel)
	{
	    return carouselMapper.updateCarousel(carousel);
	}

	/**
     * 删除轮播媒体对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCarouselByIds(String ids)
	{
		return carouselMapper.deleteCarouselByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除轮播媒体对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCarouselById(Long id)
	{
		return carouselMapper.deleteCarouselById(id);
	}
	
	



}
