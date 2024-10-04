package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RegisterFormItemMapper;
import io.goose.shooting.domain.RegisterFormItem;

import io.goose.shooting.service.IRegisterFormItemService;
import io.goose.common.support.Convert;

/**
 * 报名项目 服务层实现
 * 
 * @author goose
 * @date 2021-01-16
 */
@Service
public class RegisterFormItemServiceImpl implements IRegisterFormItemService 
{
	@Autowired
	private RegisterFormItemMapper registerFormItemMapper;


	/**
     * 查询报名项目信息
     * 
     * @param id 报名项目ID
     * @return 报名项目信息
     */
    @Override
	public RegisterFormItem selectRegisterFormItemById(Long id)
	{
	    return registerFormItemMapper.selectRegisterFormItemById(id);
	}
	
	/**
     * 查询报名项目信息 外键关联
     * 
     * @param id 报名项目ID
     * @return 报名项目信息
     */
    @Override
	public RegisterFormItem selectRegisterFormItemByIdAssoc(Long id)
	{
	    return registerFormItemMapper.selectRegisterFormItemByIdAssoc(id);
	}	
	
	/**
     * 查询所有报名项目列表
     * 
     * @param 
     * @return 报名项目集合
     */
	@Override 
	public List<RegisterFormItem> selectRegisterFormItemAll()
	{
		return registerFormItemMapper.selectRegisterFormItemAll();
	}	
	
	/**
     * 查询所有报名项目列表 外键关联
     * 
     * @param 
     * @return 报名项目集合
     */
	@Override 
	public List<RegisterFormItem> selectRegisterFormItemAllAssoc()
	{
		return registerFormItemMapper.selectRegisterFormItemAllAssoc();
	}		
	
	/**
     * 查询报名项目列表
     * 
     * @param registerFormItem 报名项目信息
     * @return 报名项目集合
     */
	@Override
	public List<RegisterFormItem> selectRegisterFormItemList(RegisterFormItem registerFormItem)
	{
	    return registerFormItemMapper.selectRegisterFormItemList(registerFormItem);
	}
	
	/**
     * 查询报名项目列表 外键关联
     * 
     * @param registerFormItem 报名项目信息
     * @return 报名项目集合
     */
	@Override
	public List<RegisterFormItem> selectRegisterFormItemListAssoc(RegisterFormItem registerFormItem)
	{
	    return registerFormItemMapper.selectRegisterFormItemListAssoc(registerFormItem);
	}	
	
    /**
     * 新增报名项目
     * 
     * @param registerFormItem 报名项目信息
     * @return 结果
     */
	@Override
	public int insertRegisterFormItem(RegisterFormItem registerFormItem)
	{
	    return registerFormItemMapper.insertRegisterFormItem(registerFormItem);
	}

	@Override
	public int insertRegisterFormItems(List<RegisterFormItem> items) {
		return registerFormItemMapper.insertRegisterFormItems(items);
	}

	/**
     * 修改报名项目
     * 
     * @param registerFormItem 报名项目信息
     * @return 结果
     */
	@Override
	public int updateRegisterFormItem(RegisterFormItem registerFormItem)
	{
	    return registerFormItemMapper.updateRegisterFormItem(registerFormItem);
	}

	/**
     * 删除报名项目对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterFormItemByIds(String ids)
	{
		return registerFormItemMapper.deleteRegisterFormItemByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除报名项目对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRegisterFormItemById(Long id)
	{
		return registerFormItemMapper.deleteRegisterFormItemById(id);
	}
	
	



}
