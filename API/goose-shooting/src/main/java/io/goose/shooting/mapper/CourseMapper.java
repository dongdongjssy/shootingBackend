package io.goose.shooting.mapper;

import io.goose.shooting.domain.Course;
import java.util.List;	

/**
 * 科目 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface CourseMapper 
{
	/**
     * 查询科目信息
     * 
     * @param id 科目ID
     * @return 科目信息
     */
	public Course selectCourseById(Long id);
	
	/**
     * 查询科目信息 外键关联
     * 
     * @param id 科目ID
     * @return 科目信息
     */
	public Course selectCourseByIdAssoc(Long id);	

	/**
     * 查询所有科目列表
     * 
     * @param 
     * @return 科目集合
     */
	public List<Course> selectCourseAll();	
	
	/**
     * 查询所有科目列表 外键关联
     * 
     * @param 
     * @return 科目集合
     */
	public List<Course> selectCourseAllAssoc();		

	
	/**
     * 查询科目列表
     * 
     * @param course 科目信息
     * @return 科目集合
     */
	public List<Course> selectCourseList(Course course);
	
	/**
     * 查询科目列表 外键关联
     * 
     * @param course 科目信息
     * @return 科目集合
     */
	public List<Course> selectCourseListAssoc(Course course);	
	
	/**
     * 新增科目
     * 
     * @param course 科目信息
     * @return 结果
     */
	public int insertCourse(Course course);
	
	/**
     * 修改科目
     * 
     * @param course 科目信息
     * @return 结果
     */
	public int updateCourse(Course course);
	
	/**
     * 删除科目
     * 
     * @param id 科目ID
     * @return 结果
     */
	public int deleteCourseById(Long id);
	
	/**
     * 批量删除科目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseByIds(String[] ids);
	
}