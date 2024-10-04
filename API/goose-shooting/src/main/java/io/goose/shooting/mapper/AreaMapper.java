package io.goose.shooting.mapper;

import io.goose.shooting.domain.Area;
import java.util.List;	

/**
 * 地区 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface AreaMapper 
{
	/**
     * 查询地区信息
     * 
     * @param id 地区ID
     * @return 地区信息
     */
	public Area selectAreaById(Long id);
	
	/**
     * 查询地区信息 外键关联
     * 
     * @param id 地区ID
     * @return 地区信息
     */
	public Area selectAreaByIdAssoc(Long id);	

	/**
     * 查询所有地区列表
     * 
     * @param 
     * @return 地区集合
     */
	public List<Area> selectAreaAll();	
	
	/**
     * 查询所有地区列表 外键关联
     * 
     * @param 
     * @return 地区集合
     */
	public List<Area> selectAreaAllAssoc();		

	
	/**
     * 查询地区列表
     * 
     * @param area 地区信息
     * @return 地区集合
     */
	public List<Area> selectAreaList(Area area);
	
	/**
     * 查询地区列表 外键关联
     * 
     * @param area 地区信息
     * @return 地区集合
     */
	public List<Area> selectAreaListAssoc(Area area);	
	
	/**
     * 新增地区
     * 
     * @param area 地区信息
     * @return 结果
     */
	public int insertArea(Area area);
	
	/**
     * 修改地区
     * 
     * @param area 地区信息
     * @return 结果
     */
	public int updateArea(Area area);
	
	/**
     * 删除地区
     * 
     * @param id 地区ID
     * @return 结果
     */
	public int deleteAreaById(Long id);
	
	/**
     * 批量删除地区
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAreaByIds(String[] ids);
	
}