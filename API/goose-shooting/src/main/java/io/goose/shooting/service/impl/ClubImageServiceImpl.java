package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.ClubImage;
import io.goose.shooting.mapper.ClubImageMapper;
import io.goose.shooting.service.IClubImageService;


/**
 * 俱乐部图片 服务层实现
 * 
 * @author goose
 * @date 2020-05-01
 */
@Service
@Primary
public class ClubImageServiceImpl implements IClubImageService {

   @Autowired
   private ClubImageMapper clubImageMapper;


   /**
    * 查询俱乐部图片信息
    * 
    * @param id
    *           俱乐部图片ID
    * @return 俱乐部图片信息
    */
   @Override
   public ClubImage selectClubImageById( Long id ) {
      return clubImageMapper.selectClubImageById( id );
   }


   /**
    * 查询俱乐部图片信息 外键关联
    * 
    * @param id
    *           俱乐部图片ID
    * @return 俱乐部图片信息
    */
   @Override
   public ClubImage selectClubImageByIdAssoc( Long id ) {
      return clubImageMapper.selectClubImageByIdAssoc( id );
   }


   /**
    * 查询所有俱乐部图片列表
    * 
    * @param
    * @return 俱乐部图片集合
    */
   @Override
   public List<ClubImage> selectClubImageAll() {
      return clubImageMapper.selectClubImageAll();
   }


   /**
    * 查询所有俱乐部图片列表 外键关联
    * 
    * @param
    * @return 俱乐部图片集合
    */
   @Override
   public List<ClubImage> selectClubImageAllAssoc() {
      return clubImageMapper.selectClubImageAllAssoc();
   }


   /**
    * 查询俱乐部图片列表
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 俱乐部图片集合
    */
   @Override
   public List<ClubImage> selectClubImageList( ClubImage clubImage ) {
      return clubImageMapper.selectClubImageList( clubImage );
   }


   /**
    * 查询俱乐部图片列表 外键关联
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 俱乐部图片集合
    */
   @Override
   public List<ClubImage> selectClubImageListAssoc( ClubImage clubImage ) {
      return clubImageMapper.selectClubImageListAssoc( clubImage );
   }


   /**
    * 新增俱乐部图片
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 结果
    */
   @Override
   public int insertClubImage( ClubImage clubImage ) {
      return clubImageMapper.insertClubImage( clubImage );
   }


   /**
    * 修改俱乐部图片
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 结果
    */
   @Override
   public int updateClubImage( ClubImage clubImage ) {
      return clubImageMapper.updateClubImage( clubImage );
   }


   /**
    * 删除俱乐部图片对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubImageByIds( String ids ) {
      return clubImageMapper.deleteClubImageByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除俱乐部图片对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClubImageById( Long id ) {
      return clubImageMapper.deleteClubImageById( id );
   }

}
