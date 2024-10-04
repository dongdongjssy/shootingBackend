package io.goose.shooting.mapper;

import io.goose.shooting.domain.ContestGroup;
import java.util.List;	

/**
 * 组别 数据层
 * 
 * @author goose
 * @date 2020-05-27
 */
public interface ContestGroupMapper 
{
	/**
     * 查询组别信息
     * 
     * @param id 组别ID
     * @return 组别信息
     */
	public ContestGroup selectContestGroupById(Long id);
	
	/**
     * 查询组别信息 外键关联
     * 
     * @param id 组别ID
     * @return 组别信息
     */
	public ContestGroup selectContestGroupByIdAssoc(Long id);	

	/**
     * 查询所有组别列表
     * 
     * @param 
     * @return 组别集合
     */
	public List<ContestGroup> selectContestGroupAll();	
	
	/**
     * 查询所有组别列表 外键关联
     * 
     * @param 
     * @return 组别集合
     */
	public List<ContestGroup> selectContestGroupAllAssoc();		

	
	/**
     * 查询组别列表
     * 
     * @param contestGroup 组别信息
     * @return 组别集合
     */
	public List<ContestGroup> selectContestGroupList(ContestGroup contestGroup);
	
	/**
     * 查询组别列表 外键关联
     * 
     * @param contestGroup 组别信息
     * @return 组别集合
     */
	public List<ContestGroup> selectContestGroupListAssoc(ContestGroup contestGroup);	
	
	/**
     * 新增组别
     * 
     * @param contestGroup 组别信息
     * @return 结果
     */
	public int insertContestGroup(ContestGroup contestGroup);
	
	/**
     * 修改组别
     * 
     * @param contestGroup 组别信息
     * @return 结果
     */
	public int updateContestGroup(ContestGroup contestGroup);
	
	/**
     * 删除组别
     * 
     * @param id 组别ID
     * @return 结果
     */
	public int deleteContestGroupById(Long id);
	
	/**
     * 批量删除组别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestGroupByIds(String[] ids);
	
}