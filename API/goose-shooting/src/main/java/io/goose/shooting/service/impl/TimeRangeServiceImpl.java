package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.TimeRangeMapper;
import io.goose.shooting.domain.TimeRange;

import io.goose.shooting.service.ITimeRangeService;
import io.goose.common.support.Convert;

/**
 * 时间段 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class TimeRangeServiceImpl implements ITimeRangeService 
{
	@Autowired
	private TimeRangeMapper timeRangeMapper;


	/**
     * 查询时间段信息
     * 
     * @param id 时间段ID
     * @return 时间段信息
     */
    @Override
	public TimeRange selectTimeRangeById(Long id)
	{
	    return timeRangeMapper.selectTimeRangeById(id);
	}
	
	/**
     * 查询时间段信息 外键关联
     * 
     * @param id 时间段ID
     * @return 时间段信息
     */
    @Override
	public TimeRange selectTimeRangeByIdAssoc(Long id)
	{
	    return timeRangeMapper.selectTimeRangeByIdAssoc(id);
	}	
	
	/**
     * 查询所有时间段列表
     * 
     * @param 
     * @return 时间段集合
     */
	@Override 
	public List<TimeRange> selectTimeRangeAll()
	{
		return timeRangeMapper.selectTimeRangeAll();
	}	
	
	/**
     * 查询所有时间段列表 外键关联
     * 
     * @param 
     * @return 时间段集合
     */
	@Override 
	public List<TimeRange> selectTimeRangeAllAssoc()
	{
		return timeRangeMapper.selectTimeRangeAllAssoc();
	}		
	
	/**
     * 查询时间段列表
     * 
     * @param timeRange 时间段信息
     * @return 时间段集合
     */
	@Override
	public List<TimeRange> selectTimeRangeList(TimeRange timeRange)
	{
	    return timeRangeMapper.selectTimeRangeList(timeRange);
	}
	
	/**
     * 查询时间段列表 外键关联
     * 
     * @param timeRange 时间段信息
     * @return 时间段集合
     */
	@Override
	public List<TimeRange> selectTimeRangeListAssoc(TimeRange timeRange)
	{
	    return timeRangeMapper.selectTimeRangeListAssoc(timeRange);
	}	
	
    /**
     * 新增时间段
     * 
     * @param timeRange 时间段信息
     * @return 结果
     */
	@Override
	public int insertTimeRange(TimeRange timeRange)
	{
	    return timeRangeMapper.insertTimeRange(timeRange);
	}
	
	/**
     * 修改时间段
     * 
     * @param timeRange 时间段信息
     * @return 结果
     */
	@Override
	public int updateTimeRange(TimeRange timeRange)
	{
	    return timeRangeMapper.updateTimeRange(timeRange);
	}

	/**
     * 删除时间段对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTimeRangeByIds(String ids)
	{
		return timeRangeMapper.deleteTimeRangeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除时间段对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTimeRangeById(Long id)
	{
		return timeRangeMapper.deleteTimeRangeById(id);
	}
	
	



}
