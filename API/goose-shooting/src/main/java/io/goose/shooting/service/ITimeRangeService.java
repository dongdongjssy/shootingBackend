package io.goose.shooting.service;

import io.goose.shooting.domain.TimeRange;
import java.util.List;

/**
 * 时间段 服务层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface ITimeRangeService 
{
	/**
     * 查询时间段信息
     * 
     * @param id 时间段ID
     * @return 时间段信息
     */
	public TimeRange selectTimeRangeById(Long id);
	
	/**
     * 查询时间段信息 外键关联
     * 
     * @param id 时间段ID
     * @return 时间段信息
     */
	public TimeRange selectTimeRangeByIdAssoc(Long id);	
	
	/**
     * 查询所有时间段列表
     * 
     * @param 
     * @return 时间段集合
     */
	public List<TimeRange> selectTimeRangeAll();		
	
	/**
     * 查询所有时间段列表  外键关联
     * 
     * @param 
     * @return 时间段集合
     */
	public List<TimeRange> selectTimeRangeAllAssoc();		
	
	/**
     * 查询时间段列表
     * 
     * @param timeRange 时间段信息
     * @return 时间段集合
     */
	public List<TimeRange> selectTimeRangeList(TimeRange timeRange);
	
	/**
     * 查询时间段列表 外键关联
     * 
     * @param timeRange 时间段信息
     * @return 时间段集合
     */
	public List<TimeRange> selectTimeRangeListAssoc(TimeRange timeRange);	
	
	/**
     * 新增时间段
     * 
     * @param timeRange 时间段信息
     * @return 结果
     */
	public int insertTimeRange(TimeRange timeRange);
	
	/**
     * 修改时间段
     * 
     * @param timeRange 时间段信息
     * @return 结果
     */
	public int updateTimeRange(TimeRange timeRange);
		
	/**
     * 删除时间段信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTimeRangeByIds(String ids);
	
	/**
     * 删除时间段信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteTimeRangeById(Long id);
	

  
}
