package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 用户收藏表 shooting_user_collection
 * 
 * @author goose
 * @date 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserCollection extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 收藏者 */
	private Long clientUserId;
	/** 收藏的对象主键 */
	private Long collectionId;
	/** 收藏对象类型 */
	private String collectionType;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("collectionId", getCollectionId())
			.append("collectionType", getCollectionType())
			.toString();
    }
}
