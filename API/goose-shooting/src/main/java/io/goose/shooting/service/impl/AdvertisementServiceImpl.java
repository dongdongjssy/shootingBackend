package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.AdvertisementMapper;
import io.goose.shooting.domain.Advertisement;

import io.goose.shooting.service.IAdvertisementService;
import io.goose.common.support.Convert;

/**
 * 广告 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class AdvertisementServiceImpl implements IAdvertisementService 
{
	@Autowired
	private AdvertisementMapper advertisementMapper;


	/**
     * 查询广告信息
     * 
     * @param id 广告ID
     * @return 广告信息
     */
    @Override
	public Advertisement selectAdvertisementById(Long id)
	{
	    return advertisementMapper.selectAdvertisementById(id);
	}
	
	/**
     * 查询广告信息 外键关联
     * 
     * @param id 广告ID
     * @return 广告信息
     */
    @Override
	public Advertisement selectAdvertisementByIdAssoc(Long id)
	{
	    return advertisementMapper.selectAdvertisementByIdAssoc(id);
	}	
	
	/**
     * 查询所有广告列表
     * 
     * @param 
     * @return 广告集合
     */
	@Override 
	public List<Advertisement> selectAdvertisementAll()
	{
		return advertisementMapper.selectAdvertisementAll();
	}	
	
	/**
     * 查询所有广告列表 外键关联
     * 
     * @param 
     * @return 广告集合
     */
	@Override 
	public List<Advertisement> selectAdvertisementAllAssoc()
	{
		return advertisementMapper.selectAdvertisementAllAssoc();
	}		
	
	/**
     * 查询广告列表
     * 
     * @param advertisement 广告信息
     * @return 广告集合
     */
	@Override
	public List<Advertisement> selectAdvertisementList(Advertisement advertisement)
	{
	    return advertisementMapper.selectAdvertisementList(advertisement);
	}
	
	/**
     * 查询广告列表 外键关联
     * 
     * @param advertisement 广告信息
     * @return 广告集合
     */
	@Override
	public List<Advertisement> selectAdvertisementListAssoc(Advertisement advertisement)
	{
	    return advertisementMapper.selectAdvertisementListAssoc(advertisement);
	}	
	
    /**
     * 新增广告
     * 
     * @param advertisement 广告信息
     * @return 结果
     */
	@Override
	public int insertAdvertisement(Advertisement advertisement)
	{
	    return advertisementMapper.insertAdvertisement(advertisement);
	}
	
	/**
     * 修改广告
     * 
     * @param advertisement 广告信息
     * @return 结果
     */
	@Override
	public int updateAdvertisement(Advertisement advertisement)
	{
	    return advertisementMapper.updateAdvertisement(advertisement);
	}

	/**
     * 删除广告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdvertisementByIds(String ids)
	{
		return advertisementMapper.deleteAdvertisementByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除广告对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdvertisementById(Long id)
	{
		return advertisementMapper.deleteAdvertisementById(id);
	}
	
	



}
