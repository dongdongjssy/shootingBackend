package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Coach;
import io.goose.shooting.mapper.CoachMapper;
import io.goose.shooting.service.ICoachService;


/**
 * 教官 服务层实现
 * 
 * @author goose
 * @date 2020-05-21
 */
@Service
@Primary
public class CoachServiceImpl implements ICoachService {

   @Autowired
   private CoachMapper coachMapper;


   /**
    * 查询教官信息
    * 
    * @param id
    *           教官ID
    * @return 教官信息
    */
   @Override
   public Coach selectCoachById( Long id ) {
      return coachMapper.selectCoachById( id );
   }


   /**
    * 查询教官信息 外键关联
    * 
    * @param id
    *           教官ID
    * @return 教官信息
    */
   @Override
   public Coach selectCoachByIdAssoc( Long id ) {
      return coachMapper.selectCoachByIdAssoc( id );
   }


   /**
    * 查询所有教官列表
    * 
    * @param
    * @return 教官集合
    */
   @Override
   public List<Coach> selectCoachAll() {
      return coachMapper.selectCoachAll();
   }


   /**
    * 查询所有教官列表 外键关联
    * 
    * @param
    * @return 教官集合
    */
   @Override
   public List<Coach> selectCoachAllAssoc() {
      return coachMapper.selectCoachAllAssoc();
   }


   /**
    * 查询教官列表
    * 
    * @param coach
    *           教官信息
    * @return 教官集合
    */
   @Override
   public List<Coach> selectCoachList( Coach coach ) {
      return coachMapper.selectCoachList( coach );
   }


   /**
    * 查询教官列表 外键关联
    * 
    * @param coach
    *           教官信息
    * @return 教官集合
    */
   @Override
   public List<Coach> selectCoachListAssoc( Coach coach ) {
      return coachMapper.selectCoachListAssoc( coach );
   }


   /**
    * 新增教官
    * 
    * @param coach
    *           教官信息
    * @return 结果
    */
   @Override
   public int insertCoach( Coach coach ) {
      return coachMapper.insertCoach( coach );
   }


   /**
    * 修改教官
    * 
    * @param coach
    *           教官信息
    * @return 结果
    */
   @Override
   public int updateCoach( Coach coach ) {
      return coachMapper.updateCoach( coach );
   }


   /**
    * 删除教官对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteCoachByIds( String ids ) {
      return coachMapper.deleteCoachByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除教官对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteCoachById( Long id ) {
      return coachMapper.deleteCoachById( id );
   }

}
