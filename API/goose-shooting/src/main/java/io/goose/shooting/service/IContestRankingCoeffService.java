package io.goose.shooting.service;

import io.goose.shooting.domain.ContestRankingCoeff;
import java.util.List;

/**
 * 名次系数 服务层
 * 
 * @author goose
 * @date 2020-07-01
 */
public interface IContestRankingCoeffService 
{
	/**
     * 查询名次系数信息
     * 
     * @param id 名次系数ID
     * @return 名次系数信息
     */
	public ContestRankingCoeff selectContestRankingCoeffById(Long id);
	
	/**
     * 查询名次系数信息 外键关联
     * 
     * @param id 名次系数ID
     * @return 名次系数信息
     */
	public ContestRankingCoeff selectContestRankingCoeffByIdAssoc(Long id);	
	
	/**
     * 查询所有名次系数列表
     * 
     * @param 
     * @return 名次系数集合
     */
	public List<ContestRankingCoeff> selectContestRankingCoeffAll();		
	
	/**
     * 查询所有名次系数列表  外键关联
     * 
     * @param 
     * @return 名次系数集合
     */
	public List<ContestRankingCoeff> selectContestRankingCoeffAllAssoc();		
	
	/**
     * 查询名次系数列表
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 名次系数集合
     */
	public List<ContestRankingCoeff> selectContestRankingCoeffList(ContestRankingCoeff contestRankingCoeff);
	
	/**
     * 查询名次系数列表 外键关联
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 名次系数集合
     */
	public List<ContestRankingCoeff> selectContestRankingCoeffListAssoc(ContestRankingCoeff contestRankingCoeff);	
	
	/**
     * 新增名次系数
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 结果
     */
	public int insertContestRankingCoeff(ContestRankingCoeff contestRankingCoeff);
	
	/**
     * 修改名次系数
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 结果
     */
	public int updateContestRankingCoeff(ContestRankingCoeff contestRankingCoeff);
		
	/**
     * 删除名次系数信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestRankingCoeffByIds(String ids);
	
	/**
     * 删除名次系数信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestRankingCoeffById(Long id);
	

  
}
