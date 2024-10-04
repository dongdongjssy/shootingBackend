package io.goose.shooting.mapper;

import io.goose.shooting.domain.CommentFeedback;
import java.util.List;	

/**
 * 回复 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface CommentFeedbackMapper 
{
	/**
     * 查询回复信息
     * 
     * @param id 回复ID
     * @return 回复信息
     */
	public CommentFeedback selectCommentFeedbackById(Long id);
	
	/**
     * 查询回复信息 外键关联
     * 
     * @param id 回复ID
     * @return 回复信息
     */
	public CommentFeedback selectCommentFeedbackByIdAssoc(Long id);	

	/**
     * 查询所有回复列表
     * 
     * @param 
     * @return 回复集合
     */
	public List<CommentFeedback> selectCommentFeedbackAll();	
	
	/**
     * 查询所有回复列表 外键关联
     * 
     * @param 
     * @return 回复集合
     */
	public List<CommentFeedback> selectCommentFeedbackAllAssoc();		

	
	/**
     * 查询回复列表
     * 
     * @param commentFeedback 回复信息
     * @return 回复集合
     */
	public List<CommentFeedback> selectCommentFeedbackList(CommentFeedback commentFeedback);
	
	/**
     * 查询回复列表 外键关联
     * 
     * @param commentFeedback 回复信息
     * @return 回复集合
     */
	public List<CommentFeedback> selectCommentFeedbackListAssoc(CommentFeedback commentFeedback);	
	
	/**
     * 新增回复
     * 
     * @param commentFeedback 回复信息
     * @return 结果
     */
	public int insertCommentFeedback(CommentFeedback commentFeedback);
	
	/**
     * 修改回复
     * 
     * @param commentFeedback 回复信息
     * @return 结果
     */
	public int updateCommentFeedback(CommentFeedback commentFeedback);
	
	/**
     * 删除回复
     * 
     * @param id 回复ID
     * @return 结果
     */
	public int deleteCommentFeedbackById(Long id);
	
	/**
     * 批量删除回复
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommentFeedbackByIds(String[] ids);
	
}