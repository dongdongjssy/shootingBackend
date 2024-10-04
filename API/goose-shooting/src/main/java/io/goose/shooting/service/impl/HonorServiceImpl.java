package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.HonorMapper;
import io.goose.shooting.domain.Honor;

import io.goose.shooting.service.IHonorService;
import io.goose.common.support.Convert;

/**
 * 荣誉榜管理 服务层实现
 * 
 * @author goose
 * @date 2021-02-01
 */
@Service
public class HonorServiceImpl implements IHonorService 
{
	@Autowired
	private HonorMapper honorMapper;


	/**
     * 查询荣誉榜管理信息
     * 
     * @param id 荣誉榜管理ID
     * @return 荣誉榜管理信息
     */
    @Override
	public Honor selectHonorById(Long id)
	{
	    return honorMapper.selectHonorById(id);
	}
	
	/**
     * 查询荣誉榜管理信息 外键关联
     * 
     * @param id 荣誉榜管理ID
     * @return 荣誉榜管理信息
     */
    @Override
	public Honor selectHonorByIdAssoc(Long id)
	{
	    return honorMapper.selectHonorByIdAssoc(id);
	}	
	
	/**
     * 查询所有荣誉榜管理列表
     * 
     * @param 
     * @return 荣誉榜管理集合
     */
	@Override 
	public List<Honor> selectHonorAll()
	{
		return honorMapper.selectHonorAll();
	}	
	
	/**
     * 查询所有荣誉榜管理列表 外键关联
     * 
     * @param 
     * @return 荣誉榜管理集合
     */
	@Override 
	public List<Honor> selectHonorAllAssoc()
	{
		return honorMapper.selectHonorAllAssoc();
	}		
	
	/**
     * 查询荣誉榜管理列表
     * 
     * @param honor 荣誉榜管理信息
     * @return 荣誉榜管理集合
     */
	@Override
	public List<Honor> selectHonorList(Honor honor)
	{
	    return honorMapper.selectHonorList(honor);
	}
	
	/**
     * 查询荣誉榜管理列表 外键关联
     * 
     * @param honor 荣誉榜管理信息
     * @return 荣誉榜管理集合
     */
	@Override
	public List<Honor> selectHonorListAssoc(Honor honor)
	{
	    return honorMapper.selectHonorListAssoc(honor);
	}	
	
    /**
     * 新增荣誉榜管理
     * 
     * @param honor 荣誉榜管理信息
     * @return 结果
     */
	@Override
	public int insertHonor(Honor honor)
	{
	    return honorMapper.insertHonor(honor);
	}
	
	/**
     * 修改荣誉榜管理
     * 
     * @param honor 荣誉榜管理信息
     * @return 结果
     */
	@Override
	public int updateHonor(Honor honor)
	{
	    return honorMapper.updateHonor(honor);
	}

	/**
     * 删除荣誉榜管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteHonorByIds(String ids)
	{
		return honorMapper.deleteHonorByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除荣誉榜管理对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteHonorById(Long id)
	{
		return honorMapper.deleteHonorById(id);
	}
	
	



}
