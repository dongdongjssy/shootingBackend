package io.goose.shooting.service.impl;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.ClubPost;
import io.goose.shooting.mapper.ClubPostMapper;
import io.goose.shooting.service.IClubPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 俱乐部动态 服务层实现
 *
 * @author goose
 * @date 2020-05-09
 */
@Service
@Primary
public class ClubPostServiceImpl implements IClubPostService {

   @Autowired
   private ClubPostMapper clubPostMapper;


   /**
    * 查询俱乐部动态信息
    *
    * @param id
    *       俱乐部动态ID
    * @return 俱乐部动态信息
    */
   @Override
   public ClubPost selectClubPostById(Long id) {
      return clubPostMapper.selectClubPostById(id);
   }


   /**
    * 查询俱乐部动态信息 外键关联
    *
    * @param id
    *       俱乐部动态ID
    * @return 俱乐部动态信息
    */
   @Override
   public ClubPost selectClubPostByIdAssoc(Long id) {
      return clubPostMapper.selectClubPostByIdAssoc(id);
   }


   /**
    * 查询所有俱乐部动态列表
    *
    * @param
    * @return 俱乐部动态集合
    */
   @Override
   public List<ClubPost> selectClubPostAll() {
      return clubPostMapper.selectClubPostAll();
   }


   /**
    * 查询所有俱乐部动态列表 外键关联
    *
    * @param
    * @return 俱乐部动态集合
    */
   @Override
   public List<ClubPost> selectClubPostAllAssoc() {
      return clubPostMapper.selectClubPostAllAssoc();
   }


   /**
    * 查询俱乐部动态列表
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 俱乐部动态集合
    */
   @Override
   public List<ClubPost> selectClubPostList(ClubPost clubPost) {
      return clubPostMapper.selectClubPostList(clubPost);
   }


   /**
    * 查询俱乐部动态列表 外键关联
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 俱乐部动态集合
    */
   @Override
   public List<ClubPost> selectClubPostListAssoc(ClubPost clubPost) {
      return clubPostMapper.selectClubPostListAssoc(clubPost);
   }


   /**
    * 新增俱乐部动态
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 结果
    */
   @Override
   public int insertClubPost(ClubPost clubPost) {
      return clubPostMapper.insertClubPost(clubPost);
   }


   /**
    * 修改俱乐部动态
    *
    * @param clubPost
    *       俱乐部动态信息
    * @return 结果
    */
   @Override
   public int updateClubPost(ClubPost clubPost) {
      return clubPostMapper.updateClubPost(clubPost);
   }


   /**
    * 删除俱乐部动态对象
    *
    * @param ids
    *       需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubPostByIds(String ids) {
      return clubPostMapper.deleteClubPostByIds(Convert.toStrArray(ids));
   }


   /**
    * 删除俱乐部动态对象
    *
    * @param id
    *       需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubPostById(Long id) {
      return clubPostMapper.deleteClubPostById(id);
   }

   @Override
   public List<ClubPost> selectClubPostSummaryList(Long userId) {
      return clubPostMapper.selectClubPostSummaryList(userId);
   }

}
