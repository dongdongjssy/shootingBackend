package io.goose.shooting.mapper;

import io.goose.shooting.domain.ClubJudge;
import java.util.List;	

/**
 * 俱乐部裁判 数据层
 * 
 * @author goose
 * @date 2021-01-15
 */
public interface ClubJudgeMapper 
{
	/**
     * 查询俱乐部裁判信息
     * 
     * @param id 俱乐部裁判ID
     * @return 俱乐部裁判信息
     */
	public ClubJudge selectClubJudgeById(Long id);
	
	/**
     * 查询俱乐部裁判信息 外键关联
     * 
     * @param id 俱乐部裁判ID
     * @return 俱乐部裁判信息
     */
	public ClubJudge selectClubJudgeByIdAssoc(Long id);	

	/**
     * 查询所有俱乐部裁判列表
     * 
     * @param 
     * @return 俱乐部裁判集合
     */
	public List<ClubJudge> selectClubJudgeAll();	
	
	/**
     * 查询所有俱乐部裁判列表 外键关联
     * 
     * @param 
     * @return 俱乐部裁判集合
     */
	public List<ClubJudge> selectClubJudgeAllAssoc();		

	
	/**
     * 查询俱乐部裁判列表
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 俱乐部裁判集合
     */
	public List<ClubJudge> selectClubJudgeList(ClubJudge clubJudge);
	
	/**
     * 查询俱乐部裁判列表 外键关联
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 俱乐部裁判集合
     */
	public List<ClubJudge> selectClubJudgeListAssoc(ClubJudge clubJudge);	
	
	/**
     * 新增俱乐部裁判
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 结果
     */
	public int insertClubJudge(ClubJudge clubJudge);
	
	/**
     * 修改俱乐部裁判
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 结果
     */
	public int updateClubJudge(ClubJudge clubJudge);
	
	/**
     * 删除俱乐部裁判
     * 
     * @param id 俱乐部裁判ID
     * @return 结果
     */
	public int deleteClubJudgeById(Long id);
	
	/**
     * 批量删除俱乐部裁判
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClubJudgeByIds(String[] ids);
	
}