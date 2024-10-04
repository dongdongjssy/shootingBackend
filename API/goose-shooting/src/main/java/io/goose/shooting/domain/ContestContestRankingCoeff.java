package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 赛事具体成绩表 shooting_contest_contest_ranking_coeff
 * 
 * @author goose
 * @date 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContestContestRankingCoeff extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 用户 */
	@Excel(name = "用户")
	private Long clientUserId;
	/** 成绩ID */
	@Excel(name = "成绩ID")
	private Long markId;
	/** 系数 */
	@Excel(name = "系数")
	private Double coefficient;

	@ApiModelProperty(hidden=true)
	private ClientUser  clientUser;

	public void setClientUser(ClientUser clientUser) 
	{
		this.clientUser = clientUser;
	}

	public ClientUser getClientUser() 
	{
		return clientUser;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("markId", getMarkId())
			.append("coefficient", getCoefficient())
			.toString();
    }
}
