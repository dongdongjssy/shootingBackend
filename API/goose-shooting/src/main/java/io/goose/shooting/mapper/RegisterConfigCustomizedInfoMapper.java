package io.goose.shooting.mapper;

import io.goose.shooting.domain.RegisterConfigCustomizedInfo;
import java.util.List;	

/**
 * 报名配置-自定义项目 数据层
 * 
 * @author goose
 * @date 2021-01-14
 */
public interface RegisterConfigCustomizedInfoMapper 
{
	/**
     * 查询报名配置-自定义项目信息
     * 
     * @param id 报名配置-自定义项目ID
     * @return 报名配置-自定义项目信息
     */
	public RegisterConfigCustomizedInfo selectRegisterConfigCustomizedInfoById(Long id);
	
	/**
     * 查询报名配置-自定义项目信息 外键关联
     * 
     * @param id 报名配置-自定义项目ID
     * @return 报名配置-自定义项目信息
     */
	public RegisterConfigCustomizedInfo selectRegisterConfigCustomizedInfoByIdAssoc(Long id);

	public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoByConfigId(Long id);

	/**
     * 查询所有报名配置-自定义项目列表
     * 
     * @param 
     * @return 报名配置-自定义项目集合
     */
	public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoAll();	
	
	/**
     * 查询所有报名配置-自定义项目列表 外键关联
     * 
     * @param 
     * @return 报名配置-自定义项目集合
     */
	public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoAllAssoc();		

	
	/**
     * 查询报名配置-自定义项目列表
     * 
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 报名配置-自定义项目集合
     */
	public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoList(RegisterConfigCustomizedInfo registerConfigCustomizedInfo);
	
	/**
     * 查询报名配置-自定义项目列表 外键关联
     * 
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 报名配置-自定义项目集合
     */
	public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoListAssoc(RegisterConfigCustomizedInfo registerConfigCustomizedInfo);	
	
	/**
     * 新增报名配置-自定义项目
     * 
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 结果
     */
	public int insertRegisterConfigCustomizedInfo(RegisterConfigCustomizedInfo registerConfigCustomizedInfo);
	
	/**
     * 修改报名配置-自定义项目
     * 
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 结果
     */
	public int updateRegisterConfigCustomizedInfo(RegisterConfigCustomizedInfo registerConfigCustomizedInfo);
	
	/**
     * 删除报名配置-自定义项目
     * 
     * @param id 报名配置-自定义项目ID
     * @return 结果
     */
	public int deleteRegisterConfigCustomizedInfoById(Long id);
	
	/**
     * 批量删除报名配置-自定义项目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterConfigCustomizedInfoByIds(String[] ids);

	public int deleteRegisterConfigCustomizedInfoByConfigId(Long id);
	
}