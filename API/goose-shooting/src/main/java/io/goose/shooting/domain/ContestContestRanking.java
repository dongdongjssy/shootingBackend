package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 成绩表 shooting_contest_contest_ranking
 * 
 * @author goose
 * @date 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContestContestRanking extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	private String startDate;
	
	private Long areaId; 
	
	private String importName;
	/** 主键 */
	private Long id;
	/** 用户 */
	private Long clientUserId;
	@Excel(name = "用户")
	private String clientUserName;
	/** 赛事ID */
	@Excel(name = "赛事ID")
	private Long contestId;
	/** 组别ID */
	@Excel(name = "组别ID")
	private Long groupId;
	/** CPSA名次 */
	@Excel(name = "CPSA名次")
	private Integer cpsaRank;
	/** 总名次 */
	@Excel(name = "总名次")
	private Integer totalRank;
	/** 成绩 */
	@Excel(name = "成绩")
	private Double score;
	/** 百分比 */
	@Excel(name = "百分比")
	private Double percentage;
	/** 平均系数 */
	@Excel(name = "平均系数")
	private Double avgCoeff;
	/** 平均时间 */
	@Excel(name = "平均时间")
	private Double avgTime;
	/** 平均得分 */
	@Excel(name = "平均得分")
	private Double avgScore;
	/** 年龄组别 */
	@Excel(name = "年龄组别")
	private Integer ageGroup;
	/** 是否DQ */
	@Excel(name = "是否DQ")
	private Integer isDq;
	/** 备注 */
	@Excel(name = "备注")
	private String note;
	/** 状态 */
	@Excel(name = "状态")
	private Integer status;
	
	//积分
	private Double point;
	
	private Long typeId;
	
	private Long courseId;
	
	private String groupName;
	
	private String color;
	
	//查询赛事排名的时候 null为有组别   其他为无组别
	private Integer isGroupNull;  
	
	private String[] item;

	@ApiModelProperty(hidden=true)
	private ClientUser  clientUser;
	@ApiModelProperty(hidden=true)
	private Contest  contest;
	@ApiModelProperty(hidden=true)
	private ContestGroup  contestGroup;
	
	private List<ContestContestRankingCoeff> contestRankingCoeff;

	
	public void setClientUser(ClientUser clientUser) 
	{
		this.clientUser = clientUser;
	}

	public ClientUser getClientUser() 
	{
		return clientUser;
	}    
	public void setContest(Contest contest) 
	{
		this.contest = contest;
	}

	public Contest getContest() 
	{
		return contest;
	}    
	public void setContestGroup(ContestGroup contestGroup) 
	{
		this.contestGroup = contestGroup;
	}

	public ContestGroup getContestGroup() 
	{
		return contestGroup;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("contestId", getContestId())
			.append("groupId", getGroupId())
			.append("cpsaRank", getCpsaRank())
			.append("totalRank", getTotalRank())
			.append("score", getScore())
			.append("percentage", getPercentage())
			.append("avgCoeff", getAvgCoeff())
			.append("avgTime", getAvgTime())
			.append("avgScore", getAvgScore())
			.append("ageGroup", getAgeGroup())
			.append("isDq", getIsDq())
			.append("note", getNote())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
