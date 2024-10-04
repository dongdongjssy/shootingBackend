package io.goose.shooting.service;

import io.goose.shooting.domain.PublishWithVideo;
import java.util.List;

/**
 * 视频发布 服务层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface IPublishWithVideoService 
{
	/**
     * 查询视频发布信息
     * 
     * @param id 视频发布ID
     * @return 视频发布信息
     */
	public PublishWithVideo selectPublishWithVideoById(Long id);
	
	/**
     * 查询视频发布信息 外键关联
     * 
     * @param id 视频发布ID
     * @return 视频发布信息
     */
	public PublishWithVideo selectPublishWithVideoByIdAssoc(Long id);	
	
	/**
     * 查询所有视频发布列表
     * 
     * @param 
     * @return 视频发布集合
     */
	public List<PublishWithVideo> selectPublishWithVideoAll();		
	
	/**
     * 查询所有视频发布列表  外键关联
     * 
     * @param 
     * @return 视频发布集合
     */
	public List<PublishWithVideo> selectPublishWithVideoAllAssoc();		
	
	/**
     * 查询视频发布列表
     * 
     * @param publishWithVideo 视频发布信息
     * @return 视频发布集合
     */
	public List<PublishWithVideo> selectPublishWithVideoList(PublishWithVideo publishWithVideo);
	
	/**
     * 查询视频发布列表 外键关联
     * 
     * @param publishWithVideo 视频发布信息
     * @return 视频发布集合
     */
	public List<PublishWithVideo> selectPublishWithVideoListAssoc(PublishWithVideo publishWithVideo);	
	
	/**
     * 新增视频发布
     * 
     * @param publishWithVideo 视频发布信息
     * @return 结果
     */
	public int insertPublishWithVideo(PublishWithVideo publishWithVideo);
	
	/**
     * 修改视频发布
     * 
     * @param publishWithVideo 视频发布信息
     * @return 结果
     */
	public int updatePublishWithVideo(PublishWithVideo publishWithVideo);
		
	/**
     * 删除视频发布信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePublishWithVideoByIds(String ids);
	
	/**
     * 删除视频发布信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deletePublishWithVideoById(Long id);
	

  
}
