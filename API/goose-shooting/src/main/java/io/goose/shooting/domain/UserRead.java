package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 用户阅读表 shooting_user_read
 * 
 * @author goose
 * @date 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserRead extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 阅读者 */
	private Long clientUserId;
	/** 阅读对象主键 */
	private Long readId;
	/** 阅读对象类型 */
	private String readType;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("readId", getReadId())
			.append("readType", getReadType())
			.toString();
    }
}
