package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ContestGroupMapper;
import io.goose.shooting.domain.ContestGroup;

import io.goose.shooting.service.IContestGroupService;
import io.goose.common.support.Convert;

/**
 * 组别 服务层实现
 * 
 * @author goose
 * @date 2020-05-27
 */
@Service
public class ContestGroupServiceImpl implements IContestGroupService 
{
	@Autowired
	private ContestGroupMapper contestGroupMapper;


	/**
     * 查询组别信息
     * 
     * @param id 组别ID
     * @return 组别信息
     */
    @Override
	public ContestGroup selectContestGroupById(Long id)
	{
	    return contestGroupMapper.selectContestGroupById(id);
	}
	
	/**
     * 查询组别信息 外键关联
     * 
     * @param id 组别ID
     * @return 组别信息
     */
    @Override
	public ContestGroup selectContestGroupByIdAssoc(Long id)
	{
	    return contestGroupMapper.selectContestGroupByIdAssoc(id);
	}	
	
	/**
     * 查询所有组别列表
     * 
     * @param 
     * @return 组别集合
     */
	@Override 
	public List<ContestGroup> selectContestGroupAll()
	{
		return contestGroupMapper.selectContestGroupAll();
	}	
	
	/**
     * 查询所有组别列表 外键关联
     * 
     * @param 
     * @return 组别集合
     */
	@Override 
	public List<ContestGroup> selectContestGroupAllAssoc()
	{
		return contestGroupMapper.selectContestGroupAllAssoc();
	}		
	
	/**
     * 查询组别列表
     * 
     * @param contestGroup 组别信息
     * @return 组别集合
     */
	@Override
	public List<ContestGroup> selectContestGroupList(ContestGroup contestGroup)
	{
	    return contestGroupMapper.selectContestGroupList(contestGroup);
	}
	
	/**
     * 查询组别列表 外键关联
     * 
     * @param contestGroup 组别信息
     * @return 组别集合
     */
	@Override
	public List<ContestGroup> selectContestGroupListAssoc(ContestGroup contestGroup)
	{
	    return contestGroupMapper.selectContestGroupListAssoc(contestGroup);
	}	
	
    /**
     * 新增组别
     * 
     * @param contestGroup 组别信息
     * @return 结果
     */
	@Override
	public int insertContestGroup(ContestGroup contestGroup)
	{
	    return contestGroupMapper.insertContestGroup(contestGroup);
	}
	
	/**
     * 修改组别
     * 
     * @param contestGroup 组别信息
     * @return 结果
     */
	@Override
	public int updateContestGroup(ContestGroup contestGroup)
	{
	    return contestGroupMapper.updateContestGroup(contestGroup);
	}

	/**
     * 删除组别对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestGroupByIds(String ids)
	{
		return contestGroupMapper.deleteContestGroupByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除组别对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestGroupById(Long id)
	{
		return contestGroupMapper.deleteContestGroupById(id);
	}
	
	



}
