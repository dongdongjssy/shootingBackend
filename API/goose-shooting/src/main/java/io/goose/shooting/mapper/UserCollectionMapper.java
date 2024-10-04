package io.goose.shooting.mapper;

import io.goose.shooting.domain.UserCollection;
import java.util.List;	

/**
 * 用户收藏 数据层
 * 
 * @author goose
 * @date 2020-05-21
 */
public interface UserCollectionMapper 
{
	/**
     * 查询用户收藏信息
     * 
     * @param id 用户收藏ID
     * @return 用户收藏信息
     */
	public UserCollection selectUserCollectionById(Long id);
	
	/**
     * 查询用户收藏信息 外键关联
     * 
     * @param id 用户收藏ID
     * @return 用户收藏信息
     */
	public UserCollection selectUserCollectionByIdAssoc(Long id);	

	/**
     * 查询所有用户收藏列表
     * 
     * @param 
     * @return 用户收藏集合
     */
	public List<UserCollection> selectUserCollectionAll();	
	
	/**
     * 查询所有用户收藏列表 外键关联
     * 
     * @param 
     * @return 用户收藏集合
     */
	public List<UserCollection> selectUserCollectionAllAssoc();		

	
	/**
     * 查询用户收藏列表
     * 
     * @param userCollection 用户收藏信息
     * @return 用户收藏集合
     */
	public List<UserCollection> selectUserCollectionList(UserCollection userCollection);
	
	/**
     * 查询用户收藏列表 外键关联
     * 
     * @param userCollection 用户收藏信息
     * @return 用户收藏集合
     */
	public List<UserCollection> selectUserCollectionListAssoc(UserCollection userCollection);	
	
	/**
     * 新增用户收藏
     * 
     * @param userCollection 用户收藏信息
     * @return 结果
     */
	public int insertUserCollection(UserCollection userCollection);
	
	/**
     * 修改用户收藏
     * 
     * @param userCollection 用户收藏信息
     * @return 结果
     */
	public int updateUserCollection(UserCollection userCollection);
	
	/**
     * 删除用户收藏
     * 
     * @param id 用户收藏ID
     * @return 结果
     */
	public int deleteUserCollectionById(Long id);
	
	/**
     * 批量删除用户收藏
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserCollectionByIds(String[] ids);
	
}