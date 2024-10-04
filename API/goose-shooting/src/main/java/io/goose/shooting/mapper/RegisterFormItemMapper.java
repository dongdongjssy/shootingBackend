package io.goose.shooting.mapper;

import io.goose.shooting.domain.RegisterFormItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报名项目 数据层
 * 
 * @author goose
 * @date 2021-01-16
 */
public interface RegisterFormItemMapper 
{
	/**
     * 查询报名项目信息
     * 
     * @param id 报名项目ID
     * @return 报名项目信息
     */
	public RegisterFormItem selectRegisterFormItemById(Long id);
	
	/**
     * 查询报名项目信息 外键关联
     * 
     * @param id 报名项目ID
     * @return 报名项目信息
     */
	public RegisterFormItem selectRegisterFormItemByIdAssoc(Long id);	

	/**
     * 查询所有报名项目列表
     * 
     * @param 
     * @return 报名项目集合
     */
	public List<RegisterFormItem> selectRegisterFormItemAll();	
	
	/**
     * 查询所有报名项目列表 外键关联
     * 
     * @param 
     * @return 报名项目集合
     */
	public List<RegisterFormItem> selectRegisterFormItemAllAssoc();		

	
	/**
     * 查询报名项目列表
     * 
     * @param registerFormItem 报名项目信息
     * @return 报名项目集合
     */
	public List<RegisterFormItem> selectRegisterFormItemList(RegisterFormItem registerFormItem);
	
	/**
     * 查询报名项目列表 外键关联
     * 
     * @param registerFormItem 报名项目信息
     * @return 报名项目集合
     */
	public List<RegisterFormItem> selectRegisterFormItemListAssoc(RegisterFormItem registerFormItem);	
	
	/**
     * 新增报名项目
     * 
     * @param registerFormItem 报名项目信息
     * @return 结果
     */
	public int insertRegisterFormItem(RegisterFormItem registerFormItem);
	
	/**
     * 修改报名项目
     * 
     * @param registerFormItem 报名项目信息
     * @return 结果
     */
	public int updateRegisterFormItem(RegisterFormItem registerFormItem);
	
	/**
     * 删除报名项目
     * 
     * @param id 报名项目ID
     * @return 结果
     */
	public int deleteRegisterFormItemById(Long id);
	
	/**
     * 批量删除报名项目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterFormItemByIds(String[] ids);

    public int insertRegisterFormItems(@Param("items") List<RegisterFormItem> items);
}