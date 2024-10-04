package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.UserCollection;
import io.goose.shooting.mapper.UserCollectionMapper;
import io.goose.shooting.service.IUserCollectionService;


/**
 * 用户收藏 服务层实现
 * 
 * @author goose
 * @date 2020-05-21
 */
@Service
@Primary
public class UserCollectionServiceImpl implements IUserCollectionService {

   @Autowired
   private UserCollectionMapper userCollectionMapper;


   /**
    * 查询用户收藏信息
    * 
    * @param id
    *           用户收藏ID
    * @return 用户收藏信息
    */
   @Override
   public UserCollection selectUserCollectionById( Long id ) {
      return userCollectionMapper.selectUserCollectionById( id );
   }


   /**
    * 查询用户收藏信息 外键关联
    * 
    * @param id
    *           用户收藏ID
    * @return 用户收藏信息
    */
   @Override
   public UserCollection selectUserCollectionByIdAssoc( Long id ) {
      return userCollectionMapper.selectUserCollectionByIdAssoc( id );
   }


   /**
    * 查询所有用户收藏列表
    * 
    * @param
    * @return 用户收藏集合
    */
   @Override
   public List<UserCollection> selectUserCollectionAll() {
      return userCollectionMapper.selectUserCollectionAll();
   }


   /**
    * 查询所有用户收藏列表 外键关联
    * 
    * @param
    * @return 用户收藏集合
    */
   @Override
   public List<UserCollection> selectUserCollectionAllAssoc() {
      return userCollectionMapper.selectUserCollectionAllAssoc();
   }


   /**
    * 查询用户收藏列表
    * 
    * @param userCollection
    *           用户收藏信息
    * @return 用户收藏集合
    */
   @Override
   public List<UserCollection> selectUserCollectionList( UserCollection userCollection ) {
      return userCollectionMapper.selectUserCollectionList( userCollection );
   }


   /**
    * 查询用户收藏列表 外键关联
    * 
    * @param userCollection
    *           用户收藏信息
    * @return 用户收藏集合
    */
   @Override
   public List<UserCollection> selectUserCollectionListAssoc( UserCollection userCollection ) {
      return userCollectionMapper.selectUserCollectionListAssoc( userCollection );
   }


   /**
    * 新增用户收藏
    * 
    * @param userCollection
    *           用户收藏信息
    * @return 结果
    */
   @Override
   public int insertUserCollection( UserCollection userCollection ) {
      return userCollectionMapper.insertUserCollection( userCollection );
   }


   /**
    * 修改用户收藏
    * 
    * @param userCollection
    *           用户收藏信息
    * @return 结果
    */
   @Override
   public int updateUserCollection( UserCollection userCollection ) {
      return userCollectionMapper.updateUserCollection( userCollection );
   }


   /**
    * 删除用户收藏对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteUserCollectionByIds( String ids ) {
      return userCollectionMapper.deleteUserCollectionByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除用户收藏对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteUserCollectionById( Long id ) {
      return userCollectionMapper.deleteUserCollectionById( id );
   }

}
