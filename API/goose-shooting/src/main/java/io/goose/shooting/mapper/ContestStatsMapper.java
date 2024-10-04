package io.goose.shooting.mapper;

import io.goose.shooting.domain.AgeGroup;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.ContestContestRanking;
import io.goose.shooting.domain.ContestStats;
import io.goose.shooting.domain.MyMark;
import io.goose.shooting.domain.Stats;

import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 选手排名 数据层
 * 
 * @author goose
 * @date 2020-05-28
 */
public interface ContestStatsMapper 
{
	/**
     * 查询选手排名信息
     * 
     * @param id 选手排名ID
     * @return 选手排名信息
     */
	public ContestStats selectContestStatsById(Long id);
	
	/**
     * 查询选手排名信息 外键关联
     * 
     * @param id 选手排名ID
     * @return 选手排名信息
     */
	public ContestStats selectContestStatsByIdAssoc(Long id);	

	/**
     * 查询所有选手排名列表
     * 
     * @param 
     * @return 选手排名集合
     */
	public List<ContestStats> selectContestStatsAll();	
	
	/**
     * 查询所有选手排名列表 外键关联
     * 
     * @param 
     * @return 选手排名集合
     */
	public List<ContestStats> selectContestStatsAllAssoc();		

	
	/**
     * 查询选手排名列表
     * 
     * @param contestStats 选手排名信息
     * @return 选手排名集合
     */
	public List<ContestStats> selectContestStatsList(ContestStats contestStats);
	
	/**
     * 查询选手排名列表 外键关联
     * 
     * @param contestStats 选手排名信息
     * @return 选手排名集合
     */
	public List<ContestStats> selectContestStatsListAssoc(ContestStats contestStats);	
	
	/**
     * 新增选手排名
     * 
     * @param contestStats 选手排名信息
     * @return 结果
     */
	public int insertContestStats(ContestStats contestStats);
	
	/**
     * 修改选手排名
     * 
     * @param contestStats 选手排名信息
     * @return 结果
     */
	public int updateContestStats(ContestStats contestStats);
	
	/**
     * 删除选手排名
     * 
     * @param id 选手排名ID
     * @return 结果
     */
	public int deleteContestStatsById(Long id);
	
	/**
     * 批量删除选手排名
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestStatsByIds(String[] ids);

	public List<Integer> selectYear(ContestStats contestStats);

	public List<MyMark> selectMyMark(ContestStats contestStats);

	public List<AgeGroup> getAgeGroup();

	public List<Stats> selectAllUser(@Param("year")Integer year,@Param("courseId")Long courseId,@Param("typeId") Long typeId,@Param("contestGroupId") Long contestGroupId,@Param("ageGroup") Integer ageGroup);

	public List<ContestContestRanking> selectContestRanking(@Param("userId")Long userId,@Param("year") Integer year,@Param("courseId") Long courseId,@Param("typeId") Long typeId,
			@Param("contestGroupId") Long contestGroupId,@Param("ageGroup") Integer ageGroup);

}