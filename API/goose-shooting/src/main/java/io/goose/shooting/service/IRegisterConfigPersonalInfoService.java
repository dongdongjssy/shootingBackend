package io.goose.shooting.service;

import io.goose.shooting.domain.RegisterConfigPersonalInfo;
import java.util.List;

/**
 * 报名配置-个人配置项 服务层
 * 
 * @author goose
 * @date 2021-01-14
 */
public interface IRegisterConfigPersonalInfoService 
{
	/**
     * 查询报名配置-个人配置项信息
     * 
     * @param id 报名配置-个人配置项ID
     * @return 报名配置-个人配置项信息
     */
	public RegisterConfigPersonalInfo selectRegisterConfigPersonalInfoById(Long id);
	
	/**
     * 查询报名配置-个人配置项信息 外键关联
     * 
     * @param id 报名配置-个人配置项ID
     * @return 报名配置-个人配置项信息
     */
	public RegisterConfigPersonalInfo selectRegisterConfigPersonalInfoByIdAssoc(Long id);	
	
	/**
     * 查询所有报名配置-个人配置项列表
     * 
     * @param 
     * @return 报名配置-个人配置项集合
     */
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoAll();		
	
	/**
     * 查询所有报名配置-个人配置项列表  外键关联
     * 
     * @param 
     * @return 报名配置-个人配置项集合
     */
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoAllAssoc();		
	
	/**
     * 查询报名配置-个人配置项列表
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 报名配置-个人配置项集合
     */
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoList(RegisterConfigPersonalInfo registerConfigPersonalInfo);
	
	/**
     * 查询报名配置-个人配置项列表 外键关联
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 报名配置-个人配置项集合
     */
	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoListAssoc(RegisterConfigPersonalInfo registerConfigPersonalInfo);	
	
	/**
     * 新增报名配置-个人配置项
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 结果
     */
	public int insertRegisterConfigPersonalInfo(RegisterConfigPersonalInfo registerConfigPersonalInfo);
	
	/**
     * 修改报名配置-个人配置项
     * 
     * @param registerConfigPersonalInfo 报名配置-个人配置项信息
     * @return 结果
     */
	public int updateRegisterConfigPersonalInfo(RegisterConfigPersonalInfo registerConfigPersonalInfo);
		
	/**
     * 删除报名配置-个人配置项信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterConfigPersonalInfoByIds(String ids);
	
	/**
     * 删除报名配置-个人配置项信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterConfigPersonalInfoById(Long id);
	
	public int deleteRegisterConfigPersonalInfoByConfigId(Long id);

	public List<RegisterConfigPersonalInfo> selectRegisterConfigPersonalInfoByconfigId(Long id);
  
}
