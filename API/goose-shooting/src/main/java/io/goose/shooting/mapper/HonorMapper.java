package io.goose.shooting.mapper;

import io.goose.shooting.domain.Honor;
import java.util.List;	

/**
 * 荣誉榜管理 数据层
 * 
 * @author goose
 * @date 2021-02-01
 */
public interface HonorMapper 
{
	/**
     * 查询荣誉榜管理信息
     * 
     * @param id 荣誉榜管理ID
     * @return 荣誉榜管理信息
     */
	public Honor selectHonorById(Long id);
	
	/**
     * 查询荣誉榜管理信息 外键关联
     * 
     * @param id 荣誉榜管理ID
     * @return 荣誉榜管理信息
     */
	public Honor selectHonorByIdAssoc(Long id);	

	/**
     * 查询所有荣誉榜管理列表
     * 
     * @param 
     * @return 荣誉榜管理集合
     */
	public List<Honor> selectHonorAll();	
	
	/**
     * 查询所有荣誉榜管理列表 外键关联
     * 
     * @param 
     * @return 荣誉榜管理集合
     */
	public List<Honor> selectHonorAllAssoc();		

	
	/**
     * 查询荣誉榜管理列表
     * 
     * @param honor 荣誉榜管理信息
     * @return 荣誉榜管理集合
     */
	public List<Honor> selectHonorList(Honor honor);
	
	/**
     * 查询荣誉榜管理列表 外键关联
     * 
     * @param honor 荣誉榜管理信息
     * @return 荣誉榜管理集合
     */
	public List<Honor> selectHonorListAssoc(Honor honor);	
	
	/**
     * 新增荣誉榜管理
     * 
     * @param honor 荣誉榜管理信息
     * @return 结果
     */
	public int insertHonor(Honor honor);
	
	/**
     * 修改荣誉榜管理
     * 
     * @param honor 荣誉榜管理信息
     * @return 结果
     */
	public int updateHonor(Honor honor);
	
	/**
     * 删除荣誉榜管理
     * 
     * @param id 荣誉榜管理ID
     * @return 结果
     */
	public int deleteHonorById(Long id);
	
	/**
     * 批量删除荣誉榜管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteHonorByIds(String[] ids);
	
}