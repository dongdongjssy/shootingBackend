package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ContestRankingCoeffDefaultMapper;
import io.goose.shooting.domain.ContestRankingCoeffDefault;

import io.goose.shooting.service.IContestRankingCoeffDefaultService;
import io.goose.common.support.Convert;

/**
 * 默认名次系数 服务层实现
 * 
 * @author goose
 * @date 2020-07-01
 */
@Service
public class ContestRankingCoeffDefaultServiceImpl implements IContestRankingCoeffDefaultService 
{
	@Autowired
	private ContestRankingCoeffDefaultMapper contestRankingCoeffDefaultMapper;


	/**
     * 查询默认名次系数信息
     * 
     * @param id 默认名次系数ID
     * @return 默认名次系数信息
     */
    @Override
	public ContestRankingCoeffDefault selectContestRankingCoeffDefaultById(Long id)
	{
	    return contestRankingCoeffDefaultMapper.selectContestRankingCoeffDefaultById(id);
	}
	
	/**
     * 查询默认名次系数信息 外键关联
     * 
     * @param id 默认名次系数ID
     * @return 默认名次系数信息
     */
    @Override
	public ContestRankingCoeffDefault selectContestRankingCoeffDefaultByIdAssoc(Long id)
	{
	    return contestRankingCoeffDefaultMapper.selectContestRankingCoeffDefaultByIdAssoc(id);
	}	
	
	/**
     * 查询所有默认名次系数列表
     * 
     * @param 
     * @return 默认名次系数集合
     */
	@Override 
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultAll()
	{
		return contestRankingCoeffDefaultMapper.selectContestRankingCoeffDefaultAll();
	}	
	
	/**
     * 查询所有默认名次系数列表 外键关联
     * 
     * @param 
     * @return 默认名次系数集合
     */
	@Override 
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultAllAssoc()
	{
		return contestRankingCoeffDefaultMapper.selectContestRankingCoeffDefaultAllAssoc();
	}		
	
	/**
     * 查询默认名次系数列表
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 默认名次系数集合
     */
	@Override
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultList(ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
	    return contestRankingCoeffDefaultMapper.selectContestRankingCoeffDefaultList(contestRankingCoeffDefault);
	}
	
	/**
     * 查询默认名次系数列表 外键关联
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 默认名次系数集合
     */
	@Override
	public List<ContestRankingCoeffDefault> selectContestRankingCoeffDefaultListAssoc(ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
	    return contestRankingCoeffDefaultMapper.selectContestRankingCoeffDefaultListAssoc(contestRankingCoeffDefault);
	}	
	
    /**
     * 新增默认名次系数
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 结果
     */
	@Override
	public int insertContestRankingCoeffDefault(ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
	    return contestRankingCoeffDefaultMapper.insertContestRankingCoeffDefault(contestRankingCoeffDefault);
	}
	
	/**
     * 修改默认名次系数
     * 
     * @param contestRankingCoeffDefault 默认名次系数信息
     * @return 结果
     */
	@Override
	public int updateContestRankingCoeffDefault(ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
	    return contestRankingCoeffDefaultMapper.updateContestRankingCoeffDefault(contestRankingCoeffDefault);
	}

	/**
     * 删除默认名次系数对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestRankingCoeffDefaultByIds(String ids)
	{
		return contestRankingCoeffDefaultMapper.deleteContestRankingCoeffDefaultByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除默认名次系数对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestRankingCoeffDefaultById(Long id)
	{
		return contestRankingCoeffDefaultMapper.deleteContestRankingCoeffDefaultById(id);
	}
	
	



}
