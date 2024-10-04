package io.goose.shooting.service.impl;

import java.util.List;

import io.goose.shooting.service.IClubJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ClubJudgeMapper;
import io.goose.shooting.domain.ClubJudge;
import io.goose.common.support.Convert;

/**
 * 俱乐部裁判 服务层实现
 * 
 * @author goose
 * @date 2021-01-15
 */
@Service
public class ClubJudgeServiceImpl implements IClubJudgeService
{
	@Autowired
	private ClubJudgeMapper clubJudgeMapper;


	/**
     * 查询俱乐部裁判信息
     * 
     * @param id 俱乐部裁判ID
     * @return 俱乐部裁判信息
     */
    @Override
	public ClubJudge selectClubJudgeById(Long id)
	{
	    return clubJudgeMapper.selectClubJudgeById(id);
	}
	
	/**
     * 查询俱乐部裁判信息 外键关联
     * 
     * @param id 俱乐部裁判ID
     * @return 俱乐部裁判信息
     */
    @Override
	public ClubJudge selectClubJudgeByIdAssoc(Long id)
	{
	    return clubJudgeMapper.selectClubJudgeByIdAssoc(id);
	}	
	
	/**
     * 查询所有俱乐部裁判列表
     * 
     * @param 
     * @return 俱乐部裁判集合
     */
	@Override 
	public List<ClubJudge> selectClubJudgeAll()
	{
		return clubJudgeMapper.selectClubJudgeAll();
	}	
	
	/**
     * 查询所有俱乐部裁判列表 外键关联
     * 
     * @param 
     * @return 俱乐部裁判集合
     */
	@Override 
	public List<ClubJudge> selectClubJudgeAllAssoc()
	{
		return clubJudgeMapper.selectClubJudgeAllAssoc();
	}		
	
	/**
     * 查询俱乐部裁判列表
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 俱乐部裁判集合
     */
	@Override
	public List<ClubJudge> selectClubJudgeList(ClubJudge clubJudge)
	{
	    return clubJudgeMapper.selectClubJudgeList(clubJudge);
	}
	
	/**
     * 查询俱乐部裁判列表 外键关联
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 俱乐部裁判集合
     */
	@Override
	public List<ClubJudge> selectClubJudgeListAssoc(ClubJudge clubJudge)
	{
	    return clubJudgeMapper.selectClubJudgeListAssoc(clubJudge);
	}	
	
    /**
     * 新增俱乐部裁判
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 结果
     */
	@Override
	public int insertClubJudge(ClubJudge clubJudge)
	{
	    return clubJudgeMapper.insertClubJudge(clubJudge);
	}
	
	/**
     * 修改俱乐部裁判
     * 
     * @param clubJudge 俱乐部裁判信息
     * @return 结果
     */
	@Override
	public int updateClubJudge(ClubJudge clubJudge)
	{
	    return clubJudgeMapper.updateClubJudge(clubJudge);
	}

	/**
     * 删除俱乐部裁判对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClubJudgeByIds(String ids)
	{
		return clubJudgeMapper.deleteClubJudgeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除俱乐部裁判对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClubJudgeById(Long id)
	{
		return clubJudgeMapper.deleteClubJudgeById(id);
	}
	
	



}
