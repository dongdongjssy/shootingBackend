package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RankInstructionsMapper;
import io.goose.shooting.domain.RankInstructions;

import io.goose.shooting.service.IRankInstructionsService;
import io.goose.common.support.Convert;

/**
 * 成绩说明 服务层实现
 * 
 * @author goose
 * @date 2021-01-06
 */
@Service
public class RankInstructionsServiceImpl implements IRankInstructionsService 
{
	@Autowired
	private RankInstructionsMapper rankInstructionsMapper;


	/**
     * 查询成绩说明信息
     * 
     * @param id 成绩说明ID
     * @return 成绩说明信息
     */
    @Override
	public RankInstructions selectRankInstructionsById(Long id)
	{
	    return rankInstructionsMapper.selectRankInstructionsById(id);
	}
	
	/**
     * 查询成绩说明信息 外键关联
     * 
     * @param id 成绩说明ID
     * @return 成绩说明信息
     */
    @Override
	public RankInstructions selectRankInstructionsByIdAssoc(Long id)
	{
	    return rankInstructionsMapper.selectRankInstructionsByIdAssoc(id);
	}	
	
	/**
     * 查询所有成绩说明列表
     * 
     * @param 
     * @return 成绩说明集合
     */
	@Override 
	public List<RankInstructions> selectRankInstructionsAll()
	{
		return rankInstructionsMapper.selectRankInstructionsAll();
	}	
	
	/**
     * 查询所有成绩说明列表 外键关联
     * 
     * @param 
     * @return 成绩说明集合
     */
	@Override 
	public List<RankInstructions> selectRankInstructionsAllAssoc()
	{
		return rankInstructionsMapper.selectRankInstructionsAllAssoc();
	}		
	
	/**
     * 查询成绩说明列表
     * 
     * @param rankInstructions 成绩说明信息
     * @return 成绩说明集合
     */
	@Override
	public List<RankInstructions> selectRankInstructionsList(RankInstructions rankInstructions)
	{
	    return rankInstructionsMapper.selectRankInstructionsList(rankInstructions);
	}
	
	/**
     * 查询成绩说明列表 外键关联
     * 
     * @param rankInstructions 成绩说明信息
     * @return 成绩说明集合
     */
	@Override
	public List<RankInstructions> selectRankInstructionsListAssoc(RankInstructions rankInstructions)
	{
	    return rankInstructionsMapper.selectRankInstructionsListAssoc(rankInstructions);
	}	
	
    /**
     * 新增成绩说明
     * 
     * @param rankInstructions 成绩说明信息
     * @return 结果
     */
	@Override
	public int insertRankInstructions(RankInstructions rankInstructions)
	{
	    return rankInstructionsMapper.insertRankInstructions(rankInstructions);
	}
	
	/**
     * 修改成绩说明
     * 
     * @param rankInstructions 成绩说明信息
     * @return 结果
     */
	@Override
	public int updateRankInstructions(RankInstructions rankInstructions)
	{
	    return rankInstructionsMapper.updateRankInstructions(rankInstructions);
	}

	/**
     * 删除成绩说明对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRankInstructionsByIds(String ids)
	{
		return rankInstructionsMapper.deleteRankInstructionsByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除成绩说明对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRankInstructionsById(Long id)
	{
		return rankInstructionsMapper.deleteRankInstructionsById(id);
	}
	
	



}
