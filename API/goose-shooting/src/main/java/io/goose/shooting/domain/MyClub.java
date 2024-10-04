package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 我关注的俱乐部表 shooting_my_club
 * 
 * @author goose
 * @date 2020-05-09
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MyClub extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 关注者 */
	private Long clientUserId;
	/** 关注的俱乐部 */
	private Long clubId;

	@ApiModelProperty(hidden=true)
	private ClientUser  clientUser;
	@ApiModelProperty(hidden=true)
	private Club  club;

	public void setClientUser(ClientUser clientUser) 
	{
		this.clientUser = clientUser;
	}

	public ClientUser getClientUser() 
	{
		return clientUser;
	}    
	public void setClub(Club club) 
	{
		this.club = club;
	}

	public Club getClub() 
	{
		return club;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("clubId", getClubId())
			.toString();
    }
}
