package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.PublishWithImageMapper;
import io.goose.shooting.domain.PublishWithImage;

import io.goose.shooting.service.IPublishWithImageService;
import io.goose.common.support.Convert;

/**
 * 图片发布 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class PublishWithImageServiceImpl implements IPublishWithImageService 
{
	@Autowired
	private PublishWithImageMapper publishWithImageMapper;


	/**
     * 查询图片发布信息
     * 
     * @param id 图片发布ID
     * @return 图片发布信息
     */
    @Override
	public PublishWithImage selectPublishWithImageById(Long id)
	{
	    return publishWithImageMapper.selectPublishWithImageById(id);
	}
	
	/**
     * 查询图片发布信息 外键关联
     * 
     * @param id 图片发布ID
     * @return 图片发布信息
     */
    @Override
	public PublishWithImage selectPublishWithImageByIdAssoc(Long id)
	{
	    return publishWithImageMapper.selectPublishWithImageByIdAssoc(id);
	}	
	
	/**
     * 查询所有图片发布列表
     * 
     * @param 
     * @return 图片发布集合
     */
	@Override 
	public List<PublishWithImage> selectPublishWithImageAll()
	{
		return publishWithImageMapper.selectPublishWithImageAll();
	}	
	
	/**
     * 查询所有图片发布列表 外键关联
     * 
     * @param 
     * @return 图片发布集合
     */
	@Override 
	public List<PublishWithImage> selectPublishWithImageAllAssoc()
	{
		return publishWithImageMapper.selectPublishWithImageAllAssoc();
	}		
	
	/**
     * 查询图片发布列表
     * 
     * @param publishWithImage 图片发布信息
     * @return 图片发布集合
     */
	@Override
	public List<PublishWithImage> selectPublishWithImageList(PublishWithImage publishWithImage)
	{
	    return publishWithImageMapper.selectPublishWithImageList(publishWithImage);
	}
	
	/**
     * 查询图片发布列表 外键关联
     * 
     * @param publishWithImage 图片发布信息
     * @return 图片发布集合
     */
	@Override
	public List<PublishWithImage> selectPublishWithImageListAssoc(PublishWithImage publishWithImage)
	{
	    return publishWithImageMapper.selectPublishWithImageListAssoc(publishWithImage);
	}	
	
    /**
     * 新增图片发布
     * 
     * @param publishWithImage 图片发布信息
     * @return 结果
     */
	@Override
	public int insertPublishWithImage(PublishWithImage publishWithImage)
	{
	    return publishWithImageMapper.insertPublishWithImage(publishWithImage);
	}
	
	/**
     * 修改图片发布
     * 
     * @param publishWithImage 图片发布信息
     * @return 结果
     */
	@Override
	public int updatePublishWithImage(PublishWithImage publishWithImage)
	{
	    return publishWithImageMapper.updatePublishWithImage(publishWithImage);
	}

	/**
     * 删除图片发布对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePublishWithImageByIds(String ids)
	{
		return publishWithImageMapper.deletePublishWithImageByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除图片发布对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePublishWithImageById(Long id)
	{
		return publishWithImageMapper.deletePublishWithImageById(id);
	}
	
	



}
