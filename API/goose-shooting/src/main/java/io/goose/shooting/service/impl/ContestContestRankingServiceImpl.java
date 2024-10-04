package io.goose.shooting.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.ContestContestRankingMapper;
import io.goose.shooting.mapper.ContestMapper;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.domain.ContestContestRanking;
import io.goose.shooting.domain.GroupAndRanking;
import io.goose.shooting.domain.Stats;
import io.goose.shooting.service.IContestContestRankingService;
import io.goose.common.support.Convert;

/**
 * 成绩 服务层实现
 * 
 * @author goose
 * @date 2020-05-27
 */
@Service
public class ContestContestRankingServiceImpl implements IContestContestRankingService 
{
	@Autowired
	private ContestContestRankingMapper contestContestRankingMapper;
	
	@Autowired
	private ContestMapper contestMapper;


	/**
     * 查询成绩信息
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
    @Override
	public ContestContestRanking selectContestContestRankingById(Long id)
	{
	    return contestContestRankingMapper.selectContestContestRankingById(id);
	}
	
	/**
     * 查询成绩信息 外键关联
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
    @Override
	public ContestContestRanking selectContestContestRankingByIdAssoc(Long id)
	{
	    return contestContestRankingMapper.selectContestContestRankingByIdAssoc(id);
	}	
	
	/**
     * 查询所有成绩列表
     * 
     * @param 
     * @return 成绩集合
     */
	@Override 
	public List<ContestContestRanking> selectContestContestRankingAll()
	{
		return contestContestRankingMapper.selectContestContestRankingAll();
	}	
	
	/**
     * 查询所有成绩列表 外键关联
     * 
     * @param 
     * @return 成绩集合
     */
	@Override 
	public List<ContestContestRanking> selectContestContestRankingAllAssoc()
	{
		return contestContestRankingMapper.selectContestContestRankingAllAssoc();
	}		
	
	/**
     * 查询成绩列表
     * 
     * @param contestContestRanking 成绩信息
     * @return 成绩集合
     */
	@Override
	public List<ContestContestRanking> selectContestContestRankingList(ContestContestRanking contestContestRanking)
	{
	    return contestContestRankingMapper.selectContestContestRankingList(contestContestRanking);
	}
	
	/**
     * 查询成绩列表 外键关联
     * 
     * @param contestContestRanking 成绩信息
     * @return 成绩集合
     */
	@Override
	public List<ContestContestRanking> selectContestContestRankingListAssoc(ContestContestRanking contestContestRanking)
	{
	    return contestContestRankingMapper.selectContestContestRankingListAssoc(contestContestRanking);
	}	
	
    /**
     * 新增成绩
     * 
     * @param contestContestRanking 成绩信息
     * @return 结果
     */
	@Override
	public int insertContestContestRanking(ContestContestRanking contestContestRanking)
	{
	    return contestContestRankingMapper.insertContestContestRanking(contestContestRanking);
	}
	
	/**
     * 修改成绩
     * 
     * @param contestContestRanking 成绩信息
     * @return 结果
     */
	@Override
	public int updateContestContestRanking(ContestContestRanking contestContestRanking)
	{
	    return contestContestRankingMapper.updateContestContestRanking(contestContestRanking);
	}

	/**
     * 删除成绩对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestContestRankingByIds(String ids)
	{
		return contestContestRankingMapper.deleteContestContestRankingByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除成绩对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestContestRankingById(Long id)
	{
		return contestContestRankingMapper.deleteContestContestRankingById(id);
	}

	@Override
	public Map<String,Object> contestRanking(ContestContestRanking contestContestRanking) {
		Contest contest = new Contest();
		contest.setAreaId(contestContestRanking.getAreaId());
		contest.setStartTime(contestContestRanking.getStartDate() + "-01");
		contest.setTypeId(contestContestRanking.getTypeId());
		contest.setCourseId(contestContestRanking.getCourseId());
		List<Contest> contestList = contestMapper.selectContestListAssoc(contest);
		Map<String,Object> map = new HashMap<>();
		if(contestList.size() > 0) {
			map.put("contestName", contestList.get(0).getTitle());
			contestContestRanking.setContestId(contestList.get(0).getId());
			//查本赛事有多少组别
			List<GroupAndRanking> garList = contestContestRankingMapper.selectContestGroupByContestId(contestContestRanking.getContestId());
			garList.add(new GroupAndRanking());
			for(GroupAndRanking gar : garList) {
				
				if(gar.getContestGroupId() != null) {
					contestContestRanking.setGroupId(gar.getContestGroupId());
					List<ContestContestRanking> list = contestContestRankingMapper.selectContestContestRankingList(contestContestRanking);
					list = list.stream().sorted(Comparator.comparing(ContestContestRanking::getCpsaRank)).collect(Collectors.toList());//根据cpsa名字正序
					gar.setItems(list);
				}else {
					contestContestRanking.setGroupId(null);
					contestContestRanking.setIsGroupNull(1);
					List<ContestContestRanking> list = contestContestRankingMapper.selectContestContestRankingList(contestContestRanking);
					list = list.stream().sorted(Comparator.comparing(ContestContestRanking::getCpsaRank)).collect(Collectors.toList());//根据cpsa名字正序
					gar.setItems(list);
				}
			}
			map.put("list", garList);
			return map;
		}else {
			map.put("contestName", "无赛事名称");
			map.put("list", new ArrayList<GroupAndRanking>());
			return map;
		}
	}
	

	@Override
	public Map<String,Object> contestRankingByContestId(ContestContestRanking contestContestRanking) {
		Contest contest = new Contest();
		contest.setId(contestContestRanking.getContestId());
		List<Contest> contestList = contestMapper.selectContestListAssoc(contest);
		Map<String,Object> map = new HashMap<>();
		if(contestList.size() > 0) {
			map.put("contestName", contestList.get(0).getTitle());
			contestContestRanking.setContestId(contestList.get(0).getId());
			//查本赛事有多少组别
			List<GroupAndRanking> garList = contestContestRankingMapper.selectContestGroupByContestId(contestContestRanking.getContestId());
			garList.add(new GroupAndRanking());
			int index = 1;
			for(GroupAndRanking gar : garList) {
				if(gar.getContestGroupId() != null) {
					String color = "";
					if(index%5 == 1) {
						color = "#E5F5FF";
					}else if(index%5 == 2) {
						color = "#FFF5E8";
					}else if(index%5 == 3) {
						color = "#FFECF4";
					}else if(index%5 == 4) {
						color = "#E6FFF3";
					}else if(index%5 == 5) {
						color = "#FFECEE";
					}
					boolean sort = true; //true 走 CPSA 排序   false走 赛事名次 
					contestContestRanking.setGroupId(gar.getContestGroupId());
					List<ContestContestRanking> list = contestContestRankingMapper.selectContestContestRankingList(contestContestRanking);
					for(ContestContestRanking c : list) {
						c.setGroupName(gar.getCateName());
						c.setColor(color);
						if(gar.getCateName().equals("军警PLC") ) {
							sort = false;
						}
					}
					if(sort) {
						list = list.stream().sorted(Comparator.comparing(ContestContestRanking::getCpsaRank)).collect(Collectors.toList());//根据cpsa名字正序
					}else {
						list = list.stream().sorted(Comparator.comparing(ContestContestRanking::getTotalRank)).collect(Collectors.toList());//根据赛事总名次正序
					}
					gar.setItems(list);
				}else {
					contestContestRanking.setGroupId(null);
					contestContestRanking.setIsGroupNull(1);
					List<ContestContestRanking> list = contestContestRankingMapper.selectContestContestRankingList(contestContestRanking);
					list = list.stream().sorted(Comparator.comparing(ContestContestRanking::getCpsaRank)).collect(Collectors.toList());//根据cpsa名字正序
					gar.setItems(list);
				}
				index++;
			}
			map.put("list", garList);
			return map;
		}else {
			map.put("contestName", "无赛事名称");
			map.put("list", new ArrayList<GroupAndRanking>());
			return map;
		}
	}



}
