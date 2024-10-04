package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.MyClub;
import io.goose.shooting.mapper.MyClubMapper;
import io.goose.shooting.service.IMyClubService;


/**
 * 我关注的俱乐部 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
@Primary
public class MyClubServiceImpl implements IMyClubService {

   @Autowired
   private MyClubMapper myClubMapper;


   /**
    * 查询我关注的俱乐部信息
    * 
    * @param id
    *           我关注的俱乐部ID
    * @return 我关注的俱乐部信息
    */
   @Override
   public MyClub selectMyClubById( Long id ) {
      return myClubMapper.selectMyClubById( id );
   }


   /**
    * 查询我关注的俱乐部信息 外键关联
    * 
    * @param id
    *           我关注的俱乐部ID
    * @return 我关注的俱乐部信息
    */
   @Override
   public MyClub selectMyClubByIdAssoc( Long id ) {
      return myClubMapper.selectMyClubByIdAssoc( id );
   }


   /**
    * 查询所有我关注的俱乐部列表
    * 
    * @param
    * @return 我关注的俱乐部集合
    */
   @Override
   public List<MyClub> selectMyClubAll() {
      return myClubMapper.selectMyClubAll();
   }


   /**
    * 查询所有我关注的俱乐部列表 外键关联
    * 
    * @param
    * @return 我关注的俱乐部集合
    */
   @Override
   public List<MyClub> selectMyClubAllAssoc() {
      return myClubMapper.selectMyClubAllAssoc();
   }


   /**
    * 查询我关注的俱乐部列表
    * 
    * @param myClub
    *           我关注的俱乐部信息
    * @return 我关注的俱乐部集合
    */
   @Override
   public List<MyClub> selectMyClubList( MyClub myClub ) {
      return myClubMapper.selectMyClubList( myClub );
   }


   /**
    * 查询我关注的俱乐部列表 外键关联
    * 
    * @param myClub
    *           我关注的俱乐部信息
    * @return 我关注的俱乐部集合
    */
   @Override
   public List<MyClub> selectMyClubListAssoc( MyClub myClub ) {
      return myClubMapper.selectMyClubListAssoc( myClub );
   }


   /**
    * 新增我关注的俱乐部
    * 
    * @param myClub
    *           我关注的俱乐部信息
    * @return 结果
    */
   @Override
   public int insertMyClub( MyClub myClub ) {
      return myClubMapper.insertMyClub( myClub );
   }


   /**
    * 修改我关注的俱乐部
    * 
    * @param myClub
    *           我关注的俱乐部信息
    * @return 结果
    */
   @Override
   public int updateMyClub( MyClub myClub ) {
      return myClubMapper.updateMyClub( myClub );
   }


   /**
    * 删除我关注的俱乐部对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteMyClubByIds( String ids ) {
      return myClubMapper.deleteMyClubByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除我关注的俱乐部对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteMyClubById( Long id ) {
      return myClubMapper.deleteMyClubById( id );
   }
   
   @Override
   public int deleteMyClubByClientUserId( Long clientUserId ) {
      return myClubMapper.deleteMyClubByClientUserId( clientUserId );
   }

}
