package io.goose.shooting.mapper;

import io.goose.shooting.domain.Post;

import javax.swing.*;
import java.util.List;

/**
 * 动态 数据层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface PostMapper
{
	/**
     * 查询动态信息
     *
     * @param id 动态ID
     * @return 动态信息
     */
	public Post selectPostById(Long id);

	/**
     * 查询动态信息 外键关联
     *
     * @param id 动态ID
     * @return 动态信息
     */
	public Post selectPostByIdAssoc(Long id);

	/**
     * 查询所有动态列表
     *
     * @param
     * @return 动态集合
     */
	public List<Post> selectPostAll();

	/**
     * 查询所有动态列表 外键关联
     *
     * @param
     * @return 动态集合
     */
	public List<Post> selectPostAllAssoc();


	/**
     * 查询动态列表
     *
     * @param post 动态信息
     * @return 动态集合
     */
	public List<Post> selectPostList(Post post);

	/**
     * 查询动态列表 外键关联
     *
     * @param post 动态信息
     * @return 动态集合
     */
	public List<Post> selectPostListAssoc(Post post);

	/**
     * 新增动态
     *
     * @param post 动态信息
     * @return 结果
     */
	public int insertPost(Post post);

	/**
     * 修改动态
     *
     * @param post 动态信息
     * @return 结果
     */
	public int updatePost(Post post);

	/**
     * 删除动态
     *
     * @param id 动态ID
     * @return 结果
     */
	public int deletePostById(Long id);

	/**
     * 批量删除动态
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePostByIds(String[] ids);

	public List<Post> selectPostSummaryList(Long userId);
}
