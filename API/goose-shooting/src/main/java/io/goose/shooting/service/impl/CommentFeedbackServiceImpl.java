package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.CommentFeedback;
import io.goose.shooting.mapper.CommentFeedbackMapper;
import io.goose.shooting.service.ICommentFeedbackService;


/**
 * 回复 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
@Primary
public class CommentFeedbackServiceImpl implements ICommentFeedbackService {

   @Autowired
   protected CommentFeedbackMapper commentFeedbackMapper;


   /**
    * 查询回复信息
    * 
    * @param id
    *           回复ID
    * @return 回复信息
    */
   @Override
   public CommentFeedback selectCommentFeedbackById( Long id ) {
      return commentFeedbackMapper.selectCommentFeedbackById( id );
   }


   /**
    * 查询回复信息 外键关联
    * 
    * @param id
    *           回复ID
    * @return 回复信息
    */
   @Override
   public CommentFeedback selectCommentFeedbackByIdAssoc( Long id ) {
      return commentFeedbackMapper.selectCommentFeedbackByIdAssoc( id );
   }


   /**
    * 查询所有回复列表
    * 
    * @param
    * @return 回复集合
    */
   @Override
   public List<CommentFeedback> selectCommentFeedbackAll() {
      return commentFeedbackMapper.selectCommentFeedbackAll();
   }


   /**
    * 查询所有回复列表 外键关联
    * 
    * @param
    * @return 回复集合
    */
   @Override
   public List<CommentFeedback> selectCommentFeedbackAllAssoc() {
      return commentFeedbackMapper.selectCommentFeedbackAllAssoc();
   }


   /**
    * 查询回复列表
    * 
    * @param commentFeedback
    *           回复信息
    * @return 回复集合
    */
   @Override
   public List<CommentFeedback> selectCommentFeedbackList( CommentFeedback commentFeedback ) {
      return commentFeedbackMapper.selectCommentFeedbackList( commentFeedback );
   }


   /**
    * 查询回复列表 外键关联
    * 
    * @param commentFeedback
    *           回复信息
    * @return 回复集合
    */
   @Override
   public List<CommentFeedback> selectCommentFeedbackListAssoc( CommentFeedback commentFeedback ) {
      return commentFeedbackMapper.selectCommentFeedbackListAssoc( commentFeedback );
   }


   /**
    * 新增回复
    * 
    * @param commentFeedback
    *           回复信息
    * @return 结果
    */
   @Override
   public int insertCommentFeedback( CommentFeedback commentFeedback ) {
      return commentFeedbackMapper.insertCommentFeedback( commentFeedback );
   }


   /**
    * 修改回复
    * 
    * @param commentFeedback
    *           回复信息
    * @return 结果
    */
   @Override
   public int updateCommentFeedback( CommentFeedback commentFeedback ) {
      return commentFeedbackMapper.updateCommentFeedback( commentFeedback );
   }


   /**
    * 删除回复对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteCommentFeedbackByIds( String ids ) {
      return commentFeedbackMapper.deleteCommentFeedbackByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除回复对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteCommentFeedbackById( Long id ) {
      return commentFeedbackMapper.deleteCommentFeedbackById( id );
   }

}
