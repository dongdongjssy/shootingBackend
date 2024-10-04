package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ContestRankingCoeffMapper;
import io.goose.shooting.domain.ContestRankingCoeff;

import io.goose.shooting.service.IContestRankingCoeffService;
import io.goose.common.support.Convert;

/**
 * 名次系数 服务层实现
 * 
 * @author goose
 * @date 2020-07-01
 */
@Service
public class ContestRankingCoeffServiceImpl implements IContestRankingCoeffService 
{
	@Autowired
	private ContestRankingCoeffMapper contestRankingCoeffMapper;


	/**
     * 查询名次系数信息
     * 
     * @param id 名次系数ID
     * @return 名次系数信息
     */
    @Override
	public ContestRankingCoeff selectContestRankingCoeffById(Long id)
	{
	    return contestRankingCoeffMapper.selectContestRankingCoeffById(id);
	}
	
	/**
     * 查询名次系数信息 外键关联
     * 
     * @param id 名次系数ID
     * @return 名次系数信息
     */
    @Override
	public ContestRankingCoeff selectContestRankingCoeffByIdAssoc(Long id)
	{
	    return contestRankingCoeffMapper.selectContestRankingCoeffByIdAssoc(id);
	}	
	
	/**
     * 查询所有名次系数列表
     * 
     * @param 
     * @return 名次系数集合
     */
	@Override 
	public List<ContestRankingCoeff> selectContestRankingCoeffAll()
	{
		return contestRankingCoeffMapper.selectContestRankingCoeffAll();
	}	
	
	/**
     * 查询所有名次系数列表 外键关联
     * 
     * @param 
     * @return 名次系数集合
     */
	@Override 
	public List<ContestRankingCoeff> selectContestRankingCoeffAllAssoc()
	{
		return contestRankingCoeffMapper.selectContestRankingCoeffAllAssoc();
	}		
	
	/**
     * 查询名次系数列表
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 名次系数集合
     */
	@Override
	public List<ContestRankingCoeff> selectContestRankingCoeffList(ContestRankingCoeff contestRankingCoeff)
	{
	    return contestRankingCoeffMapper.selectContestRankingCoeffList(contestRankingCoeff);
	}
	
	/**
     * 查询名次系数列表 外键关联
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 名次系数集合
     */
	@Override
	public List<ContestRankingCoeff> selectContestRankingCoeffListAssoc(ContestRankingCoeff contestRankingCoeff)
	{
	    return contestRankingCoeffMapper.selectContestRankingCoeffListAssoc(contestRankingCoeff);
	}	
	
    /**
     * 新增名次系数
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 结果
     */
	@Override
	public int insertContestRankingCoeff(ContestRankingCoeff contestRankingCoeff)
	{
	    return contestRankingCoeffMapper.insertContestRankingCoeff(contestRankingCoeff);
	}
	
	/**
     * 修改名次系数
     * 
     * @param contestRankingCoeff 名次系数信息
     * @return 结果
     */
	@Override
	public int updateContestRankingCoeff(ContestRankingCoeff contestRankingCoeff)
	{
	    return contestRankingCoeffMapper.updateContestRankingCoeff(contestRankingCoeff);
	}

	/**
     * 删除名次系数对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestRankingCoeffByIds(String ids)
	{
		return contestRankingCoeffMapper.deleteContestRankingCoeffByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除名次系数对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestRankingCoeffById(Long id)
	{
		return contestRankingCoeffMapper.deleteContestRankingCoeffById(id);
	}
	
	



}
