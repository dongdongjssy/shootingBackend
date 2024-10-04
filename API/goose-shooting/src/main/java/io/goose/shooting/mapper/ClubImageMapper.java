package io.goose.shooting.mapper;

import java.util.List;

import io.goose.shooting.domain.ClubImage;


/**
 * 俱乐部图片 数据层
 * 
 * @author goose
 * @date 2020-05-01
 */
public interface ClubImageMapper {

   /**
    * 查询俱乐部图片信息
    * 
    * @param id
    *           俱乐部图片ID
    * @return 俱乐部图片信息
    */
   public ClubImage selectClubImageById( Long id );


   /**
    * 查询俱乐部图片信息 外键关联
    * 
    * @param id
    *           俱乐部图片ID
    * @return 俱乐部图片信息
    */
   public ClubImage selectClubImageByIdAssoc( Long id );


   /**
    * 查询所有俱乐部图片列表
    * 
    * @param
    * @return 俱乐部图片集合
    */
   public List<ClubImage> selectClubImageAll();


   /**
    * 查询所有俱乐部图片列表 外键关联
    * 
    * @param
    * @return 俱乐部图片集合
    */
   public List<ClubImage> selectClubImageAllAssoc();


   /**
    * 查询俱乐部图片列表
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 俱乐部图片集合
    */
   public List<ClubImage> selectClubImageList( ClubImage clubImage );


   /**
    * 查询俱乐部图片列表 外键关联
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 俱乐部图片集合
    */
   public List<ClubImage> selectClubImageListAssoc( ClubImage clubImage );


   /**
    * 新增俱乐部图片
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 结果
    */
   public int insertClubImage( ClubImage clubImage );


   /**
    * 修改俱乐部图片
    * 
    * @param clubImage
    *           俱乐部图片信息
    * @return 结果
    */
   public int updateClubImage( ClubImage clubImage );


   /**
    * 删除俱乐部图片
    * 
    * @param id
    *           俱乐部图片ID
    * @return 结果
    */
   public int deleteClubImageById( Long id );


   /**
    * 批量删除俱乐部图片
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   public int deleteClubImageByIds( String[] ids );

}
