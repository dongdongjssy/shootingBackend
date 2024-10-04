package io.goose.shooting.service.impl;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.UserLike;
import io.goose.shooting.mapper.UserLikeMapper;
import io.goose.shooting.service.IUserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户点赞 服务层实现
 *
 * @author goose
 * @date 2020-05-21
 */
@Service
public class UserLikeServiceImpl implements IUserLikeService {
   @Autowired
   private UserLikeMapper userLikeMapper;


   /**
    * 查询用户点赞信息
    *
    * @param id
    *       用户点赞ID
    * @return 用户点赞信息
    */
   @Override
   public UserLike selectUserLikeById(Long id) {
      return userLikeMapper.selectUserLikeById(id);
   }

   /**
    * 查询用户点赞信息 外键关联
    *
    * @param id
    *       用户点赞ID
    * @return 用户点赞信息
    */
   @Override
   public UserLike selectUserLikeByIdAssoc(Long id) {
      return userLikeMapper.selectUserLikeByIdAssoc(id);
   }

   /**
    * 查询所有用户点赞列表
    *
    * @param
    * @return 用户点赞集合
    */
   @Override
   public List<UserLike> selectUserLikeAll() {
      return userLikeMapper.selectUserLikeAll();
   }

   /**
    * 查询所有用户点赞列表 外键关联
    *
    * @param
    * @return 用户点赞集合
    */
   @Override
   public List<UserLike> selectUserLikeAllAssoc() {
      return userLikeMapper.selectUserLikeAllAssoc();
   }

   /**
    * 查询用户点赞列表
    *
    * @param userLike
    *       用户点赞信息
    * @return 用户点赞集合
    */
   @Override
   public List<UserLike> selectUserLikeList(UserLike userLike) {
      return userLikeMapper.selectUserLikeList(userLike);
   }

   /**
    * 查询用户点赞列表 外键关联
    *
    * @param userLike
    *       用户点赞信息
    * @return 用户点赞集合
    */
   @Override
   public List<UserLike> selectUserLikeListAssoc(UserLike userLike) {
      return userLikeMapper.selectUserLikeListAssoc(userLike);
   }

   /**
    * 新增用户点赞
    *
    * @param userLike
    *       用户点赞信息
    * @return 结果
    */
   @Override
   public int insertUserLike(UserLike userLike) {
      return userLikeMapper.insertUserLike(userLike);
   }

   /**
    * 修改用户点赞
    *
    * @param userLike
    *       用户点赞信息
    * @return 结果
    */
   @Override
   public int updateUserLike(UserLike userLike) {
      return userLikeMapper.updateUserLike(userLike);
   }

   /**
    * 删除用户点赞对象
    *
    * @param ids
    *       需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteUserLikeByIds(String ids) {
      return userLikeMapper.deleteUserLikeByIds(Convert.toStrArray(ids));
   }

   /**
    * 删除用户点赞对象
    *
    * @param id
    *       需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteUserLikeById(Long id) {
      return userLikeMapper.deleteUserLikeById(id);
   }

   @Override
   public int deleteUserLike(UserLike userLike) {
      return userLikeMapper.deleteUserLike(userLike);
   }


}
