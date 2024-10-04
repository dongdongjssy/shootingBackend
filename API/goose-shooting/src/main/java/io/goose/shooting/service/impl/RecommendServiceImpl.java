package io.goose.shooting.service.impl;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Recommend;
import io.goose.shooting.mapper.RecommendMapper;
import io.goose.shooting.service.IRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 推荐 服务层实现
 *
 * @author goose
 * @date 2020-04-28
 */
@Service
@Primary
public class RecommendServiceImpl implements IRecommendService {

   @Autowired
   public RecommendMapper recommendMapper;


   /**
    * 查询推荐信息
    *
    * @param id
    *       推荐ID
    * @return 推荐信息
    */
   @Override
   public Recommend selectRecommendById(Long id) {
      return recommendMapper.selectRecommendById(id);
   }


   /**
    * 查询推荐信息 外键关联
    *
    * @param id
    *       推荐ID
    * @return 推荐信息
    */
   @Override
   public Recommend selectRecommendByIdAssoc(Long id) {
      return recommendMapper.selectRecommendByIdAssoc(id);
   }


   /**
    * 查询所有推荐列表
    *
    * @param
    * @return 推荐集合
    */
   @Override
   public List<Recommend> selectRecommendAll() {
      return recommendMapper.selectRecommendAll();
   }


   /**
    * 查询所有推荐列表 外键关联
    *
    * @param
    * @return 推荐集合
    */
   @Override
   public List<Recommend> selectRecommendAllAssoc() {
      return recommendMapper.selectRecommendAllAssoc();
   }


   /**
    * 查询推荐列表
    *
    * @param recommend
    *       推荐信息
    * @return 推荐集合
    */
   @Override
   public List<Recommend> selectRecommendList(Recommend recommend) {
      return recommendMapper.selectRecommendList(recommend);
   }


   /**
    * 查询推荐列表 外键关联
    *
    * @param recommend
    *       推荐信息
    * @return 推荐集合
    */
   @Override
   public List<Recommend> selectRecommendListAssoc(Recommend recommend) {
      return recommendMapper.selectRecommendListAssoc(recommend);
   }


   /**
    * 新增推荐
    *
    * @param recommend
    *       推荐信息
    * @return 结果
    */
   @Override
   public int insertRecommend(Recommend recommend) {
      return recommendMapper.insertRecommend(recommend);
   }


   /**
    * 修改推荐
    *
    * @param recommend
    *       推荐信息
    * @return 结果
    */
   @Override
   public int updateRecommend(Recommend recommend) {
      return recommendMapper.updateRecommend(recommend);
   }


   /**
    * 删除推荐对象
    *
    * @param ids
    *       需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteRecommendByIds(String ids) {
      return recommendMapper.deleteRecommendByIds(Convert.toStrArray(ids));
   }


   /**
    * 删除推荐对象
    *
    * @param id
    *       需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteRecommendById(Long id) {
      return recommendMapper.deleteRecommendById(id);
   }

   @Override
   public List<Recommend> selectRecommendSummaryList(Long userId) {
      return recommendMapper.selectRecommendSummaryList(userId);
   }

}
