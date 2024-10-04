package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Comment;
import io.goose.shooting.mapper.CommentMapper;
import io.goose.shooting.service.ICommentService;


/**
 * 评论 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
@Primary
public class CommentServiceImpl implements ICommentService {

   @Autowired
   protected CommentMapper commentMapper;


   /**
    * 查询评论信息
    * 
    * @param id
    *           评论ID
    * @return 评论信息
    */
   @Override
   public Comment selectCommentById( Long id ) {
      return commentMapper.selectCommentById( id );
   }


   /**
    * 查询评论信息 外键关联
    * 
    * @param id
    *           评论ID
    * @return 评论信息
    */
   @Override
   public Comment selectCommentByIdAssoc( Long id ) {
      return commentMapper.selectCommentByIdAssoc( id );
   }


   /**
    * 查询所有评论列表
    * 
    * @param
    * @return 评论集合
    */
   @Override
   public List<Comment> selectCommentAll() {
      return commentMapper.selectCommentAll();
   }


   /**
    * 查询所有评论列表 外键关联
    * 
    * @param
    * @return 评论集合
    */
   @Override
   public List<Comment> selectCommentAllAssoc() {
      return commentMapper.selectCommentAllAssoc();
   }


   /**
    * 查询评论列表
    * 
    * @param comment
    *           评论信息
    * @return 评论集合
    */
   @Override
   public List<Comment> selectCommentList( Comment comment ) {
      return commentMapper.selectCommentList( comment );
   }


   /**
    * 查询评论列表 外键关联
    * 
    * @param comment
    *           评论信息
    * @return 评论集合
    */
   @Override
   public List<Comment> selectCommentListAssoc( Comment comment ) {
      return commentMapper.selectCommentListAssoc( comment );
   }


   /**
    * 新增评论
    * 
    * @param comment
    *           评论信息
    * @return 结果
    */
   @Override
   public int insertComment( Comment comment ) {
      return commentMapper.insertComment( comment );
   }


   /**
    * 修改评论
    * 
    * @param comment
    *           评论信息
    * @return 结果
    */
   @Override
   public int updateComment( Comment comment ) {
      return commentMapper.updateComment( comment );
   }


   /**
    * 删除评论对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteCommentByIds( String ids ) {
      return commentMapper.deleteCommentByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除评论对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteCommentById( Long id ) {
      return commentMapper.deleteCommentById( id );
   }

}
