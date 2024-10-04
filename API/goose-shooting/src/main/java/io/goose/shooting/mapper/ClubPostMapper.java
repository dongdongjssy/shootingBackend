package io.goose.shooting.mapper;

import io.goose.shooting.domain.ClubPost;

import java.util.List;

/**
 * 俱乐部动态 数据层
 *
 * @author goose
 * @date 2020-05-09
 */
public interface ClubPostMapper {
   /**
    * 查询俱乐部动态信息
    *
    * @param id
    *       俱乐部动态ID
    * @return 俱乐部动态信息
    */
   public ClubPost selectClubPostById(Long id);

   /**
    * 查询俱乐部动态信息 外键关联
    *
    * @param id
    *       俱乐部动态ID
    * @return 俱乐部动态信息
    */
   public ClubPost selectClubPostByIdAssoc(Long id);

   /**
    * 查询所有俱乐部动态列表
    *
    * @param
    * @return 俱乐部动态集合
    */
   public List<ClubPost> selectClubPostAll();

   /**
    * 查询所有俱乐部动态列表 外键关联
    *
    * @param
    * @return 俱乐部动态集合
    */
   public List<ClubPost> selectClubPostAllAssoc();


   /**
    * 查询俱乐部动态列表
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 俱乐部动态集合
    */
   public List<ClubPost> selectClubPostList(ClubPost clubPost);

   /**
    * 查询俱乐部动态列表 外键关联
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 俱乐部动态集合
    */
   public List<ClubPost> selectClubPostListAssoc(ClubPost clubPost);

   /**
    * 新增俱乐部动态
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 结果
    */
   public int insertClubPost(ClubPost clubPost);

   /**
    * 修改俱乐部动态
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 结果
    */
   public int updateClubPost(ClubPost clubPost);

   /**
    * 删除俱乐部动态
    *
    * @param id
    *       俱乐部动态ID
    * @return 结果
    */
   public int deleteClubPostById(Long id);

   /**
    * 批量删除俱乐部动态
    *
    * @param ids
    *       需要删除的数据ID
    * @return 结果
    */
   public int deleteClubPostByIds(String[] ids);

	public List<ClubPost> selectClubPostSummaryList(Long userId);
}
