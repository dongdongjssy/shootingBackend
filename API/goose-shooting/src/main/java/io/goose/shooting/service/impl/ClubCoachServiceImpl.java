package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.ClubCoach;
import io.goose.shooting.mapper.ClubCoachMapper;
import io.goose.shooting.service.IClubCoachService;


/**
 * 俱乐部教官 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
@Primary
public class ClubCoachServiceImpl implements IClubCoachService {

   @Autowired
   private ClubCoachMapper clubCoachMapper;


   /**
    * 查询俱乐部教官信息
    * 
    * @param id
    *           俱乐部教官ID
    * @return 俱乐部教官信息
    */
   @Override
   public ClubCoach selectClubCoachById( Long id ) {
      return clubCoachMapper.selectClubCoachById( id );
   }


   /**
    * 查询俱乐部教官信息 外键关联
    * 
    * @param id
    *           俱乐部教官ID
    * @return 俱乐部教官信息
    */
   @Override
   public ClubCoach selectClubCoachByIdAssoc( Long id ) {
      return clubCoachMapper.selectClubCoachByIdAssoc( id );
   }


   /**
    * 查询所有俱乐部教官列表
    * 
    * @param
    * @return 俱乐部教官集合
    */
   @Override
   public List<ClubCoach> selectClubCoachAll() {
      return clubCoachMapper.selectClubCoachAll();
   }


   /**
    * 查询所有俱乐部教官列表 外键关联
    * 
    * @param
    * @return 俱乐部教官集合
    */
   @Override
   public List<ClubCoach> selectClubCoachAllAssoc() {
      return clubCoachMapper.selectClubCoachAllAssoc();
   }


   /**
    * 查询俱乐部教官列表
    * 
    * @param clubCoach
    *           俱乐部教官信息
    * @return 俱乐部教官集合
    */
   @Override
   public List<ClubCoach> selectClubCoachList( ClubCoach clubCoach ) {
      return clubCoachMapper.selectClubCoachList( clubCoach );
   }


   /**
    * 查询俱乐部教官列表 外键关联
    * 
    * @param clubCoach
    *           俱乐部教官信息
    * @return 俱乐部教官集合
    */
   @Override
   public List<ClubCoach> selectClubCoachListAssoc( ClubCoach clubCoach ) {
      return clubCoachMapper.selectClubCoachListAssoc( clubCoach );
   }


   /**
    * 新增俱乐部教官
    * 
    * @param clubCoach
    *           俱乐部教官信息
    * @return 结果
    */
   @Override
   public int insertClubCoach( ClubCoach clubCoach ) {
      return clubCoachMapper.insertClubCoach( clubCoach );
   }


   /**
    * 修改俱乐部教官
    * 
    * @param clubCoach
    *           俱乐部教官信息
    * @return 结果
    */
   @Override
   public int updateClubCoach( ClubCoach clubCoach ) {
      return clubCoachMapper.updateClubCoach( clubCoach );
   }


   /**
    * 删除俱乐部教官对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubCoachByIds( String ids ) {
      return clubCoachMapper.deleteClubCoachByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除俱乐部教官对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubCoachById( Long id ) {
      return clubCoachMapper.deleteClubCoachById( id );
   }

}
