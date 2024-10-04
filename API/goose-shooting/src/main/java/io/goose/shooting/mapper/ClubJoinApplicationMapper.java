package io.goose.shooting.mapper;

import io.goose.shooting.domain.ClubJoinApplication;
import java.util.List;	

/**
 * 入群申请 数据层
 * 
 * @author goose
 * @date 2020-05-23
 */
public interface ClubJoinApplicationMapper 
{
	/**
     * 查询入群申请信息
     * 
     * @param id 入群申请ID
     * @return 入群申请信息
     */
	public ClubJoinApplication selectClubJoinApplicationById(Long id);
	
	/**
     * 查询入群申请信息 外键关联
     * 
     * @param id 入群申请ID
     * @return 入群申请信息
     */
	public ClubJoinApplication selectClubJoinApplicationByIdAssoc(Long id);	

	/**
     * 查询所有入群申请列表
     * 
     * @param 
     * @return 入群申请集合
     */
	public List<ClubJoinApplication> selectClubJoinApplicationAll();	
	
	/**
     * 查询所有入群申请列表 外键关联
     * 
     * @param 
     * @return 入群申请集合
     */
	public List<ClubJoinApplication> selectClubJoinApplicationAllAssoc();		

	
	/**
     * 查询入群申请列表
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 入群申请集合
     */
	public List<ClubJoinApplication> selectClubJoinApplicationList(ClubJoinApplication clubJoinApplication);
	
	/**
     * 查询入群申请列表 外键关联
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 入群申请集合
     */
	public List<ClubJoinApplication> selectClubJoinApplicationListAssoc(ClubJoinApplication clubJoinApplication);	
	
	/**
     * 新增入群申请
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 结果
     */
	public int insertClubJoinApplication(ClubJoinApplication clubJoinApplication);
	
	/**
     * 修改入群申请
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 结果
     */
	public int updateClubJoinApplication(ClubJoinApplication clubJoinApplication);
	
	/**
     * 删除入群申请
     * 
     * @param id 入群申请ID
     * @return 结果
     */
	public int deleteClubJoinApplicationById(Long id);
	
	/**
     * 批量删除入群申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClubJoinApplicationByIds(String[] ids);
	
}