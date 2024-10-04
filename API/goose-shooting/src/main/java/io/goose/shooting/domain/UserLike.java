package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 用户点赞表 shooting_user_like
 * 
 * @author goose
 * @date 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserLike extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 点赞者 */
	private Long clientUserId;
	/** 点赞的对象主键 */
	private Long likeId;
	/** 点赞对象类型 */
	private String likeType;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("likeId", getLikeId())
			.append("likeType", getLikeType())
			.toString();
    }
}
