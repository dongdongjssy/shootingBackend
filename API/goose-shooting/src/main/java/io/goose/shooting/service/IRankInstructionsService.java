package io.goose.shooting.service;

import io.goose.shooting.domain.RankInstructions;
import java.util.List;

/**
 * 成绩说明 服务层
 * 
 * @author goose
 * @date 2021-01-06
 */
public interface IRankInstructionsService 
{
	/**
     * 查询成绩说明信息
     * 
     * @param id 成绩说明ID
     * @return 成绩说明信息
     */
	public RankInstructions selectRankInstructionsById(Long id);
	
	/**
     * 查询成绩说明信息 外键关联
     * 
     * @param id 成绩说明ID
     * @return 成绩说明信息
     */
	public RankInstructions selectRankInstructionsByIdAssoc(Long id);	
	
	/**
     * 查询所有成绩说明列表
     * 
     * @param 
     * @return 成绩说明集合
     */
	public List<RankInstructions> selectRankInstructionsAll();		
	
	/**
     * 查询所有成绩说明列表  外键关联
     * 
     * @param 
     * @return 成绩说明集合
     */
	public List<RankInstructions> selectRankInstructionsAllAssoc();		
	
	/**
     * 查询成绩说明列表
     * 
     * @param rankInstructions 成绩说明信息
     * @return 成绩说明集合
     */
	public List<RankInstructions> selectRankInstructionsList(RankInstructions rankInstructions);
	
	/**
     * 查询成绩说明列表 外键关联
     * 
     * @param rankInstructions 成绩说明信息
     * @return 成绩说明集合
     */
	public List<RankInstructions> selectRankInstructionsListAssoc(RankInstructions rankInstructions);	
	
	/**
     * 新增成绩说明
     * 
     * @param rankInstructions 成绩说明信息
     * @return 结果
     */
	public int insertRankInstructions(RankInstructions rankInstructions);
	
	/**
     * 修改成绩说明
     * 
     * @param rankInstructions 成绩说明信息
     * @return 结果
     */
	public int updateRankInstructions(RankInstructions rankInstructions);
		
	/**
     * 删除成绩说明信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRankInstructionsByIds(String ids);
	
	/**
     * 删除成绩说明信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteRankInstructionsById(Long id);
	

  
}
