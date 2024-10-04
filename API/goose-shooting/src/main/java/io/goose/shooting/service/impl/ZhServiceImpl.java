package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ZhMapper;
import io.goose.shooting.domain.Zh;

import io.goose.shooting.service.IZhService;
import io.goose.common.support.Convert;

/**
 * 总会内容 服务层实现
 * 
 * @author goose
 * @date 2020-12-09
 */
@Service
public class ZhServiceImpl implements IZhService 
{
	@Autowired
	private ZhMapper zhMapper;


	/**
     * 查询总会内容信息
     * 
     * @param id 总会内容ID
     * @return 总会内容信息
     */
    @Override
	public Zh selectZhById(Long id)
	{
	    return zhMapper.selectZhById(id);
	}
	
	/**
     * 查询总会内容信息 外键关联
     * 
     * @param id 总会内容ID
     * @return 总会内容信息
     */
    @Override
	public Zh selectZhByIdAssoc(Long id)
	{
	    return zhMapper.selectZhByIdAssoc(id);
	}	
	
	/**
     * 查询所有总会内容列表
     * 
     * @param 
     * @return 总会内容集合
     */
	@Override 
	public List<Zh> selectZhAll()
	{
		return zhMapper.selectZhAll();
	}	
	
	/**
     * 查询所有总会内容列表 外键关联
     * 
     * @param 
     * @return 总会内容集合
     */
	@Override 
	public List<Zh> selectZhAllAssoc()
	{
		return zhMapper.selectZhAllAssoc();
	}		
	
	/**
     * 查询总会内容列表
     * 
     * @param zh 总会内容信息
     * @return 总会内容集合
     */
	@Override
	public List<Zh> selectZhList(Zh zh)
	{
	    return zhMapper.selectZhList(zh);
	}
	
	/**
     * 查询总会内容列表 外键关联
     * 
     * @param zh 总会内容信息
     * @return 总会内容集合
     */
	@Override
	public List<Zh> selectZhListAssoc(Zh zh)
	{
	    return zhMapper.selectZhListAssoc(zh);
	}	
	
    /**
     * 新增总会内容
     * 
     * @param zh 总会内容信息
     * @return 结果
     */
	@Override
	public int insertZh(Zh zh)
	{
	    return zhMapper.insertZh(zh);
	}
	
	/**
     * 修改总会内容
     * 
     * @param zh 总会内容信息
     * @return 结果
     */
	@Override
	public int updateZh(Zh zh)
	{
	    return zhMapper.updateZh(zh);
	}

	/**
     * 删除总会内容对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhByIds(String ids)
	{
		return zhMapper.deleteZhByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除总会内容对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteZhById(Long id)
	{
		return zhMapper.deleteZhById(id);
	}
	
	



}
