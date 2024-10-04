package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.PublishWithVideoMapper;
import io.goose.shooting.domain.PublishWithVideo;

import io.goose.shooting.service.IPublishWithVideoService;
import io.goose.common.support.Convert;

/**
 * 视频发布 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class PublishWithVideoServiceImpl implements IPublishWithVideoService 
{
	@Autowired
	private PublishWithVideoMapper publishWithVideoMapper;


	/**
     * 查询视频发布信息
     * 
     * @param id 视频发布ID
     * @return 视频发布信息
     */
    @Override
	public PublishWithVideo selectPublishWithVideoById(Long id)
	{
	    return publishWithVideoMapper.selectPublishWithVideoById(id);
	}
	
	/**
     * 查询视频发布信息 外键关联
     * 
     * @param id 视频发布ID
     * @return 视频发布信息
     */
    @Override
	public PublishWithVideo selectPublishWithVideoByIdAssoc(Long id)
	{
	    return publishWithVideoMapper.selectPublishWithVideoByIdAssoc(id);
	}	
	
	/**
     * 查询所有视频发布列表
     * 
     * @param 
     * @return 视频发布集合
     */
	@Override 
	public List<PublishWithVideo> selectPublishWithVideoAll()
	{
		return publishWithVideoMapper.selectPublishWithVideoAll();
	}	
	
	/**
     * 查询所有视频发布列表 外键关联
     * 
     * @param 
     * @return 视频发布集合
     */
	@Override 
	public List<PublishWithVideo> selectPublishWithVideoAllAssoc()
	{
		return publishWithVideoMapper.selectPublishWithVideoAllAssoc();
	}		
	
	/**
     * 查询视频发布列表
     * 
     * @param publishWithVideo 视频发布信息
     * @return 视频发布集合
     */
	@Override
	public List<PublishWithVideo> selectPublishWithVideoList(PublishWithVideo publishWithVideo)
	{
	    return publishWithVideoMapper.selectPublishWithVideoList(publishWithVideo);
	}
	
	/**
     * 查询视频发布列表 外键关联
     * 
     * @param publishWithVideo 视频发布信息
     * @return 视频发布集合
     */
	@Override
	public List<PublishWithVideo> selectPublishWithVideoListAssoc(PublishWithVideo publishWithVideo)
	{
	    return publishWithVideoMapper.selectPublishWithVideoListAssoc(publishWithVideo);
	}	
	
    /**
     * 新增视频发布
     * 
     * @param publishWithVideo 视频发布信息
     * @return 结果
     */
	@Override
	public int insertPublishWithVideo(PublishWithVideo publishWithVideo)
	{
	    return publishWithVideoMapper.insertPublishWithVideo(publishWithVideo);
	}
	
	/**
     * 修改视频发布
     * 
     * @param publishWithVideo 视频发布信息
     * @return 结果
     */
	@Override
	public int updatePublishWithVideo(PublishWithVideo publishWithVideo)
	{
	    return publishWithVideoMapper.updatePublishWithVideo(publishWithVideo);
	}

	/**
     * 删除视频发布对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePublishWithVideoByIds(String ids)
	{
		return publishWithVideoMapper.deletePublishWithVideoByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除视频发布对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePublishWithVideoById(Long id)
	{
		return publishWithVideoMapper.deletePublishWithVideoById(id);
	}
	
	



}
