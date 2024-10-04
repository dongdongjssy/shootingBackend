package io.goose.shooting.mapper;

import io.goose.shooting.domain.RegisterConfigRoleInfo;
import java.util.List;	

/**
 * 报名配置-角色配置项 数据层
 * 
 * @author goose
 * @date 2021-01-14
 */
public interface RegisterConfigRoleInfoMapper 
{
	/**
     * 查询报名配置-角色配置项信息
     * 
     * @param id 报名配置-角色配置项ID
     * @return 报名配置-角色配置项信息
     */
	public RegisterConfigRoleInfo selectRegisterConfigRoleInfoById(Long id);
	
	/**
     * 查询报名配置-角色配置项信息 外键关联
     * 
     * @param id 报名配置-角色配置项ID
     * @return 报名配置-角色配置项信息
     */
	public RegisterConfigRoleInfo selectRegisterConfigRoleInfoByIdAssoc(Long id);	

	/**
     * 查询所有报名配置-角色配置项列表
     * 
     * @param 
     * @return 报名配置-角色配置项集合
     */
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoAll();	
	
	/**
     * 查询所有报名配置-角色配置项列表 外键关联
     * 
     * @param 
     * @return 报名配置-角色配置项集合
     */
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoAllAssoc();		

	
	/**
     * 查询报名配置-角色配置项列表
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 报名配置-角色配置项集合
     */
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoList(RegisterConfigRoleInfo registerConfigRoleInfo);
	
	/**
     * 查询报名配置-角色配置项列表 外键关联
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 报名配置-角色配置项集合
     */
	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoListAssoc(RegisterConfigRoleInfo registerConfigRoleInfo);	
	
	/**
     * 新增报名配置-角色配置项
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 结果
     */
	public int insertRegisterConfigRoleInfo(RegisterConfigRoleInfo registerConfigRoleInfo);
	
	/**
     * 修改报名配置-角色配置项
     * 
     * @param registerConfigRoleInfo 报名配置-角色配置项信息
     * @return 结果
     */
	public int updateRegisterConfigRoleInfo(RegisterConfigRoleInfo registerConfigRoleInfo);
	
	/**
     * 删除报名配置-角色配置项
     * 
     * @param id 报名配置-角色配置项ID
     * @return 结果
     */
	public int deleteRegisterConfigRoleInfoById(Long id);
	
	/**
     * 批量删除报名配置-角色配置项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterConfigRoleInfoByIds(String[] ids);

	public int deleteRegisterConfigRoleInfoByConfigId(Long id);

	public List<RegisterConfigRoleInfo> selectRegisterConfigRoleInfoByConfigId(Long id);
	
}