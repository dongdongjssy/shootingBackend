package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Judge;
import io.goose.shooting.mapper.JudgeMapper;
import io.goose.shooting.service.IJudgeService;


/**
 * 裁判 服务层实现
 * 
 * @author goose
 * @date 2020-05-21
 */
@Service
@Primary
public class JudgeServiceImpl implements IJudgeService {

   @Autowired
   private JudgeMapper judgeMapper;


   /**
    * 查询裁判信息
    * 
    * @param id
    *           裁判ID
    * @return 裁判信息
    */
   @Override
   public Judge selectJudgeById( Long id ) {
      return judgeMapper.selectJudgeById( id );
   }


   /**
    * 查询裁判信息 外键关联
    * 
    * @param id
    *           裁判ID
    * @return 裁判信息
    */
   @Override
   public Judge selectJudgeByIdAssoc( Long id ) {
      return judgeMapper.selectJudgeByIdAssoc( id );
   }


   /**
    * 查询所有裁判列表
    * 
    * @param
    * @return 裁判集合
    */
   @Override
   public List<Judge> selectJudgeAll() {
      return judgeMapper.selectJudgeAll();
   }


   /**
    * 查询所有裁判列表 外键关联
    * 
    * @param
    * @return 裁判集合
    */
   @Override
   public List<Judge> selectJudgeAllAssoc() {
      return judgeMapper.selectJudgeAllAssoc();
   }


   /**
    * 查询裁判列表
    * 
    * @param judge
    *           裁判信息
    * @return 裁判集合
    */
   @Override
   public List<Judge> selectJudgeList( Judge judge ) {
      return judgeMapper.selectJudgeList( judge );
   }


   /**
    * 查询裁判列表 外键关联
    * 
    * @param judge
    *           裁判信息
    * @return 裁判集合
    */
   @Override
   public List<Judge> selectJudgeListAssoc( Judge judge ) {
      return judgeMapper.selectJudgeListAssoc( judge );
   }


   /**
    * 新增裁判
    * 
    * @param judge
    *           裁判信息
    * @return 结果
    */
   @Override
   public int insertJudge( Judge judge ) {
      return judgeMapper.insertJudge( judge );
   }


   /**
    * 修改裁判
    * 
    * @param judge
    *           裁判信息
    * @return 结果
    */
   @Override
   public int updateJudge( Judge judge ) {
      return judgeMapper.updateJudge( judge );
   }


   /**
    * 删除裁判对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteJudgeByIds( String ids ) {
      return judgeMapper.deleteJudgeByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除裁判对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteJudgeById( Long id ) {
      return judgeMapper.deleteJudgeById( id );
   }

}
