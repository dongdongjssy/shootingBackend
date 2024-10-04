package io.goose.shooting.mapper;

import io.goose.shooting.domain.Recommend;

import java.util.List;

/**
 * 推荐 数据层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface RecommendMapper {
   /**
    * 查询推荐信息
    *
    * @param id
    *       推荐ID
    * @return 推荐信息
    */
   public Recommend selectRecommendById(Long id);

   /**
    * 查询推荐信息 外键关联
    *
    * @param id
    *       推荐ID
    * @return 推荐信息
    */
   public Recommend selectRecommendByIdAssoc(Long id);

   /**
    * 查询所有推荐列表
    *
    * @param
    * @return 推荐集合
    */
   public List<Recommend> selectRecommendAll();

   /**
    * 查询所有推荐列表 外键关联
    *
    * @param
    * @return 推荐集合
    */
   public List<Recommend> selectRecommendAllAssoc();


   /**
    * 查询推荐列表
    *
    * @param recommend
    *       推荐信息
    * @return 推荐集合
    */
   public List<Recommend> selectRecommendList(Recommend recommend);

   /**
    * 查询推荐列表 外键关联
    *
    * @param recommend
    *       推荐信息
    * @return 推荐集合
    */
   public List<Recommend> selectRecommendListAssoc(Recommend recommend);

   /**
    * 新增推荐
    *
    * @param recommend
    *       推荐信息
    * @return 结果
    */
   public int insertRecommend(Recommend recommend);

   /**
    * 修改推荐
    *
    * @param recommend
    *       推荐信息
    * @return 结果
    */
   public int updateRecommend(Recommend recommend);

   /**
    * 删除推荐
    *
    * @param id
    *       推荐ID
    * @return 结果
    */
   public int deleteRecommendById(Long id);

   /**
    * 批量删除推荐
    *
    * @param ids
    *       需要删除的数据ID
    * @return 结果
    */
   public int deleteRecommendByIds(String[] ids);

   public List<Recommend> selectRecommendSummaryList(Long userId);
}
