package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.AreaMapper;
import io.goose.shooting.domain.Area;

import io.goose.shooting.service.IAreaService;
import io.goose.common.support.Convert;

/**
 * 地区 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class AreaServiceImpl implements IAreaService 
{
	@Autowired
	private AreaMapper areaMapper;


	/**
     * 查询地区信息
     * 
     * @param id 地区ID
     * @return 地区信息
     */
    @Override
	public Area selectAreaById(Long id)
	{
	    return areaMapper.selectAreaById(id);
	}
	
	/**
     * 查询地区信息 外键关联
     * 
     * @param id 地区ID
     * @return 地区信息
     */
    @Override
	public Area selectAreaByIdAssoc(Long id)
	{
	    return areaMapper.selectAreaByIdAssoc(id);
	}	
	
	/**
     * 查询所有地区列表
     * 
     * @param 
     * @return 地区集合
     */
	@Override 
	public List<Area> selectAreaAll()
	{
		return areaMapper.selectAreaAll();
	}	
	
	/**
     * 查询所有地区列表 外键关联
     * 
     * @param 
     * @return 地区集合
     */
	@Override 
	public List<Area> selectAreaAllAssoc()
	{
		return areaMapper.selectAreaAllAssoc();
	}		
	
	/**
     * 查询地区列表
     * 
     * @param area 地区信息
     * @return 地区集合
     */
	@Override
	public List<Area> selectAreaList(Area area)
	{
	    return areaMapper.selectAreaList(area);
	}
	
	/**
     * 查询地区列表 外键关联
     * 
     * @param area 地区信息
     * @return 地区集合
     */
	@Override
	public List<Area> selectAreaListAssoc(Area area)
	{
	    return areaMapper.selectAreaListAssoc(area);
	}	
	
    /**
     * 新增地区
     * 
     * @param area 地区信息
     * @return 结果
     */
	@Override
	public int insertArea(Area area)
	{
	    return areaMapper.insertArea(area);
	}
	
	/**
     * 修改地区
     * 
     * @param area 地区信息
     * @return 结果
     */
	@Override
	public int updateArea(Area area)
	{
	    return areaMapper.updateArea(area);
	}

	/**
     * 删除地区对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAreaByIds(String ids)
	{
		return areaMapper.deleteAreaByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除地区对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAreaById(Long id)
	{
		return areaMapper.deleteAreaById(id);
	}
	
	



}
