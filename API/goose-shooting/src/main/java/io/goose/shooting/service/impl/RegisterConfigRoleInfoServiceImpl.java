package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RegisterConfigRoleInfoMapper;
import io.goose.shooting.domain.RegisterConfigRoleInfo;

import io.goose.shooting.service.IRegisterConfigRoleInfoService;
import io.goose.common.support.Convert;

/**
 * 报名配置-角色配置项 服务层实现
 * 
 * @author goose
 * @date 2021-01-14
 */
@Service
public class RegisterConfigRoleInfoServiceImpl implements IRegisterConfigRoleInfoService 
{
	@Autowired
	private RegisterConfigRoleInfoMapper registerConfigRoleInfoMapper;


	/**
     * 查询报名配置-角色配置项信息
     * 
     * @param id 报名配置-角色配置项ID
     * @return 报名配置-角色配置项信息
     */
    @Override
	public RegisterConfigRoleInfo selectRegisterConfigRoleInfoById(Long id)
	{
	    return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoById(id);
	}
	
	/**
     * 查询报名配置-角色配置项信息 外键关联
     *
     * @param id 报名配置-角色配置项ID
     * @return 报名配置-角色配置项信息
     */
    @Override
	public RegisterConfigRoleInfo selectRegisterConfigRoleInfoByIdAssoc(Long id)
	{
	    return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoByIdAssoc(id);
	}	
	
	/**
     * 查询所有报名配置-角色配置项列表
     * 
     * @param 
     * @return 报名配置-角色配置项集合
     */
	@Override 
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoAll()
	{
		return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoAll();
	}	
	
	/**
     * 查询所有报名配置-角色配置项列表 外键关联
     * 
     * @param 
     * @return 报名配置-角色配置项集合
     */
	@Override 
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoAllAssoc()
	{
		return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoAllAssoc();
	}		
	
	/**
     * 查询报名配置-角色配置项列表
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 报名配置-角色配置项集合
     */
	@Override
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoList(RegisterConfigRoleInfo registerConfigRoleInfo)
	{
	    return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoList(registerConfigRoleInfo);
	}
	
	/**
     * 查询报名配置-角色配置项列表 外键关联
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 报名配置-角色配置项集合
     */
	@Override
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoListAssoc(RegisterConfigRoleInfo registerConfigRoleInfo)
	{
	    return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoListAssoc(registerConfigRoleInfo);
	}	
	
    /**
     * 新增报名配置-角色配置项
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 结果
     */
	@Override
	public int insertRegisterConfigRoleInfo(RegisterConfigRoleInfo registerConfigRoleInfo)
	{
	    return registerConfigRoleInfoMapper.insertRegisterConfigRoleInfo(registerConfigRoleInfo);
	}
	
	/**
     * 修改报名配置-角色配置项
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 结果
     */
	@Override
	public int updateRegisterConfigRoleInfo(RegisterConfigRoleInfo registerConfigRoleInfo)
	{
	    return registerConfigRoleInfoMapper.updateRegisterConfigRoleInfo(registerConfigRoleInfo);
	}

	/**
     * 删除报名配置-角色配置项对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterConfigRoleInfoByIds(String ids)
	{
		return registerConfigRoleInfoMapper.deleteRegisterConfigRoleInfoByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除报名配置-角色配置项对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterConfigRoleInfoById(Long id)
	{
		return registerConfigRoleInfoMapper.deleteRegisterConfigRoleInfoById(id);
	}

	@Override
	public int deleteRegisterConfigRoleInfoByConfigId(Long id) {
		return registerConfigRoleInfoMapper.deleteRegisterConfigRoleInfoByConfigId(id);
	}

	@Override
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoByConfigId(Long id) {
		return registerConfigRoleInfoMapper.selectRegisterConfigRoleInfoByConfigId(id);
	}


}
