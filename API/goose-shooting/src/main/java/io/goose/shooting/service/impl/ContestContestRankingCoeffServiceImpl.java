package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ContestContestRankingCoeffMapper;
import io.goose.shooting.domain.ContestContestRankingCoeff;

import io.goose.shooting.service.IContestContestRankingCoeffService;
import io.goose.common.support.Convert;

/**
 * 赛事具体成绩 服务层实现
 * 
 * @author goose
 * @date 2020-05-27
 */
@Service
public class ContestContestRankingCoeffServiceImpl implements IContestContestRankingCoeffService 
{
	@Autowired
	private ContestContestRankingCoeffMapper contestContestRankingCoeffMapper;


	/**
     * 查询赛事具体成绩信息
     * 
     * @param id 赛事具体成绩ID
     * @return 赛事具体成绩信息
     */
    @Override
	public ContestContestRankingCoeff selectContestContestRankingCoeffById(Long id)
	{
	    return contestContestRankingCoeffMapper.selectContestContestRankingCoeffById(id);
	}
	
	/**
     * 查询赛事具体成绩信息 外键关联
     * 
     * @param id 赛事具体成绩ID
     * @return 赛事具体成绩信息
     */
    @Override
	public ContestContestRankingCoeff selectContestContestRankingCoeffByIdAssoc(Long id)
	{
	    return contestContestRankingCoeffMapper.selectContestContestRankingCoeffByIdAssoc(id);
	}	
	
	/**
     * 查询所有赛事具体成绩列表
     * 
     * @param 
     * @return 赛事具体成绩集合
     */
	@Override 
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffAll()
	{
		return contestContestRankingCoeffMapper.selectContestContestRankingCoeffAll();
	}	
	
	/**
     * 查询所有赛事具体成绩列表 外键关联
     * 
     * @param 
     * @return 赛事具体成绩集合
     */
	@Override 
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffAllAssoc()
	{
		return contestContestRankingCoeffMapper.selectContestContestRankingCoeffAllAssoc();
	}		
	
	/**
     * 查询赛事具体成绩列表
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 赛事具体成绩集合
     */
	@Override
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffList(ContestContestRankingCoeff contestContestRankingCoeff)
	{
	    return contestContestRankingCoeffMapper.selectContestContestRankingCoeffList(contestContestRankingCoeff);
	}
	
	/**
     * 查询赛事具体成绩列表 外键关联
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 赛事具体成绩集合
     */
	@Override
	public List<ContestContestRankingCoeff> selectContestContestRankingCoeffListAssoc(ContestContestRankingCoeff contestContestRankingCoeff)
	{
	    return contestContestRankingCoeffMapper.selectContestContestRankingCoeffListAssoc(contestContestRankingCoeff);
	}	
	
    /**
     * 新增赛事具体成绩
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 结果
     */
	@Override
	public int insertContestContestRankingCoeff(ContestContestRankingCoeff contestContestRankingCoeff)
	{
	    return contestContestRankingCoeffMapper.insertContestContestRankingCoeff(contestContestRankingCoeff);
	}
	
	/**
     * 修改赛事具体成绩
     * 
     * @param contestContestRankingCoeff 赛事具体成绩信息
     * @return 结果
     */
	@Override
	public int updateContestContestRankingCoeff(ContestContestRankingCoeff contestContestRankingCoeff)
	{
	    return contestContestRankingCoeffMapper.updateContestContestRankingCoeff(contestContestRankingCoeff);
	}

	/**
     * 删除赛事具体成绩对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestContestRankingCoeffByIds(String ids)
	{
		return contestContestRankingCoeffMapper.deleteContestContestRankingCoeffByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除赛事具体成绩对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestContestRankingCoeffById(Long id)
	{
		return contestContestRankingCoeffMapper.deleteContestContestRankingCoeffById(id);
	}
	
	



}
