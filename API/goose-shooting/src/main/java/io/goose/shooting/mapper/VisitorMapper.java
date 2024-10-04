package io.goose.shooting.mapper;

import io.goose.shooting.domain.Visitor;
import java.util.List;	

/**
 * 访客 数据层
 * 
 * @author goose
 * @date 2020-12-24
 */
public interface VisitorMapper 
{
	/**
     * 查询访客信息
     * 
     * @param id 访客ID
     * @return 访客信息
     */
	public Visitor selectVisitorById(Long id);
	
	/**
     * 查询访客信息 外键关联
     * 
     * @param id 访客ID
     * @return 访客信息
     */
	public Visitor selectVisitorByIdAssoc(Long id);

	public Visitor selectVisitorByEmail(String email);

	/**
     * 查询所有访客列表
     * 
     * @param 
     * @return 访客集合
     */
	public List<Visitor> selectVisitorAll();	
	
	/**
     * 查询所有访客列表 外键关联
     * 
     * @param 
     * @return 访客集合
     */
	public List<Visitor> selectVisitorAllAssoc();		

	
	/**
     * 查询访客列表
     * 
     * @param visitor 访客信息
     * @return 访客集合
     */
	public List<Visitor> selectVisitorList(Visitor visitor);
	
	/**
     * 查询访客列表 外键关联
     * 
     * @param visitor 访客信息
     * @return 访客集合
     */
	public List<Visitor> selectVisitorListAssoc(Visitor visitor);	
	
	/**
     * 新增访客
     * 
     * @param visitor 访客信息
     * @return 结果
     */
	public int insertVisitor(Visitor visitor);
	
	/**
     * 修改访客
     * 
     * @param visitor 访客信息
     * @return 结果
     */
	public int updateVisitor(Visitor visitor);
	
	/**
     * 删除访客
     * 
     * @param id 访客ID
     * @return 结果
     */
	public int deleteVisitorById(Long id);
	
	/**
     * 批量删除访客
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVisitorByIds(String[] ids);
	
}