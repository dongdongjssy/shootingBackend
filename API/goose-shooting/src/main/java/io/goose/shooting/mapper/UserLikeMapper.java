package io.goose.shooting.mapper;

import io.goose.shooting.domain.UserLike;

import java.util.List;

/**
 * 用户点赞 数据层
 *
 * @author goose
 * @date 2020-05-21
 */
public interface UserLikeMapper {
   /**
    * 查询用户点赞信息
    *
    * @param id
    *       用户点赞ID
    * @return 用户点赞信息
    */
   public UserLike selectUserLikeById(Long id);

   /**
    * 查询用户点赞信息 外键关联
    *
    * @param id
    *       用户点赞ID
    * @return 用户点赞信息
    */
   public UserLike selectUserLikeByIdAssoc(Long id);

   /**
    * 查询所有用户点赞列表
    *
    * @param
    * @return 用户点赞集合
    */
   public List<UserLike> selectUserLikeAll();

   /**
    * 查询所有用户点赞列表 外键关联
    *
    * @param
    * @return 用户点赞集合
    */
   public List<UserLike> selectUserLikeAllAssoc();


   /**
    * 查询用户点赞列表
    *
    * @param userLike
    *       用户点赞信息
    * @return 用户点赞集合
    */
   public List<UserLike> selectUserLikeList(UserLike userLike);

   /**
    * 查询用户点赞列表 外键关联
    *
    * @param userLike
    *       用户点赞信息
    * @return 用户点赞集合
    */
   public List<UserLike> selectUserLikeListAssoc(UserLike userLike);

   /**
    * 新增用户点赞
    *
    * @param userLike
    *       用户点赞信息
    * @return 结果
    */
   public int insertUserLike(UserLike userLike);

   /**
    * 修改用户点赞
    *
    * @param userLike
    *       用户点赞信息
    * @return 结果
    */
   public int updateUserLike(UserLike userLike);

   /**
    * 删除用户点赞
    *
    * @param id
    *       用户点赞ID
    * @return 结果
    */
   public int deleteUserLikeById(Long id);

   /**
    * 批量删除用户点赞
    *
    * @param ids
    *       需要删除的数据ID
    * @return 结果
    */
   public int deleteUserLikeByIds(String[] ids);

   public int deleteUserLike(UserLike userLike);
}
