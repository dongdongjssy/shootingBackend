package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 俱乐部教官表 shooting_club_coach
 * 
 * @author goose
 * @date 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ClubCoach extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	
	private String ids;
	/** 俱乐部 */
	private Long clubId;
	@Excel(name = "俱乐部")
	private String clubName;
	/** 教官 */
	private Long coachId;
	@Excel(name = "教官")
	private String coachName;


	@ApiModelProperty(hidden=true)
	private Club  club;
	@ApiModelProperty(hidden=true)
	private Coach  coach;

	public void setClub(Club club) 
	{
		this.club = club;
	}

	public Club getClub() 
	{
		return club;
	}    
	public void setCoach(Coach coach) 
	{
		this.coach = coach;
	}

	public Coach getCoach() 
	{
		return coach;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clubId", getClubId())
			.append("coachId", getCoachId())
			.toString();
    }
}
