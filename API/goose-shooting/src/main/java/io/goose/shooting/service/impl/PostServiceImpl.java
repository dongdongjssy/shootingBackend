package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Post;
import io.goose.shooting.mapper.PostMapper;
import io.goose.shooting.service.IPostService;
import io.goose.shooting.service.impl.ext.ServiceUtils;


/**
 * 动态 服务层实现
 *
 * @author goose
 * @date 2020-04-28
 */
@Service
@Primary
public class PostServiceImpl implements IPostService {

   @Autowired
   public PostMapper postMapper;


   /**
    * 查询动态信息
    *
    * @param id
    *           动态ID
    * @return 动态信息
    */
   @Override
   public Post selectPostById( Long id ) {
      return postMapper.selectPostById( id );
   }


   /**
    * 查询动态信息 外键关联
    *
    * @param id
    *           动态ID
    * @return 动态信息
    */
   @Override
   public Post selectPostByIdAssoc( Long id ) {
      return postMapper.selectPostByIdAssoc( id );
   }


   /**
    * 查询所有动态列表
    *
    * @param
    * @return 动态集合
    */
   @Override
   public List<Post> selectPostAll() {
      return postMapper.selectPostAll();
   }


   /**
    * 查询所有动态列表 外键关联
    *
    * @param
    * @return 动态集合
    */
   @Override
   public List<Post> selectPostAllAssoc() {
      return postMapper.selectPostAllAssoc();
   }


   /**
    * 查询动态列表
    *
    * @param post
    *           动态信息
    * @return 动态集合
    */
   @Override
   public List<Post> selectPostList( Post post ) {
      return postMapper.selectPostList( post );
   }


   /**
    * 查询动态列表 外键关联
    *
    * @param post
    *           动态信息
    * @return 动态集合
    */
   @Override
   public List<Post> selectPostListAssoc( Post post ) {
      return postMapper.selectPostListAssoc( post );
   }


   @Override
   public List<Post> selectPostListAssocFullDisplay( Post postInput ) {
      List<Post> posts = postMapper.selectPostListAssoc( postInput );
      posts.forEach( post -> ServiceUtils.setUserFullDisplay( post.getClientUser() ) );
      return posts;
   }


   /**
    * 新增动态
    *
    * @param post
    *           动态信息
    * @return 结果
    */
   @Override
   public int insertPost( Post post ) {
      return postMapper.insertPost( post );
   }


   /**
    * 修改动态
    *
    * @param post
    *           动态信息
    * @return 结果
    */
   @Override
   public int updatePost( Post post ) {
      return postMapper.updatePost( post );
   }


   /**
    * 删除动态对象
    *
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deletePostByIds( String ids ) {
      return postMapper.deletePostByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除动态对象
    *
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deletePostById( Long id ) {
      return postMapper.deletePostById( id );
   }


   @Override
   public List<Post> selectPostSummaryList( Long userId ) {
      return postMapper.selectPostSummaryList( userId );
   }

}
