package io.goose.shooting.mapper;

import io.goose.shooting.domain.Judge;
import java.util.List;	

/**
 * 裁判 数据层
 * 
 * @author goose
 * @date 2020-05-21
 */
public interface JudgeMapper 
{
	/**
     * 查询裁判信息
     * 
     * @param id 裁判ID
     * @return 裁判信息
     */
	public Judge selectJudgeById(Long id);
	
	/**
     * 查询裁判信息 外键关联
     * 
     * @param id 裁判ID
     * @return 裁判信息
     */
	public Judge selectJudgeByIdAssoc(Long id);	

	/**
     * 查询所有裁判列表
     * 
     * @param 
     * @return 裁判集合
     */
	public List<Judge> selectJudgeAll();	
	
	/**
     * 查询所有裁判列表 外键关联
     * 
     * @param 
     * @return 裁判集合
     */
	public List<Judge> selectJudgeAllAssoc();		

	
	/**
     * 查询裁判列表
     * 
     * @param judge 裁判信息
     * @return 裁判集合
     */
	public List<Judge> selectJudgeList(Judge judge);
	
	/**
     * 查询裁判列表 外键关联
     * 
     * @param judge 裁判信息
     * @return 裁判集合
     */
	public List<Judge> selectJudgeListAssoc(Judge judge);	
	
	/**
     * 新增裁判
     * 
     * @param judge 裁判信息
     * @return 结果
     */
	public int insertJudge(Judge judge);
	
	/**
     * 修改裁判
     * 
     * @param judge 裁判信息
     * @return 结果
     */
	public int updateJudge(Judge judge);
	
	/**
     * 删除裁判
     * 
     * @param id 裁判ID
     * @return 结果
     */
	public int deleteJudgeById(Long id);
	
	/**
     * 批量删除裁判
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteJudgeByIds(String[] ids);
	
}