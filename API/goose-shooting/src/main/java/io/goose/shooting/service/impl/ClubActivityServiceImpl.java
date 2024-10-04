package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.ClubActivity;
import io.goose.shooting.mapper.ClubActivityMapper;
import io.goose.shooting.service.IClubActivityService;


/**
 * 俱乐部活动 服务层实现
 * 
 * @author goose
 * @date 2020-05-03
 */
@Service
@Primary
public class ClubActivityServiceImpl implements IClubActivityService {

   @Autowired
   private ClubActivityMapper clubActivityMapper;


   /**
    * 查询俱乐部活动信息
    * 
    * @param id
    *           俱乐部活动ID
    * @return 俱乐部活动信息
    */
   @Override
   public ClubActivity selectClubActivityById( Long id ) {
      return clubActivityMapper.selectClubActivityById( id );
   }


   /**
    * 查询俱乐部活动信息 外键关联
    * 
    * @param id
    *           俱乐部活动ID
    * @return 俱乐部活动信息
    */
   @Override
   public ClubActivity selectClubActivityByIdAssoc( Long id ) {
      return clubActivityMapper.selectClubActivityByIdAssoc( id );
   }


   /**
    * 查询所有俱乐部活动列表
    * 
    * @param
    * @return 俱乐部活动集合
    */
   @Override
   public List<ClubActivity> selectClubActivityAll() {
      return clubActivityMapper.selectClubActivityAll();
   }


   /**
    * 查询所有俱乐部活动列表 外键关联
    * 
    * @param
    * @return 俱乐部活动集合
    */
   @Override
   public List<ClubActivity> selectClubActivityAllAssoc() {
      return clubActivityMapper.selectClubActivityAllAssoc();
   }


   /**
    * 查询俱乐部活动列表
    * 
    * @param clubActivity
    *           俱乐部活动信息
    * @return 俱乐部活动集合
    */
   @Override
   public List<ClubActivity> selectClubActivityList( ClubActivity clubActivity ) {
      return clubActivityMapper.selectClubActivityList( clubActivity );
   }


   /**
    * 查询俱乐部活动列表 外键关联
    * 
    * @param clubActivity
    *           俱乐部活动信息
    * @return 俱乐部活动集合
    */
   @Override
   public List<ClubActivity> selectClubActivityListAssoc( ClubActivity clubActivity ) {
      return clubActivityMapper.selectClubActivityListAssoc( clubActivity );
   }


   /**
    * 新增俱乐部活动
    * 
    * @param clubActivity
    *           俱乐部活动信息
    * @return 结果
    */
   @Override
   public int insertClubActivity( ClubActivity clubActivity ) {
      return clubActivityMapper.insertClubActivity( clubActivity );
   }


   /**
    * 修改俱乐部活动
    * 
    * @param clubActivity
    *           俱乐部活动信息
    * @return 结果
    */
   @Override
   public int updateClubActivity( ClubActivity clubActivity ) {
      return clubActivityMapper.updateClubActivity( clubActivity );
   }


   /**
    * 删除俱乐部活动对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubActivityByIds( String ids ) {
      return clubActivityMapper.deleteClubActivityByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除俱乐部活动对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubActivityById( Long id ) {
      return clubActivityMapper.deleteClubActivityById( id );
   }

}
