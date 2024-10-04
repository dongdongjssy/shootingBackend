package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.MarkMapper;
import io.goose.shooting.domain.Mark;

import io.goose.shooting.service.IMarkService;
import io.goose.common.support.Convert;

/**
 * 成绩 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class MarkServiceImpl implements IMarkService 
{
	@Autowired
	private MarkMapper markMapper;


	/**
     * 查询成绩信息
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
    @Override
	public Mark selectMarkById(Long id)
	{
	    return markMapper.selectMarkById(id);
	}
	
	/**
     * 查询成绩信息 外键关联
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
    @Override
	public Mark selectMarkByIdAssoc(Long id)
	{
	    return markMapper.selectMarkByIdAssoc(id);
	}	
	
	/**
     * 查询所有成绩列表
     * 
     * @param 
     * @return 成绩集合
     */
	@Override 
	public List<Mark> selectMarkAll()
	{
		return markMapper.selectMarkAll();
	}	
	
	/**
     * 查询所有成绩列表 外键关联
     * 
     * @param 
     * @return 成绩集合
     */
	@Override 
	public List<Mark> selectMarkAllAssoc()
	{
		return markMapper.selectMarkAllAssoc();
	}		
	
	/**
     * 查询成绩列表
     * 
     * @param mark 成绩信息
     * @return 成绩集合
     */
	@Override
	public List<Mark> selectMarkList(Mark mark)
	{
	    return markMapper.selectMarkList(mark);
	}
	
	/**
     * 查询成绩列表 外键关联
     * 
     * @param mark 成绩信息
     * @return 成绩集合
     */
	@Override
	public List<Mark> selectMarkListAssoc(Mark mark)
	{
	    return markMapper.selectMarkListAssoc(mark);
	}	
	
    /**
     * 新增成绩
     * 
     * @param mark 成绩信息
     * @return 结果
     */
	@Override
	public int insertMark(Mark mark)
	{
	    return markMapper.insertMark(mark);
	}
	
	/**
     * 修改成绩
     * 
     * @param mark 成绩信息
     * @return 结果
     */
	@Override
	public int updateMark(Mark mark)
	{
	    return markMapper.updateMark(mark);
	}

	/**
     * 删除成绩对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMarkByIds(String ids)
	{
		return markMapper.deleteMarkByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除成绩对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMarkById(Long id)
	{
		return markMapper.deleteMarkById(id);
	}
	
	



}
