package io.goose.shooting.service;

import java.util.List;

import io.goose.shooting.domain.Post;


/**
 * 动态 服务层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface IPostService {

   /**
    * 查询动态信息
    *
    * @param id
    *           动态ID
    * @return 动态信息
    */
   public Post selectPostById( Long id );


   /**
    * 查询动态信息 外键关联
    *
    * @param id
    *           动态ID
    * @return 动态信息
    */
   public Post selectPostByIdAssoc( Long id );


   /**
    * 查询所有动态列表
    *
    * @param
    * @return 动态集合
    */
   public List<Post> selectPostAll();


   /**
    * 查询所有动态列表 外键关联
    *
    * @param
    * @return 动态集合
    */
   public List<Post> selectPostAllAssoc();


   /**
    * 查询动态列表
    *
    * @param post
    *           动态信息
    * @return 动态集合
    */
   public List<Post> selectPostList( Post post );


   /**
    * 查询动态列表 外键关联
    *
    * @param post
    *           动态信息
    * @return 动态集合
    */
   public List<Post> selectPostListAssoc( Post post );


   public List<Post> selectPostListAssocFullDisplay( Post post );


   /**
    * 新增动态
    *
    * @param post
    *           动态信息
    * @return 结果
    */
   public int insertPost( Post post );


   /**
    * 修改动态
    *
    * @param post
    *           动态信息
    * @return 结果
    */
   public int updatePost( Post post );


   /**
    * 删除动态信息
    *
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   public int deletePostByIds( String ids );


   /**
    * 删除动态信息
    *
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   public int deletePostById( Long id );


   public List<Post> selectPostSummaryList( Long userId );
}
