package io.goose.shooting.service;

import io.goose.shooting.domain.ContestContestRankingCoeff;
import java.util.List;

/**
 * 赛事具体成绩 服务层
 * 
 * @author goose
 * @date 2020-05-27
 */
public interface IContestContestRankingCoeffService 
{
	/**
     * 查询赛事具体成绩信息
     * 
     * @param id 赛事具体成绩ID
     * @return 赛事具体成绩信息
     */
	public ContestContestRankingCoeff selectContestContestRankingCoeffById(Long id);
	
	/**
     * 查询赛事具体成绩信息 外键关联
     * 
     * @param id 赛事具体成绩ID
     * @return 赛事具体成绩信息
     */
	public ContestContestRankingCoeff selectContestContestRankingCoeffByIdAssoc(Long id);	
	
	/**
     * 查询所有赛事具体成绩列表
     * 
     * @param 
     * @return 赛事具体成绩集合
     */
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffAll();		
	
	/**
     * 查询所有赛事具体成绩列表  外键关联
     * 
     * @param 
     * @return 赛事具体成绩集合
     */
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffAllAssoc();		
	
	/**
     * 查询赛事具体成绩列表
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 赛事具体成绩集合
     */
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffList(ContestContestRankingCoeff contestContestRankingCoeff);
	
	/**
     * 查询赛事具体成绩列表 外键关联
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 赛事具体成绩集合
     */
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffListAssoc(ContestContestRankingCoeff contestContestRankingCoeff);	
	
	/**
     * 新增赛事具体成绩
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 结果
     */
	public int insertContestContestRankingCoeff(ContestContestRankingCoeff contestContestRankingCoeff);
	
	/**
     * 修改赛事具体成绩
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 结果
     */
	public int updateContestContestRankingCoeff(ContestContestRankingCoeff contestContestRankingCoeff);
		
	/**
     * 删除赛事具体成绩信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestContestRankingCoeffByIds(String ids);
	
	/**
     * 删除赛事具体成绩信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestContestRankingCoeffById(Long id);
	

  
}
