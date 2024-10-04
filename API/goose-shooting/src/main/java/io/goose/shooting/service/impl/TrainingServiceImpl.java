package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Training;
import io.goose.shooting.mapper.TrainingMapper;
import io.goose.shooting.service.ITrainingService;


/**
 * 培训 服务层实现
 * 
 * @author goose
 * @date 2020-05-03
 */
@Service
public class TrainingServiceImpl implements ITrainingService {

   @Autowired
   private TrainingMapper trainingMapper;


   /**
    * 查询培训信息
    * 
    * @param id
    *           培训ID
    * @return 培训信息
    */
   @Override
   public Training selectTrainingById( Long id ) {
      return trainingMapper.selectTrainingById( id );
   }


   /**
    * 查询培训信息 外键关联
    * 
    * @param id
    *           培训ID
    * @return 培训信息
    */
   @Override
   public Training selectTrainingByIdAssoc( Long id ) {
      return trainingMapper.selectTrainingByIdAssoc( id );
   }


   /**
    * 查询所有培训列表
    * 
    * @param
    * @return 培训集合
    */
   @Override
   public List<Training> selectTrainingAll() {
      return trainingMapper.selectTrainingAll();
   }


   /**
    * 查询所有培训列表 外键关联
    * 
    * @param
    * @return 培训集合
    */
   @Override
   public List<Training> selectTrainingAllAssoc() {
      return trainingMapper.selectTrainingAllAssoc();
   }


   /**
    * 查询培训列表
    * 
    * @param training
    *           培训信息
    * @return 培训集合
    */
   @Override
   public List<Training> selectTrainingList( Training training ) {
      return trainingMapper.selectTrainingListAssoc( training );
   }


   /**
    * 查询培训列表 外键关联
    * 
    * @param training
    *           培训信息
    * @return 培训集合
    */
   @Override
   public List<Training> selectTrainingListAssoc( Training training ) {
      return trainingMapper.selectTrainingListAssoc( training );
   }


   /**
    * 新增培训
    * 
    * @param training
    *           培训信息
    * @return 结果
    */
   @Override
   public int insertTraining( Training training ) {
      return trainingMapper.insertTraining( training );
   }


   /**
    * 修改培训
    * 
    * @param training
    *           培训信息
    * @return 结果
    */
   @Override
   public int updateTraining( Training training ) {
      return trainingMapper.updateTraining( training );
   }


   /**
    * 删除培训对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteTrainingByIds( String ids ) {
      return trainingMapper.deleteTrainingByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除培训对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteTrainingById( Long id ) {
      return trainingMapper.deleteTrainingById( id );
   }

}
