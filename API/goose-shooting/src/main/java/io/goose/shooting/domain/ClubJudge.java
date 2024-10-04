package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 俱乐部裁判表 shooting_club_judge
 * 
 * @author goose
 * @date 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ClubJudge extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 俱乐部 */
	private Long clubId;
	/** 裁判 */
	private Long judgeId;

	@ApiModelProperty(hidden=true)
	private Club  club;
	@ApiModelProperty(hidden=true)
	private Judge  judge;

	public void setClub(Club club) 
	{
		this.club = club;
	}

	public Club getClub() 
	{
		return club;
	}    
	public void setJudge(Judge judge) 
	{
		this.judge = judge;
	}

	public Judge getJudge() 
	{
		return judge;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clubId", getClubId())
			.append("judgeId", getJudgeId())
			.toString();
    }
}
