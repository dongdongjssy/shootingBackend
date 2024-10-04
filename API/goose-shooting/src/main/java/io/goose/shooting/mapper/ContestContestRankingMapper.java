package io.goose.shooting.mapper;

import io.goose.shooting.domain.ContestContestRanking;
import io.goose.shooting.domain.GroupAndRanking;

import java.util.List;	

/**
 * 成绩 数据层
 * 
 * @author goose
 * @date 2020-05-27
 */
public interface ContestContestRankingMapper 
{
	/**
     * 查询成绩信息
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
	public ContestContestRanking selectContestContestRankingById(Long id);
	
	/**
     * 查询成绩信息 外键关联
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
	public ContestContestRanking selectContestContestRankingByIdAssoc(Long id);	

	/**
     * 查询所有成绩列表
     * 
     * @param 
     * @return 成绩集合
     */
	public List<ContestContestRanking> selectContestContestRankingAll();	
	
	/**
     * 查询所有成绩列表 外键关联
     * 
     * @param 
     * @return 成绩集合
     */
	public List<ContestContestRanking> selectContestContestRankingAllAssoc();		

	
	/**
     * 查询成绩列表
     * 
     * @param contestContestRanking 成绩信息
     * @return 成绩集合
     */
	public List<ContestContestRanking> selectContestContestRankingList(ContestContestRanking contestContestRanking);
	
	/**
     * 查询成绩列表 外键关联
     * 
     * @param contestContestRanking 成绩信息
     * @return 成绩集合
     */
	public List<ContestContestRanking> selectContestContestRankingListAssoc(ContestContestRanking contestContestRanking);	
	
	/**
     * 新增成绩
     * 
     * @param contestContestRanking 成绩信息
     * @return 结果
     */
	public int insertContestContestRanking(ContestContestRanking contestContestRanking);
	
	/**
     * 修改成绩
     * 
     * @param contestContestRanking 成绩信息
     * @return 结果
     */
	public int updateContestContestRanking(ContestContestRanking contestContestRanking);
	
	/**
     * 删除成绩
     * 
     * @param id 成绩ID
     * @return 结果
     */
	public int deleteContestContestRankingById(Long id);
	
	/**
     * 批量删除成绩
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestContestRankingByIds(String[] ids);

	public List<GroupAndRanking> selectContestGroupByContestId(Long contestId);
	
}