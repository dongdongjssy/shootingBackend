package io.goose.shooting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.goose.shooting.domain.Message;	

/**
 * 通告 数据层
 * 
 * @author goose
 * @date 2020-05-20
 */
public interface MessageMapper 
{
	/**
     * 查询通告信息
     * 
     * @param id 通告ID
     * @return 通告信息
     */
	public Message selectMessageById(Long id);
	
	/**
     * 查询通告信息 外键关联
     * 
     * @param id 通告ID
     * @return 通告信息
     */
	public Message selectMessageByIdAssoc(Long id);	

	/**
     * 查询所有通告列表
     * 
     * @param 
     * @return 通告集合
     */
	public List<Message> selectMessageAll();	
	
	/**
     * 查询所有通告列表 外键关联
     * 
     * @param 
     * @return 通告集合
     */
	public List<Message> selectMessageAllAssoc();		

	
	/**
     * 查询通告列表
     * 
     * @param message 通告信息
     * @return 通告集合
     */
	public List<Message> selectMessageList(Message message);
	
	/**
     * 查询通告列表 外键关联
     * 
     * @param message 通告信息
     * @return 通告集合
     */
	public List<Message> selectMessageListAssoc(Message message);	
	
	/**
     * 新增通告
     * 
     * @param message 通告信息
     * @return 结果
     */
	public int insertMessage(Message message);
	
	/**
     * 修改通告
     * 
     * @param message 通告信息
     * @return 结果
     */
	public int updateMessage(Message message);
	
	/**
     * 删除通告
     * 
     * @param id 通告ID
     * @return 结果
     */
	public int deleteMessageById(Long id);
	
	/**
     * 批量删除通告
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMessageByIds(String[] ids);

	public int insertMessageUser(@Param("messageId")Long messageId,@Param("clientUserId") Long clientUserId,@Param("createBy") String createBy);

	public int updateMessageUser(Long clientUserId);

	public List<Message> selectMessageListByUser(Message message);

	public int selectMessageIsViewByUser(Message message);

	public void insertMessageUserInAsync(@Param("messageId")Long messageId,@Param("clientUserId") Long clientUserId,@Param("createBy") String createBy);
	
}