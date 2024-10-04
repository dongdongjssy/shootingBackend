package io.goose.shooting.mapper;

import io.goose.shooting.domain.RegisterConfig;
import java.util.List;	

/**
 * 报名配置 数据层
 * 
 * @author goose
 * @date 2021-01-14
 */
public interface RegisterConfigMapper 
{
	/**
     * 查询报名配置信息
     * 
     * @param id 报名配置ID
     * @return 报名配置信息
     */
	public RegisterConfig selectRegisterConfigById(Long id);
	
	/**
     * 查询报名配置信息 外键关联
     * 
     * @param id 报名配置ID
     * @return 报名配置信息
     */
	public RegisterConfig selectRegisterConfigByIdAssoc(Long id);

	public RegisterConfig selectRegisterConfigByFkIdAndType(RegisterConfig registerConfig);

	/**
     * 查询所有报名配置列表
     * 
     * @param 
     * @return 报名配置集合
     */
	public List<RegisterConfig> selectRegisterConfigAll();	
	
	/**
     * 查询所有报名配置列表 外键关联
     * 
     * @param 
     * @return 报名配置集合
     */
	public List<RegisterConfig> selectRegisterConfigAllAssoc();		

	
	/**
     * 查询报名配置列表
     * 
     * @param registerConfig 报名配置信息
     * @return 报名配置集合
     */
	public List<RegisterConfig> selectRegisterConfigList(RegisterConfig registerConfig);
	
	/**
     * 查询报名配置列表 外键关联
     * 
     * @param registerConfig 报名配置信息
     * @return 报名配置集合
     */
	public List<RegisterConfig> selectRegisterConfigListAssoc(RegisterConfig registerConfig);	
	
	/**
     * 新增报名配置
     * 
     * @param registerConfig 报名配置信息
     * @return 结果
     */
	public int insertRegisterConfig(RegisterConfig registerConfig);
	
	/**
     * 修改报名配置
     * 
     * @param registerConfig 报名配置信息
     * @return 结果
     */
	public int updateRegisterConfig(RegisterConfig registerConfig);
	
	/**
     * 删除报名配置
     * 
     * @param id 报名配置ID
     * @return 结果
     */
	public int deleteRegisterConfigById(Long id);
	
	/**
     * 批量删除报名配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterConfigByIds(String[] ids);
	
}