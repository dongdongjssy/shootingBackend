package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.mapper.ContestMapper;
import io.goose.shooting.service.IContestService;


/**
 * 赛事 服务层实现
 * 
 * @author goose
 * @date 2020-05-03
 */
@Service
public class ContestServiceImpl implements IContestService {

   @Autowired
   private ContestMapper contestMapper;


   /**
    * 查询赛事信息
    * 
    * @param id
    *           赛事ID
    * @return 赛事信息
    */
   @Override
   public Contest selectContestById( Long id ) {
      return contestMapper.selectContestById( id );
   }


   /**
    * 查询赛事信息 外键关联
    * 
    * @param id
    *           赛事ID
    * @return 赛事信息
    */
   @Override
   public Contest selectContestByIdAssoc( Long id ) {
      return contestMapper.selectContestByIdAssoc( id );
   }


   /**
    * 查询所有赛事列表
    * 
    * @param
    * @return 赛事集合
    */
   @Override
   public List<Contest> selectContestAll() {
      return contestMapper.selectContestAll();
   }


   /**
    * 查询所有赛事列表 外键关联
    * 
    * @param
    * @return 赛事集合
    */
   @Override
   public List<Contest> selectContestAllAssoc() {
      return contestMapper.selectContestAllAssoc();
   }


   /**
    * 查询赛事列表
    * 
    * @param contest
    *           赛事信息
    * @return 赛事集合
    */
   @Override
   public List<Contest> selectContestList( Contest contest ) {
      return contestMapper.selectContestListAssoc( contest );
   }


   /**
    * 查询赛事列表 外键关联
    * 
    * @param contest
    *           赛事信息
    * @return 赛事集合
    */
   @Override
   public List<Contest> selectContestListAssoc( Contest contest ) {
      return contestMapper.selectContestListAssoc( contest );
   }


   /**
    * 新增赛事
    * 
    * @param contest
    *           赛事信息
    * @return 结果
    */
   @Override
   public int insertContest( Contest contest ) {
      return contestMapper.insertContest( contest );
   }


   /**
    * 修改赛事
    * 
    * @param contest
    *           赛事信息
    * @return 结果
    */
   @Override
   public int updateContest( Contest contest ) {
      return contestMapper.updateContest( contest );
   }


   /**
    * 删除赛事对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteContestByIds( String ids ) {
      return contestMapper.deleteContestByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除赛事对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteContestById( Long id ) {
      return contestMapper.deleteContestById( id );
   }

}
