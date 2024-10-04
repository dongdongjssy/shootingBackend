package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RegisterConfigPersonalInfoMapper;
import io.goose.shooting.domain.RegisterConfigPersonalInfo;

import io.goose.shooting.service.IRegisterConfigPersonalInfoService;
import io.goose.common.support.Convert;

/**
 * 报名配置-个人配置项 服务层实现
 * 
 * @author goose
 * @date 2021-01-14
 */
@Service
public class RegisterConfigPersonalInfoServiceImpl implements IRegisterConfigPersonalInfoService 
{
	@Autowired
	private RegisterConfigPersonalInfoMapper registerConfigPersonalInfoMapper;


	/**
     * 查询报名配置-个人配置项信息
     * 
     * @param id 报名配置-个人配置项ID
     * @return 报名配置-个人配置项信息
     */
    @Override
	public RegisterConfigPersonalInfo selectRegisterConfigPersonalInfoById(Long id)
	{
	    return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoById(id);
	}
	
	/**
     * 查询报名配置-个人配置项信息 外键关联
     * 
     * @param id 报名配置-个人配置项ID
     * @return 报名配置-个人配置项信息
     */
    @Override
	public RegisterConfigPersonalInfo selectRegisterConfigPersonalInfoByIdAssoc(Long id)
	{
	    return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoByIdAssoc(id);
	}	
	
	/**
     * 查询所有报名配置-个人配置项列表
     * 
     * @param 
     * @return 报名配置-个人配置项集合
     */
	@Override 
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoAll()
	{
		return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoAll();
	}	
	
	/**
     * 查询所有报名配置-个人配置项列表 外键关联
     * 
     * @param 
     * @return 报名配置-个人配置项集合
     */
	@Override 
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoAllAssoc()
	{
		return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoAllAssoc();
	}		
	
	/**
     * 查询报名配置-个人配置项列表
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 报名配置-个人配置项集合
     */
	@Override
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoList(RegisterConfigPersonalInfo registerConfigPersonalInfo)
	{
	    return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoList(registerConfigPersonalInfo);
	}
	
	/**
     * 查询报名配置-个人配置项列表 外键关联
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 报名配置-个人配置项集合
     */
	@Override
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoListAssoc(RegisterConfigPersonalInfo registerConfigPersonalInfo)
	{
	    return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoListAssoc(registerConfigPersonalInfo);
	}	
	
    /**
     * 新增报名配置-个人配置项
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 结果
     */
	@Override
	public int insertRegisterConfigPersonalInfo(RegisterConfigPersonalInfo registerConfigPersonalInfo)
	{
	    return registerConfigPersonalInfoMapper.insertRegisterConfigPersonalInfo(registerConfigPersonalInfo);
	}
	
	/**
     * 修改报名配置-个人配置项
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 结果
     */
	@Override
	public int updateRegisterConfigPersonalInfo(RegisterConfigPersonalInfo registerConfigPersonalInfo)
	{
	    return registerConfigPersonalInfoMapper.updateRegisterConfigPersonalInfo(registerConfigPersonalInfo);
	}

	/**
     * 删除报名配置-个人配置项对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterConfigPersonalInfoByIds(String ids)
	{
		return registerConfigPersonalInfoMapper.deleteRegisterConfigPersonalInfoByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除报名配置-个人配置项对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterConfigPersonalInfoById(Long id)
	{
		return registerConfigPersonalInfoMapper.deleteRegisterConfigPersonalInfoById(id);
	}

	@Override
	public int deleteRegisterConfigPersonalInfoByConfigId(Long id) {
		return registerConfigPersonalInfoMapper.deleteRegisterConfigPersonalInfoByConfigId(id);
	}

	@Override
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoByconfigId(Long id) {
		return registerConfigPersonalInfoMapper.selectRegisterConfigPersonalInfoByconfigId(id);
	}


}
