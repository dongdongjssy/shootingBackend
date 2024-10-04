package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ClubJoinApplicationMapper;
import io.goose.shooting.domain.ClubJoinApplication;

import io.goose.shooting.service.IClubJoinApplicationService;
import io.goose.common.support.Convert;

/**
 * 入群申请 服务层实现
 * 
 * @author goose
 * @date 2020-05-23
 */
@Service
public class ClubJoinApplicationServiceImpl implements IClubJoinApplicationService 
{
	@Autowired
	private ClubJoinApplicationMapper clubJoinApplicationMapper;


	/**
     * 查询入群申请信息
     * 
     * @param id 入群申请ID
     * @return 入群申请信息
     */
    @Override
	public ClubJoinApplication selectClubJoinApplicationById(Long id)
	{
	    return clubJoinApplicationMapper.selectClubJoinApplicationById(id);
	}
	
	/**
     * 查询入群申请信息 外键关联
     * 
     * @param id 入群申请ID
     * @return 入群申请信息
     */
    @Override
	public ClubJoinApplication selectClubJoinApplicationByIdAssoc(Long id)
	{
	    return clubJoinApplicationMapper.selectClubJoinApplicationByIdAssoc(id);
	}	
	
	/**
     * 查询所有入群申请列表
     * 
     * @param 
     * @return 入群申请集合
     */
	@Override 
	public List<ClubJoinApplication> selectClubJoinApplicationAll()
	{
		return clubJoinApplicationMapper.selectClubJoinApplicationAll();
	}	
	
	/**
     * 查询所有入群申请列表 外键关联
     * 
     * @param 
     * @return 入群申请集合
     */
	@Override 
	public List<ClubJoinApplication> selectClubJoinApplicationAllAssoc()
	{
		return clubJoinApplicationMapper.selectClubJoinApplicationAllAssoc();
	}		
	
	/**
     * 查询入群申请列表
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 入群申请集合
     */
	@Override
	public List<ClubJoinApplication> selectClubJoinApplicationList(ClubJoinApplication clubJoinApplication)
	{
	    return clubJoinApplicationMapper.selectClubJoinApplicationList(clubJoinApplication);
	}
	
	/**
     * 查询入群申请列表 外键关联
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 入群申请集合
     */
	@Override
	public List<ClubJoinApplication> selectClubJoinApplicationListAssoc(ClubJoinApplication clubJoinApplication)
	{
	    return clubJoinApplicationMapper.selectClubJoinApplicationListAssoc(clubJoinApplication);
	}	
	
    /**
     * 新增入群申请
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 结果
     */
	@Override
	public int insertClubJoinApplication(ClubJoinApplication clubJoinApplication)
	{
	    return clubJoinApplicationMapper.insertClubJoinApplication(clubJoinApplication);
	}
	
	/**
     * 修改入群申请
     * 
     * @param clubJoinApplication 入群申请信息
     * @return 结果
     */
	@Override
	public int updateClubJoinApplication(ClubJoinApplication clubJoinApplication)
	{
	    return clubJoinApplicationMapper.updateClubJoinApplication(clubJoinApplication);
	}

	/**
     * 删除入群申请对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClubJoinApplicationByIds(String ids)
	{
		return clubJoinApplicationMapper.deleteClubJoinApplicationByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除入群申请对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClubJoinApplicationById(Long id)
	{
		return clubJoinApplicationMapper.deleteClubJoinApplicationById(id);
	}
	
	



}
