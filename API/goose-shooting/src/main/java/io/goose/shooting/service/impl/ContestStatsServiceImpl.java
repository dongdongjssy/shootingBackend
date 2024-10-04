package io.goose.shooting.service.impl;

import java.math.BigDecimal;
import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.AgeGroup;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.ContestContestRanking;
import io.goose.shooting.domain.ContestStats;
import io.goose.shooting.domain.ContestStatsNew;
import io.goose.shooting.domain.MyMark;
import io.goose.shooting.domain.Stats;
import io.goose.shooting.mapper.ClientUserMapper;
import io.goose.shooting.mapper.ContestContestRankingMapper;
import io.goose.shooting.mapper.ContestStatsMapper;
import io.goose.shooting.service.IContestStatsService;

/**
 * 选手排名 服务层实现
 * 
 * @author goose
 * @date 2020-05-28
 */
@Service
public class ContestStatsServiceImpl implements IContestStatsService 
{
	@Autowired
	private ContestStatsMapper contestStatsMapper;
	
	@Autowired
	private ClientUserMapper clientUserMapper;
	
	@Autowired
	private ContestContestRankingMapper rankingMapper;


	/**
     * 查询选手排名信息
     * 
     * @param id 选手排名ID
     * @return 选手排名信息
     */
    @Override
	public ContestStats selectContestStatsById(Long id)
	{
	    return contestStatsMapper.selectContestStatsById(id);
	}
	
	/**
     * 查询选手排名信息 外键关联
     * 
     * @param id 选手排名ID
     * @return 选手排名信息
     */
    @Override
	public ContestStats selectContestStatsByIdAssoc(Long id)
	{
	    return contestStatsMapper.selectContestStatsByIdAssoc(id);
	}	
	
	/**
     * 查询所有选手排名列表
     * 
     * @param 
     * @return 选手排名集合
     */
	@Override 
	public List<ContestStats> selectContestStatsAll()
	{
		return contestStatsMapper.selectContestStatsAll();
	}	
	
	/**
     * 查询所有选手排名列表 外键关联
     * 
     * @param 
     * @return 选手排名集合
     */
	@Override 
	public List<ContestStats> selectContestStatsAllAssoc()
	{
		return contestStatsMapper.selectContestStatsAllAssoc();
	}		
	
	/**
     * 查询选手排名列表
     * 
     * @param contestStats 选手排名信息
     * @return 选手排名集合
     */
	@Override
	public List<ContestStats> selectContestStatsList(ContestStats contestStats)
	{
	    return contestStatsMapper.selectContestStatsList(contestStats);
	}
	
	/**
     * 查询选手排名列表 外键关联
     * 
     * @param contestStats 选手排名信息
     * @return 选手排名集合
     */
	@Override
	public List<ContestStats> selectContestStatsListAssoc(ContestStats contestStats)
	{
	    return contestStatsMapper.selectContestStatsListAssoc(contestStats);
	}	
	
    /**
     * 新增选手排名
     * 
     * @param contestStats 选手排名信息
     * @return 结果
     */
	@Override
	public int insertContestStats(ContestStats contestStats)
	{
	    return contestStatsMapper.insertContestStats(contestStats);
	}
	
	/**
     * 修改选手排名
     * 
     * @param contestStats 选手排名信息
     * @return 结果
     */
	@Override
	public int updateContestStats(ContestStats contestStats)
	{
	    return contestStatsMapper.updateContestStats(contestStats);
	}

	/**
     * 删除选手排名对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestStatsByIds(String ids)
	{
		return contestStatsMapper.deleteContestStatsByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除选手排名对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteContestStatsById(Long id)
	{
		return contestStatsMapper.deleteContestStatsById(id);
	}

	@Override
	public List<Integer> selectYear(ContestStats contestStats) {
		return contestStatsMapper.selectYear(contestStats);
	}

	@Override
	public List<Stats> getStats(ContestStats contestStats) {
		
		//第一步 根据前台传的year 类别  组别   年龄组别      查 所有对应年份 且类型满足的人
		
		List<Stats> users = contestStatsMapper.selectAllUser(contestStats.getYear(),contestStats.getCourseId(),contestStats.getTypeId(),contestStats.getContestGroupId(),contestStats.getAgeGroup());

		//循环查每个人的排名数据
		for(Stats clientUser : users) {
			if(clientUser.getClientUserId().equals(contestStats.getSendUserId())) {
				clientUser.setItMe(true);
			}
			//查到自己的满足条件的赛事成绩数据
			List<ContestContestRanking> list = contestStatsMapper.selectContestRanking(clientUser.getClientUserId(),contestStats.getYear(),contestStats.getCourseId(),contestStats.getTypeId(),contestStats.getContestGroupId(),contestStats.getAgeGroup());
			
			//算分
			Double totalScore = new Double(0);
			Double totalAvgScore = new Double(0);
			Double score = new Double(0);
			List<ContestStatsNew> cslist = new ArrayList<>();
			ContestStatsNew cs = new ContestStatsNew();

			
			for(ContestContestRanking ran : list) {
				totalScore = totalScore + ran.getPoint();
				score=score+ran.getScore();
			}
			
	        list = list.stream().sorted(Comparator.comparing(ContestContestRanking::getPoint,Comparator.reverseOrder())).collect(Collectors.toList());
			
			 totalAvgScore  = totalScore/list.size();
			 BigDecimal b = new BigDecimal(totalAvgScore);
			clientUser.setPoint(totalScore);
			clientUser.setScore(score);
			cs.setTotalCount(list.size());
			clientUser.setCc(list.size());
			cs.setTotalScore(totalScore);
			cs.setTotalAvgScore(b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
			//如果大于2  就哟与最好3场的数据
			if(list.size() > 2) {
				cs.setBestScore(list.get(0).getPoint() + list.get(1).getPoint()+ list.get(2).getPoint());
				cs.setBestCount(3);
				
				b = new BigDecimal((list.get(0).getPoint() + list.get(1).getPoint()+ list.get(2).getPoint())/3);

				cs.setBestAvgScore(b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			
			cslist.add(cs);
			cslist.add(cs);
			clientUser.setContestStatsList(cslist);

		}
		if(contestStats.isSortByScore()){
			Collections.sort(users,new Comparator<Stats>() {
				Collator collator = Collator.getInstance(Locale.CHINA);
				@Override
				public int compare(Stats o1, Stats o2) {
					int flag;
					flag = (int) (o2.getScore()-o1.getScore());
					if(flag !=0){
						flag = (flag > 0) ? 3 : -1;
					}else {
						flag = (int)(o2.getPoint()-o1.getPoint());
						if(flag !=0) {
							flag = (flag > 0) ? 2 : -2;
						}else {
							flag = (int)(o1.getCc()-o2.getCc());
							if(flag !=0) {
								flag = (flag > 0) ? 1 : -3;
							}else {
								flag = collator.compare( o1.getRealName(),o2.getRealName());
								if (flag != 0) {
									flag = (flag > 0) ? 0 : -4;
								}
							}
						}
					}
					return flag;
				}
			});
		}else {
			Collections.sort(users,new Comparator<Stats>() {
				Collator collator = Collator.getInstance(Locale.CHINA);
				@Override
				public int compare(Stats o1, Stats o2) {
					int flag;
					flag = (int) (o2.getPoint()-o1.getPoint());
					if(flag !=0){
						flag = (flag > 0) ? 3 : -1;
					}else {
						flag = (int)(o2.getScore()-o1.getScore());
						if(flag !=0) {
							flag = (flag > 0) ? 2 : -2;
						}else {
							flag = (int)(o1.getCc()-o2.getCc());
							if(flag !=0) {
								flag = (flag > 0) ? 1 : -3;
							}else {
								flag = collator.compare( o1.getRealName(),o2.getRealName());
								if (flag != 0) {
									flag = (flag > 0) ? 0 : -4;
								}
							}
						}
					}
					return flag;
				}
			});
		}


//		users = users.stream().sorted(Comparator.comparing(Stats::getPoint,Comparator.reverseOrder()).thenComparing(Stats::getCc,Comparator.reverseOrder()).thenComparing(Stats::getRealName,new Comparator<Stats>())).collect(Collectors.toList());//先以属性一升序,再进行属性二降序<br><br><br>

//		users = users.stream().sorted(Comparator.comparing(Stats::getPoint,Comparator.reverseOrder())).collect(Collectors.toList());
		for(int i = 0; i<users.size(); i++) {
			users.get(i).setRank(i+1);
		}
		return users;
	}
	


	@Override
	public Map<String, Object> findMyMark(ContestStats contestStats) {
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		ClientUser clientUser =  clientUserMapper.selectClientUserById(contestStats.getClientUserId());
		if(clientUser == null) {
			map.put("clientUserName", null);
			map.put("englishName",null);
			map.put("memberId",null);
		}else {
			map.put("clientUserName", clientUser.getRealName());
			map.put("englishName", clientUser.getEnglishName());
			map.put("memberId",clientUser.getMemberId());
		}
		
		//第一步判断是否有传year 为null  就 最新年份
		if(contestStats.getYear() == null) {
			Calendar date = Calendar.getInstance();
		    String year = String.valueOf(date.get(Calendar.YEAR));
		    contestStats.setYear(Integer.valueOf(year.substring(2,4)));
			map.put("year", year);
		}else {
			map.put("year",contestStats.getYear());
			contestStats.setYear(Integer.valueOf(contestStats.getYear().toString().substring(2,4)));
		}
		
		 List<MyMark> list = contestStatsMapper.selectMyMark(contestStats);
		 Double totalScore = new Double(0);
		 for(MyMark myMark : list) {
		        totalScore = totalScore + myMark.getPoint();
		 }	
		 
		 map.put("totalScore",totalScore);
		 map.put("totalCount",list.size());
		 
		 if(list.size() > 0) {
			 Double avgscore  = totalScore/list.size();
			 BigDecimal b = new BigDecimal(avgscore);
			 map.put("avgScore",b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
		 }else {
			 map.put("avgScore",totalScore);
		 }
		 
		 map.put("total", list.size());
		 map.put("rows", list);
		return map;
	}

	@Override
	public List<AgeGroup> getAgeGroup() {
		 List<AgeGroup> list = contestStatsMapper.getAgeGroup();
		return list;
	}
	
	



}
