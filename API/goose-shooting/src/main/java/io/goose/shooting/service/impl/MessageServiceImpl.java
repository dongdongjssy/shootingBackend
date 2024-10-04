package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.MessageMapper;
import io.goose.shooting.domain.Message;

import io.goose.shooting.service.IMessageService;
import io.goose.common.support.Convert;

/**
 * 通告 服务层实现
 * 
 * @author goose
 * @date 2020-05-20
 */
@Service
public class MessageServiceImpl implements IMessageService 
{
	@Autowired
	private MessageMapper messageMapper;


	/**
     * 查询通告信息
     * 
     * @param id 通告ID
     * @return 通告信息
     */
    @Override
	public Message selectMessageById(Long id)
	{
	    return messageMapper.selectMessageById(id);
	}
	
	/**
     * 查询通告信息 外键关联
     * 
     * @param id 通告ID
     * @return 通告信息
     */
    @Override
	public Message selectMessageByIdAssoc(Long id)
	{
	    return messageMapper.selectMessageByIdAssoc(id);
	}	
	
	/**
     * 查询所有通告列表
     * 
     * @param 
     * @return 通告集合
     */
	@Override 
	public List<Message> selectMessageAll()
	{
		return messageMapper.selectMessageAll();
	}	
	
	/**
     * 查询所有通告列表 外键关联
     * 
     * @param 
     * @return 通告集合
     */
	@Override 
	public List<Message> selectMessageAllAssoc()
	{
		return messageMapper.selectMessageAllAssoc();
	}		
	
	/**
     * 查询通告列表
     * 
     * @param message 通告信息
     * @return 通告集合
     */
	@Override
	public List<Message> selectMessageList(Message message)
	{
	    return messageMapper.selectMessageList(message);
	}
	
	/**
     * 查询通告列表 外键关联
     * 
     * @param message 通告信息
     * @return 通告集合
     */
	@Override
	public List<Message> selectMessageListAssoc(Message message)
	{
	    return messageMapper.selectMessageListAssoc(message);
	}	
	
    /**
     * 新增通告
     * 
     * @param message 通告信息
     * @return 结果
     */
	@Override
	public int insertMessage(Message message)
	{
	    return messageMapper.insertMessage(message);
	}
	
	/**
     * 修改通告
     * 
     * @param message 通告信息
     * @return 结果
     */
	@Override
	public int updateMessage(Message message)
	{
	    return messageMapper.updateMessage(message);
	}

	/**
     * 删除通告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMessageByIds(String ids)
	{
		return messageMapper.deleteMessageByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除通告对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMessageById(Long id)
	{
		return messageMapper.deleteMessageById(id);
	}

	@Override
	public int insertMessageUser(Long messageId, Long clientUserId, String createBy) {
		return messageMapper.insertMessageUser(messageId,clientUserId,createBy);
	}
	
	@Override
	public void insertMessageUserInAsync(Long messageId, Long clientUserId, String createBy) {
		messageMapper.insertMessageUserInAsync(messageId,clientUserId,createBy);
	}

	@Override
	public int updateMessageUser(Long clientUserId) {
		return messageMapper.updateMessageUser(clientUserId);
	}

	@Override
	public List<Message> selectMessageListByUser(Message message) {
		return messageMapper.selectMessageListByUser(message);
	}

	@Override
	public int selectMessageIsViewByUser(Message message) {
		return messageMapper.selectMessageIsViewByUser(message);
	}
	
	



}
