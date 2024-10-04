package io.goose.shooting.mapper;

import io.goose.shooting.domain.Zh;
import java.util.List;	

/**
 * 总会内容 数据层
 * 
 * @author goose
 * @date 2020-12-09
 */
public interface ZhMapper 
{
	/**
     * 查询总会内容信息
     * 
     * @param id 总会内容ID
     * @return 总会内容信息
     */
	public Zh selectZhById(Long id);
	
	/**
     * 查询总会内容信息 外键关联
     * 
     * @param id 总会内容ID
     * @return 总会内容信息
     */
	public Zh selectZhByIdAssoc(Long id);	

	/**
     * 查询所有总会内容列表
     * 
     * @param 
     * @return 总会内容集合
     */
	public List<Zh> selectZhAll();	
	
	/**
     * 查询所有总会内容列表 外键关联
     * 
     * @param 
     * @return 总会内容集合
     */
	public List<Zh> selectZhAllAssoc();		

	
	/**
     * 查询总会内容列表
     * 
     * @param zh 总会内容信息
     * @return 总会内容集合
     */
	public List<Zh> selectZhList(Zh zh);
	
	/**
     * 查询总会内容列表 外键关联
     * 
     * @param zh 总会内容信息
     * @return 总会内容集合
     */
	public List<Zh> selectZhListAssoc(Zh zh);	
	
	/**
     * 新增总会内容
     * 
     * @param zh 总会内容信息
     * @return 结果
     */
	public int insertZh(Zh zh);
	
	/**
     * 修改总会内容
     * 
     * @param zh 总会内容信息
     * @return 结果
     */
	public int updateZh(Zh zh);
	
	/**
     * 删除总会内容
     * 
     * @param id 总会内容ID
     * @return 结果
     */
	public int deleteZhById(Long id);
	
	/**
     * 批量删除总会内容
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteZhByIds(String[] ids);
	
}