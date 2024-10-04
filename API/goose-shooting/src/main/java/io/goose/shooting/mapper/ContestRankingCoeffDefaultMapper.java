package io.goose.shooting.mapper;

import io.goose.shooting.domain.ContestRankingCoeffDefault;
import java.util.List;	

/**
 * 默认名次系数 数据层
 * 
 * @author goose
 * @date 2020-07-01
 */
public interface ContestRankingCoeffDefaultMapper 
{
	/**
     * 查询默认名次系数信息
     * 
     * @param id 默认名次系数ID
     * @return 默认名次系数信息
     */
	public ContestRankingCoeffDefault selectContestRankingCoeffDefaultById(Long id);
	
	/**
     * 查询默认名次系数信息 外键关联
     * 
     * @param id 默认名次系数ID
     * @return 默认名次系数信息
     */
	public ContestRankingCoeffDefault selectContestRankingCoeffDefaultByIdAssoc(Long id);	

	/**
     * 查询所有默认名次系数列表
     * 
     * @param 
     * @return 默认名次系数集合
     */
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultAll();	
	
	/**
     * 查询所有默认名次系数列表 外键关联
     * 
     * @param 
     * @return 默认名次系数集合
     */
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultAllAssoc();		

	
	/**
     * 查询默认名次系数列表
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 默认名次系数集合
     */
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultList(ContestRankingCoeffDefault contestRankingCoeffDefault);
	
	/**
     * 查询默认名次系数列表 外键关联
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 默认名次系数集合
     */
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultListAssoc(ContestRankingCoeffDefault contestRankingCoeffDefault);	
	
	/**
     * 新增默认名次系数
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 结果
     */
	public int insertContestRankingCoeffDefault(ContestRankingCoeffDefault contestRankingCoeffDefault);
	
	/**
     * 修改默认名次系数
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 结果
     */
	public int updateContestRankingCoeffDefault(ContestRankingCoeffDefault contestRankingCoeffDefault);
	
	/**
     * 删除默认名次系数
     * 
     * @param id 默认名次系数ID
     * @return 结果
     */
	public int deleteContestRankingCoeffDefaultById(Long id);
	
	/**
     * 批量删除默认名次系数
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestRankingCoeffDefaultByIds(String[] ids);
	
}