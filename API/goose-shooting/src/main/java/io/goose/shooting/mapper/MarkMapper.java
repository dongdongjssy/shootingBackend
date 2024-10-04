package io.goose.shooting.mapper;

import io.goose.shooting.domain.Mark;
import java.util.List;	

/**
 * 成绩 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface MarkMapper 
{
	/**
     * 查询成绩信息
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
	public Mark selectMarkById(Long id);
	
	/**
     * 查询成绩信息 外键关联
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
	public Mark selectMarkByIdAssoc(Long id);	

	/**
     * 查询所有成绩列表
     * 
     * @param 
     * @return 成绩集合
     */
	public List<Mark> selectMarkAll();	
	
	/**
     * 查询所有成绩列表 外键关联
     * 
     * @param 
     * @return 成绩集合
     */
	public List<Mark> selectMarkAllAssoc();		

	
	/**
     * 查询成绩列表
     * 
     * @param mark 成绩信息
     * @return 成绩集合
     */
	public List<Mark> selectMarkList(Mark mark);
	
	/**
     * 查询成绩列表 外键关联
     * 
     * @param mark 成绩信息
     * @return 成绩集合
     */
	public List<Mark> selectMarkListAssoc(Mark mark);	
	
	/**
     * 新增成绩
     * 
     * @param mark 成绩信息
     * @return 结果
     */
	public int insertMark(Mark mark);
	
	/**
     * 修改成绩
     * 
     * @param mark 成绩信息
     * @return 结果
     */
	public int updateMark(Mark mark);
	
	/**
     * 删除成绩
     * 
     * @param id 成绩ID
     * @return 结果
     */
	public int deleteMarkById(Long id);
	
	/**
     * 批量删除成绩
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMarkByIds(String[] ids);
	
}