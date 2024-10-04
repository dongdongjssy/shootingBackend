package io.goose.shooting.mapper;

import io.goose.shooting.domain.Register;
import java.util.List;	

/**
 * 报名 数据层
 * 
 * @author goose
 * @date 2020-05-19
 */
public interface RegisterMapper 
{
	/**
     * 查询报名信息
     * 
     * @param id 报名ID
     * @return 报名信息
     */
	public Register selectRegisterById(Long id);
	
	/**
     * 查询报名信息 外键关联
     * 
     * @param id 报名ID
     * @return 报名信息
     */
	public Register selectRegisterByIdAssoc(Long id);	

	/**
     * 查询所有报名列表
     * 
     * @param 
     * @return 报名集合
     */
	public List<Register> selectRegisterAll();	
	
	/**
     * 查询所有报名列表 外键关联
     * 
     * @param 
     * @return 报名集合
     */
	public List<Register> selectRegisterAllAssoc();		

	
	/**
     * 查询报名列表
     * 
     * @param register 报名信息
     * @return 报名集合
     */
	public List<Register> selectRegisterList(Register register);
	
	/**
     * 查询报名列表 外键关联
     * 
     * @param register 报名信息
     * @return 报名集合
     */
	public List<Register> selectRegisterListAssoc(Register register);	
	
	/**
     * 新增报名
     * 
     * @param register 报名信息
     * @return 结果
     */
	public int insertRegister(Register register);
	
	/**
     * 修改报名
     * 
     * @param register 报名信息
     * @return 结果
     */
	public int updateRegister(Register register);
	
	/**
     * 删除报名
     * 
     * @param id 报名ID
     * @return 结果
     */
	public int deleteRegisterById(Long id);
	
	/**
     * 批量删除报名
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRegisterByIds(String[] ids);
	
}