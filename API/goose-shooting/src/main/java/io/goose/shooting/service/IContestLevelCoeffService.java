package io.goose.shooting.service;

import io.goose.shooting.domain.ContestLevelCoeff;
import java.util.List;

/**
 * 级别系数 服务层
 * 
 * @author goose
 * @date 2020-05-27
 */
public interface IContestLevelCoeffService 
{
	/**
     * 查询级别系数信息
     * 
     * @param id 级别系数ID
     * @return 级别系数信息
     */
	public ContestLevelCoeff selectContestLevelCoeffById(Long id);
	
	/**
     * 查询级别系数信息 外键关联
     * 
     * @param id 级别系数ID
     * @return 级别系数信息
     */
	public ContestLevelCoeff selectContestLevelCoeffByIdAssoc(Long id);	
	
	/**
     * 查询所有级别系数列表
     * 
     * @param 
     * @return 级别系数集合
     */
	public List<ContestLevelCoeff> selectContestLevelCoeffAll();		
	
	/**
     * 查询所有级别系数列表  外键关联
     * 
     * @param 
     * @return 级别系数集合
     */
	public List<ContestLevelCoeff> selectContestLevelCoeffAllAssoc();		
	
	/**
     * 查询级别系数列表
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 级别系数集合
     */
	public List<ContestLevelCoeff> selectContestLevelCoeffList(ContestLevelCoeff contestLevelCoeff);
	
	/**
     * 查询级别系数列表 外键关联
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 级别系数集合
     */
	public List<ContestLevelCoeff> selectContestLevelCoeffListAssoc(ContestLevelCoeff contestLevelCoeff);	
	
	/**
     * 新增级别系数
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 结果
     */
	public int insertContestLevelCoeff(ContestLevelCoeff contestLevelCoeff);
	
	/**
     * 修改级别系数
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 结果
     */
	public int updateContestLevelCoeff(ContestLevelCoeff contestLevelCoeff);
		
	/**
     * 删除级别系数信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestLevelCoeffByIds(String ids);
	
	/**
     * 删除级别系数信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestLevelCoeffById(Long id);
	

  
}
