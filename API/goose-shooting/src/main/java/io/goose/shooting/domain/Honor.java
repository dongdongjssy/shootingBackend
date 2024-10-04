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
 * 荣誉榜管理表 shooting_honor
 * 
 * @author goose
 * @date 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Honor extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 用户id */
	private Long clientUserId;
	/** 标题 */
	private String title;
	/** 会员号 */
	private String memberId;
	/** 图片 */
	private String pictureUrl;
	/** 排序 */
	private Integer sort;
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
			.append("clientUserId", getClientUserId())
			.append("title", getTitle())
				.append("memberId", getMemberId())
			.append("pictureUrl", getPictureUrl())
			.append("sort", getSort())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
