package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ContestLevelCoeffMapper;
import io.goose.shooting.domain.ContestLevelCoeff;

import io.goose.shooting.service.IContestLevelCoeffService;
import io.goose.common.support.Convert;

/**
 * 级别系数 服务层实现
 * 
 * @author goose
 * @date 2020-05-27
 */
@Service
public class ContestLevelCoeffServiceImpl implements IContestLevelCoeffService 
{
	@Autowired
	private ContestLevelCoeffMapper contestLevelCoeffMapper;


	/**
     * 查询级别系数信息
     * 
     * @param id 级别系数ID
     * @return 级别系数信息
     */
    @Override
	public ContestLevelCoeff selectContestLevelCoeffById(Long id)
	{
	    return contestLevelCoeffMapper.selectContestLevelCoeffById(id);
	}
	
	/**
     * 查询级别系数信息 外键关联
     * 
     * @param id 级别系数ID
     * @return 级别系数信息
     */
    @Override
	public ContestLevelCoeff selectContestLevelCoeffByIdAssoc(Long id)
	{
	    return contestLevelCoeffMapper.selectContestLevelCoeffByIdAssoc(id);
	}	
	
	/**
     * 查询所有级别系数列表
     * 
     * @param 
     * @return 级别系数集合
     */
	@Override 
	public List<ContestLevelCoeff> selectContestLevelCoeffAll()
	{
		return contestLevelCoeffMapper.selectContestLevelCoeffAll();
	}	
	
	/**
     * 查询所有级别系数列表 外键关联
     * 
     * @param 
     * @return 级别系数集合
     */
	@Override 
	public List<ContestLevelCoeff> selectContestLevelCoeffAllAssoc()
	{
		return contestLevelCoeffMapper.selectContestLevelCoeffAllAssoc();
	}		
	
	/**
     * 查询级别系数列表
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 级别系数集合
     */
	@Override
	public List<ContestLevelCoeff> selectContestLevelCoeffList(ContestLevelCoeff contestLevelCoeff)
	{
	    return contestLevelCoeffMapper.selectContestLevelCoeffList(contestLevelCoeff);
	}
	
	/**
     * 查询级别系数列表 外键关联
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 级别系数集合
     */
	@Override
	public List<ContestLevelCoeff> selectContestLevelCoeffListAssoc(ContestLevelCoeff contestLevelCoeff)
	{
	    return contestLevelCoeffMapper.selectContestLevelCoeffListAssoc(contestLevelCoeff);
	}	
	
    /**
     * 新增级别系数
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 结果
     */
	@Override
	public int insertContestLevelCoeff(ContestLevelCoeff contestLevelCoeff)
	{
	    return contestLevelCoeffMapper.insertContestLevelCoeff(contestLevelCoeff);
	}
	
	/**
     * 修改级别系数
     * 
     * @param contestLevelCoeff 级别系数信息
     * @return 结果
     */
	@Override
	public int updateContestLevelCoeff(ContestLevelCoeff contestLevelCoeff)
	{
	    return contestLevelCoeffMapper.updateContestLevelCoeff(contestLevelCoeff);
	}

	/**
     * 删除级别系数对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestLevelCoeffByIds(String ids)
	{
		return contestLevelCoeffMapper.deleteContestLevelCoeffByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除级别系数对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestLevelCoeffById(Long id)
	{
		return contestLevelCoeffMapper.deleteContestLevelCoeffById(id);
	}
	
	



}
