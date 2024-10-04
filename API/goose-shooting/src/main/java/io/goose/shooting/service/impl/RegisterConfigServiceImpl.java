package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RegisterConfigMapper;
import io.goose.shooting.domain.RegisterConfig;

import io.goose.shooting.service.IRegisterConfigService;
import io.goose.common.support.Convert;

/**
 * 报名配置 服务层实现
 * 
 * @author goose
 * @date 2021-01-14
 */
@Service
public class RegisterConfigServiceImpl implements IRegisterConfigService 
{
	@Autowired
	private RegisterConfigMapper registerConfigMapper;


	/**
     * 查询报名配置信息
     * 
     * @param id 报名配置ID
     * @return 报名配置信息
     */
    @Override
	public RegisterConfig selectRegisterConfigById(Long id)
	{
	    return registerConfigMapper.selectRegisterConfigById(id);
	}
	
	/**
     * 查询报名配置信息 外键关联
     * 
     * @param id 报名配置ID
     * @return 报名配置信息
     */
    @Override
	public RegisterConfig selectRegisterConfigByIdAssoc(Long id)
	{
	    return registerConfigMapper.selectRegisterConfigByIdAssoc(id);
	}

	@Override
	public RegisterConfig selectRegisterConfigByFkIdAndType(RegisterConfig registerConfig) {
		return registerConfigMapper.selectRegisterConfigByFkIdAndType(registerConfig);
	}

	/**
     * 查询所有报名配置列表
     * 
     * @param 
     * @return 报名配置集合
     */
	@Override 
	public List<RegisterConfig> selectRegisterConfigAll()
	{
		return registerConfigMapper.selectRegisterConfigAll();
	}	
	
	/**
     * 查询所有报名配置列表 外键关联
     * 
     * @param 
     * @return 报名配置集合
     */
	@Override 
	public List<RegisterConfig> selectRegisterConfigAllAssoc()
	{
		return registerConfigMapper.selectRegisterConfigAllAssoc();
	}		
	
	/**
     * 查询报名配置列表
     * 
     * @param registerConfig 报名配置信息
     * @return 报名配置集合
     */
	@Override
	public List<RegisterConfig> selectRegisterConfigList(RegisterConfig registerConfig)
	{
	    return registerConfigMapper.selectRegisterConfigList(registerConfig);
	}
	
	/**
     * 查询报名配置列表 外键关联
     * 
     * @param registerConfig 报名配置信息
     * @return 报名配置集合
     */
	@Override
	public List<RegisterConfig> selectRegisterConfigListAssoc(RegisterConfig registerConfig)
	{
	    return registerConfigMapper.selectRegisterConfigListAssoc(registerConfig);
	}	
	
    /**
     * 新增报名配置
     * 
     * @param registerConfig 报名配置信息
     * @return 结果
     */
	@Override
	public int insertRegisterConfig(RegisterConfig registerConfig)
	{
	    return registerConfigMapper.insertRegisterConfig(registerConfig);
	}
	
	/**
     * 修改报名配置
     * 
     * @param registerConfig 报名配置信息
     * @return 结果
     */
	@Override
	public int updateRegisterConfig(RegisterConfig registerConfig)
	{
	    return registerConfigMapper.updateRegisterConfig(registerConfig);
	}

	/**
     * 删除报名配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterConfigByIds(String ids)
	{
		return registerConfigMapper.deleteRegisterConfigByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除报名配置对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterConfigById(Long id)
	{
		return registerConfigMapper.deleteRegisterConfigById(id);
	}
	
	



}
