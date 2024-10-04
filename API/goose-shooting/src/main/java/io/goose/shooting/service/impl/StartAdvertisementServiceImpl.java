package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.StartAdvertisementMapper;
import io.goose.shooting.domain.StartAdvertisement;

import io.goose.shooting.service.IStartAdvertisementService;
import io.goose.common.support.Convert;

/**
 * 启动页广告 服务层实现
 * 
 * @author goose
 * @date 2021-01-06
 */
@Service
public class StartAdvertisementServiceImpl implements IStartAdvertisementService 
{
	@Autowired
	private StartAdvertisementMapper startAdvertisementMapper;


	/**
     * 查询启动页广告信息
     * 
     * @param id 启动页广告ID
     * @return 启动页广告信息
     */
    @Override
	public StartAdvertisement selectStartAdvertisementById(Long id)
	{
	    return startAdvertisementMapper.selectStartAdvertisementById(id);
	}
	
	/**
     * 查询启动页广告信息 外键关联
     * 
     * @param id 启动页广告ID
     * @return 启动页广告信息
     */
    @Override
	public StartAdvertisement selectStartAdvertisementByIdAssoc(Long id)
	{
	    return startAdvertisementMapper.selectStartAdvertisementByIdAssoc(id);
	}	
	
	/**
     * 查询所有启动页广告列表
     * 
     * @param 
     * @return 启动页广告集合
     */
	@Override 
	public List<StartAdvertisement> selectStartAdvertisementAll()
	{
		return startAdvertisementMapper.selectStartAdvertisementAll();
	}	
	
	/**
     * 查询所有启动页广告列表 外键关联
     * 
     * @param 
     * @return 启动页广告集合
     */
	@Override 
	public List<StartAdvertisement> selectStartAdvertisementAllAssoc()
	{
		return startAdvertisementMapper.selectStartAdvertisementAllAssoc();
	}		
	
	/**
     * 查询启动页广告列表
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 启动页广告集合
     */
	@Override
	public List<StartAdvertisement> selectStartAdvertisementList(StartAdvertisement startAdvertisement)
	{
	    return startAdvertisementMapper.selectStartAdvertisementList(startAdvertisement);
	}
	
	/**
     * 查询启动页广告列表 外键关联
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 启动页广告集合
     */
	@Override
	public List<StartAdvertisement> selectStartAdvertisementListAssoc(StartAdvertisement startAdvertisement)
	{
	    return startAdvertisementMapper.selectStartAdvertisementListAssoc(startAdvertisement);
	}	
	
    /**
     * 新增启动页广告
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 结果
     */
	@Override
	public int insertStartAdvertisement(StartAdvertisement startAdvertisement)
	{
	    return startAdvertisementMapper.insertStartAdvertisement(startAdvertisement);
	}
	
	/**
     * 修改启动页广告
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 结果
     */
	@Override
	public int updateStartAdvertisement(StartAdvertisement startAdvertisement)
	{
	    return startAdvertisementMapper.updateStartAdvertisement(startAdvertisement);
	}

	/**
     * 删除启动页广告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStartAdvertisementByIds(String ids)
	{
		return startAdvertisementMapper.deleteStartAdvertisementByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除启动页广告对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStartAdvertisementById(Long id)
	{
		return startAdvertisementMapper.deleteStartAdvertisementById(id);
	}

	@Override
	public List<StartAdvertisement> findDataByTime(StartAdvertisement startAdvertisement) {
		return startAdvertisementMapper.findDataByTime(startAdvertisement);
	}

	@Override
	public List<StartAdvertisement> selectStartAdvertisementByTime() {
		return startAdvertisementMapper.selectStartAdvertisementByTime();
	}


}
