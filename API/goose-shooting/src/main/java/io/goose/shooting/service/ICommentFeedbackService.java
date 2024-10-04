package io.goose.shooting.service;

import io.goose.shooting.domain.CommentFeedback;
import java.util.List;

/**
 * 回复 服务层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface ICommentFeedbackService 
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
     * 查询所有回复列表  外键关联
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
     * 删除回复信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommentFeedbackByIds(String ids);
	
	/**
     * 删除回复信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommentFeedbackById(Long id);
	

  
}
