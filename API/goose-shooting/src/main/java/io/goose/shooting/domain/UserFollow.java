package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 用户关注表 shooting_user_follow
 * 
 * @author goose
 * @date 2020-05-22
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserFollow extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 关注者 */
	private Long clientUserId;
	/** 关注对象主键 */
	private Long followId;
	/** 关注对象类型 */
	private String followType;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("followId", getFollowId())
			.append("followType", getFollowType())
			.toString();
    }
}
