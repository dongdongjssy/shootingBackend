package io.goose.shooting.mapper;

import io.goose.shooting.domain.Comment;
import java.util.List;	

/**
 * 评论 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface CommentMapper 
{
	/**
     * 查询评论信息
     * 
     * @param id 评论ID
     * @return 评论信息
     */
	public Comment selectCommentById(Long id);
	
	/**
     * 查询评论信息 外键关联
     * 
     * @param id 评论ID
     * @return 评论信息
     */
	public Comment selectCommentByIdAssoc(Long id);	

	/**
     * 查询所有评论列表
     * 
     * @param 
     * @return 评论集合
     */
	public List<Comment> selectCommentAll();	
	
	/**
     * 查询所有评论列表 外键关联
     * 
     * @param 
     * @return 评论集合
     */
	public List<Comment> selectCommentAllAssoc();		

	
	/**
     * 查询评论列表
     * 
     * @param comment 评论信息
     * @return 评论集合
     */
	public List<Comment> selectCommentList(Comment comment);
	
	/**
     * 查询评论列表 外键关联
     * 
     * @param comment 评论信息
     * @return 评论集合
     */
	public List<Comment> selectCommentListAssoc(Comment comment);	
	
	/**
     * 新增评论
     * 
     * @param comment 评论信息
     * @return 结果
     */
	public int insertComment(Comment comment);
	
	/**
     * 修改评论
     * 
     * @param comment 评论信息
     * @return 结果
     */
	public int updateComment(Comment comment);
	
	/**
     * 删除评论
     * 
     * @param id 评论ID
     * @return 结果
     */
	public int deleteCommentById(Long id);
	
	/**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommentByIds(String[] ids);
	
}