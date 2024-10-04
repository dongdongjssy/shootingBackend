package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 默认名次系数表 shooting_contest_ranking_coeff_default
 * 
 * @author goose
 * @date 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContestRankingCoeffDefault extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 年份  */
	private Long year;
	/** 科目 */
	private Long courseId;
	/** 射击类别 */
	private Long typeId;
	/** 组别 */
	private Long groupId;
	/** 默认系数 */
	private Double rankCoeff;

	@ApiModelProperty(hidden=true)
	private Course  course;
	@ApiModelProperty(hidden=true)
	private Type  type;
	@ApiModelProperty(hidden=true)
	private ContestGroup  contestGroup;

	public void setCourse(Course course) 
	{
		this.course = course;
	}

	public Course getCourse() 
	{
		return course;
	}    
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("year", getYear())
			.append("courseId", getCourseId())
			.append("typeId", getTypeId())
			.append("groupId", getGroupId())
			.append("rankCoeff", getRankCoeff())
			.toString();
    }
}
