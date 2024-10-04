package io.goose.shooting.mapper;

import io.goose.shooting.domain.PublishWithImage;
import java.util.List;	

/**
 * 图片发布 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface PublishWithImageMapper 
{
	/**
     * 查询图片发布信息
     * 
     * @param id 图片发布ID
     * @return 图片发布信息
     */
	public PublishWithImage selectPublishWithImageById(Long id);
	
	/**
     * 查询图片发布信息 外键关联
     * 
     * @param id 图片发布ID
     * @return 图片发布信息
     */
	public PublishWithImage selectPublishWithImageByIdAssoc(Long id);	

	/**
     * 查询所有图片发布列表
     * 
     * @param 
     * @return 图片发布集合
     */
	public List<PublishWithImage> selectPublishWithImageAll();	
	
	/**
     * 查询所有图片发布列表 外键关联
     * 
     * @param 
     * @return 图片发布集合
     */
	public List<PublishWithImage> selectPublishWithImageAllAssoc();		

	
	/**
     * 查询图片发布列表
     * 
     * @param publishWithImage 图片发布信息
     * @return 图片发布集合
     */
	public List<PublishWithImage> selectPublishWithImageList(PublishWithImage publishWithImage);
	
	/**
     * 查询图片发布列表 外键关联
     * 
     * @param publishWithImage 图片发布信息
     * @return 图片发布集合
     */
	public List<PublishWithImage> selectPublishWithImageListAssoc(PublishWithImage publishWithImage);	
	
	/**
     * 新增图片发布
     * 
     * @param publishWithImage 图片发布信息
     * @return 结果
     */
	public int insertPublishWithImage(PublishWithImage publishWithImage);
	
	/**
     * 修改图片发布
     * 
     * @param publishWithImage 图片发布信息
     * @return 结果
     */
	public int updatePublishWithImage(PublishWithImage publishWithImage);
	
	/**
     * 删除图片发布
     * 
     * @param id 图片发布ID
     * @return 结果
     */
	public int deletePublishWithImageById(Long id);
	
	/**
     * 批量删除图片发布
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePublishWithImageByIds(String[] ids);
	
}