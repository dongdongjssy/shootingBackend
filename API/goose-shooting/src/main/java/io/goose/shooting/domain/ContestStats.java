package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;





/**
 * 选手排名表 shooting_contest_stats
 * 
 * @author goose
 * @date 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContestStats extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 用户 */
	private Long clientUserId;
	
	/** 请求接口的用户ID */
	private Long sendUserId;
	
	private String nickname;
	/** 射击类别 */
	private Long typeId;
	
	/** 科目*/
	private Long courseId;
	/** 组别ID */
	private Long contestGroupId;
	/** 年龄组别 */
	private Integer ageGroup;
	/** 年份 */
	private Integer year;
	/** 排名 */
	private Integer rank;
	/** 积分  */
	private Double point;
	/** 总分 */
	private Double totalScore;
	/** 总数 */
	private Integer totalCount;
	/** 总平均分 */
	private Double totalAvgScore;
	/** 最高分数 */
	private Double bestScore;
	/** 最大总数 */
	private Integer bestCount;
	/** 最高平均分 */
	private Double bestAvgScore;
	/** 备注 */
	private String note;
	/** 状态 */
	private Integer status;



	/** 是否按分数排序 */
	private boolean sortByScore;

	@ApiModelProperty(hidden=true)
	private Type  type;
	@ApiModelProperty(hidden=true)
	private ContestGroup  contestGroup;

	public void setType(Type type) 
	{
		this.type = type;
	}

	public Type getType() 
	{
		return type;
	}    
	public void setContestGroup(ContestGroup contestGroup) 
	{
		this.contestGroup = contestGroup;
	}

	public ContestGroup getContestGroup() 
	{
		return contestGroup;
	}
	public boolean isSortByScore() {
		return sortByScore;
	}

	public void setSortByScore(boolean sortByScore) {
		this.sortByScore = sortByScore;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("typeId", getTypeId())
			.append("contestGroupId", getContestGroupId())
			.append("ageGroup", getAgeGroup())
			.append("year", getYear())
			.append("rank", getRank())
			.append("point", getPoint())
			.append("totalScore", getTotalScore())
			.append("totalCount", getTotalCount())
			.append("totalAvgScore", getTotalAvgScore())
			.append("bestScore", getBestScore())
			.append("bestCount", getBestCount())
			.append("bestAvgScore", getBestAvgScore())
			.append("note", getNote())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
				.append("sortByScore", isSortByScore())
			.toString();
    }
}
