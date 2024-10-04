package io.goose.shooting.service;

import io.goose.shooting.domain.Contest;
import java.util.List;

/**
 * 赛事 服务层
 * 
 * @author goose
 * @date 2020-05-03
 */
public interface IContestService 
{
	/**
     * 查询赛事信息
     * 
     * @param id 赛事ID
     * @return 赛事信息
     */
	public Contest selectContestById(Long id);
	
	/**
     * 查询赛事信息 外键关联
     * 
     * @param id 赛事ID
     * @return 赛事信息
     */
	public Contest selectContestByIdAssoc(Long id);	
	
	/**
     * 查询所有赛事列表
     * 
     * @param 
     * @return 赛事集合
     */
	public List<Contest> selectContestAll();		
	
	/**
     * 查询所有赛事列表  外键关联
     * 
     * @param 
     * @return 赛事集合
     */
	public List<Contest> selectContestAllAssoc();		
	
	/**
     * 查询赛事列表
     * 
     * @param contest 赛事信息
     * @return 赛事集合
     */
	public List<Contest> selectContestList(Contest contest);
	
	/**
     * 查询赛事列表 外键关联
     * 
     * @param contest 赛事信息
     * @return 赛事集合
     */
	public List<Contest> selectContestListAssoc(Contest contest);	
	
	/**
     * 新增赛事
     * 
     * @param contest 赛事信息
     * @return 结果
     */
	public int insertContest(Contest contest);
	
	/**
     * 修改赛事
     * 
     * @param contest 赛事信息
     * @return 结果
     */
	public int updateContest(Contest contest);
		
	/**
     * 删除赛事信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestByIds(String ids);
	
	/**
     * 删除赛事信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteContestById(Long id);
	

  
}
