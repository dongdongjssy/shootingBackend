package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsCarouselMapper;
import io.goose.shooting.domain.GoodsCarousel;

import io.goose.shooting.service.IGoodsCarouselService;
import io.goose.common.support.Convert;

/**
 * 商城轮播图 服务层实现
 * 
 * @author goose
 * @date 2021-03-03
 */
@Service
public class GoodsCarouselServiceImpl implements IGoodsCarouselService 
{
	@Autowired
	private GoodsCarouselMapper goodsCarouselMapper;


	/**
     * 查询商城轮播图信息
     * 
     * @param id 商城轮播图ID
     * @return 商城轮播图信息
     */
    @Override
	public GoodsCarousel selectGoodsCarouselById(Long id)
	{
	    return goodsCarouselMapper.selectGoodsCarouselById(id);
	}
	
	/**
     * 查询商城轮播图信息 外键关联
     * 
     * @param id 商城轮播图ID
     * @return 商城轮播图信息
     */
    @Override
	public GoodsCarousel selectGoodsCarouselByIdAssoc(Long id)
	{
	    return goodsCarouselMapper.selectGoodsCarouselByIdAssoc(id);
	}	
	
	/**
     * 查询所有商城轮播图列表
     * 
     * @param 
     * @return 商城轮播图集合
     */
	@Override 
	public List<GoodsCarousel> selectGoodsCarouselAll()
	{
		return goodsCarouselMapper.selectGoodsCarouselAll();
	}	
	
	/**
     * 查询所有商城轮播图列表 外键关联
     * 
     * @param 
     * @return 商城轮播图集合
     */
	@Override 
	public List<GoodsCarousel> selectGoodsCarouselAllAssoc()
	{
		return goodsCarouselMapper.selectGoodsCarouselAllAssoc();
	}		
	
	/**
     * 查询商城轮播图列表
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 商城轮播图集合
     */
	@Override
	public List<GoodsCarousel> selectGoodsCarouselList(GoodsCarousel goodsCarousel)
	{
	    return goodsCarouselMapper.selectGoodsCarouselList(goodsCarousel);
	}
	
	/**
     * 查询商城轮播图列表 外键关联
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 商城轮播图集合
     */
	@Override
	public List<GoodsCarousel> selectGoodsCarouselListAssoc(GoodsCarousel goodsCarousel)
	{
	    return goodsCarouselMapper.selectGoodsCarouselListAssoc(goodsCarousel);
	}	
	
    /**
     * 新增商城轮播图
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 结果
     */
	@Override
	public int insertGoodsCarousel(GoodsCarousel goodsCarousel)
	{
	    return goodsCarouselMapper.insertGoodsCarousel(goodsCarousel);
	}
	
	/**
     * 修改商城轮播图
     * 
     * @param goodsCarousel 商城轮播图信息
     * @return 结果
     */
	@Override
	public int updateGoodsCarousel(GoodsCarousel goodsCarousel)
	{
	    return goodsCarouselMapper.updateGoodsCarousel(goodsCarousel);
	}

	/**
     * 删除商城轮播图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsCarouselByIds(String ids)
	{
		return goodsCarouselMapper.deleteGoodsCarouselByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商城轮播图对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsCarouselById(Long id)
	{
		return goodsCarouselMapper.deleteGoodsCarouselById(id);
	}
	
	



}
