package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 评论表 shooting_comment
 * 
 * @author goose
 * @date 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Comment extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 外键 */
	private Long fkId;
	/** 外键表 */
	private Integer fkTable;
	/** 评论者 */
	private Long clientUserId;
	/** 评论内容 */
	private String content;
	/** 排名 */
	private Integer rankings;
	/** 状态 */
	private Integer status;

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
			.append("fkId", getFkId())
			.append("fkTable", getFkTable())
			.append("clientUserId", getClientUserId())
			.append("content", getContent())
			.append("rankings", getRankings())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
