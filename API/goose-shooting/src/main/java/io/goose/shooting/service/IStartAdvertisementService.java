package io.goose.shooting.service;

import io.goose.shooting.domain.StartAdvertisement;
import java.util.List;

/**
 * 启动页广告 服务层
 * 
 * @author goose
 * @date 2021-01-06
 */
public interface IStartAdvertisementService 
{
	/**
     * 查询启动页广告信息
     * 
     * @param id 启动页广告ID
     * @return 启动页广告信息
     */
	public StartAdvertisement selectStartAdvertisementById(Long id);
	
	/**
     * 查询启动页广告信息 外键关联
     * 
     * @param id 启动页广告ID
     * @return 启动页广告信息
     */
	public StartAdvertisement selectStartAdvertisementByIdAssoc(Long id);	
	
	/**
     * 查询所有启动页广告列表
     * 
     * @param 
     * @return 启动页广告集合
     */
	public List<StartAdvertisement> selectStartAdvertisementAll();		
	
	/**
     * 查询所有启动页广告列表  外键关联
     * 
     * @param 
     * @return 启动页广告集合
     */
	public List<StartAdvertisement> selectStartAdvertisementAllAssoc();		
	
	/**
     * 查询启动页广告列表
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 启动页广告集合
     */
	public List<StartAdvertisement> selectStartAdvertisementList(StartAdvertisement startAdvertisement);
	
	/**
     * 查询启动页广告列表 外键关联
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 启动页广告集合
     */
	public List<StartAdvertisement> selectStartAdvertisementListAssoc(StartAdvertisement startAdvertisement);	
	
	/**
     * 新增启动页广告
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 结果
     */
	public int insertStartAdvertisement(StartAdvertisement startAdvertisement);
	
	/**
     * 修改启动页广告
     * 
     * @param startAdvertisement 启动页广告信息
     * @return 结果
     */
	public int updateStartAdvertisement(StartAdvertisement startAdvertisement);
		
	/**
     * 删除启动页广告信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteStartAdvertisementByIds(String ids);
	
	/**
     * 删除启动页广告信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteStartAdvertisementById(Long id);


	public List<StartAdvertisement> findDataByTime(StartAdvertisement startAdvertisement);


	List<StartAdvertisement> selectStartAdvertisementByTime();
	

  
}
