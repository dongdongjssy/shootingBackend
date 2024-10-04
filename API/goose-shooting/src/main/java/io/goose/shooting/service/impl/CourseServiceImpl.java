package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.CourseMapper;
import io.goose.shooting.domain.Course;

import io.goose.shooting.service.ICourseService;
import io.goose.common.support.Convert;

/**
 * 科目 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class CourseServiceImpl implements ICourseService 
{
	@Autowired
	private CourseMapper courseMapper;


	/**
     * 查询科目信息
     * 
     * @param id 科目ID
     * @return 科目信息
     */
    @Override
	public Course selectCourseById(Long id)
	{
	    return courseMapper.selectCourseById(id);
	}
	
	/**
     * 查询科目信息 外键关联
     * 
     * @param id 科目ID
     * @return 科目信息
     */
    @Override
	public Course selectCourseByIdAssoc(Long id)
	{
	    return courseMapper.selectCourseByIdAssoc(id);
	}	
	
	/**
     * 查询所有科目列表
     * 
     * @param 
     * @return 科目集合
     */
	@Override 
	public List<Course> selectCourseAll()
	{
		return courseMapper.selectCourseAll();
	}	
	
	/**
     * 查询所有科目列表 外键关联
     * 
     * @param 
     * @return 科目集合
     */
	@Override 
	public List<Course> selectCourseAllAssoc()
	{
		return courseMapper.selectCourseAllAssoc();
	}		
	
	/**
     * 查询科目列表
     * 
     * @param course 科目信息
     * @return 科目集合
     */
	@Override
	public List<Course> selectCourseList(Course course)
	{
	    return courseMapper.selectCourseList(course);
	}
	
	/**
     * 查询科目列表 外键关联
     * 
     * @param course 科目信息
     * @return 科目集合
     */
	@Override
	public List<Course> selectCourseListAssoc(Course course)
	{
	    return courseMapper.selectCourseListAssoc(course);
	}	
	
    /**
     * 新增科目
     * 
     * @param course 科目信息
     * @return 结果
     */
	@Override
	public int insertCourse(Course course)
	{
	    return courseMapper.insertCourse(course);
	}
	
	/**
     * 修改科目
     * 
     * @param course 科目信息
     * @return 结果
     */
	@Override
	public int updateCourse(Course course)
	{
	    return courseMapper.updateCourse(course);
	}

	/**
     * 删除科目对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseByIds(String ids)
	{
		return courseMapper.deleteCourseByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除科目对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseById(Long id)
	{
		return courseMapper.deleteCourseById(id);
	}
	
	



}
