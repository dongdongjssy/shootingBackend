package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 通告表 shooting_message
 * 
 * @author goose
 * @date 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Message extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 通告标题 */
	private String title;
	/** 通告内容 */
	private String content;
	/** 通告类型 1 全部 2指定用户 */
	private Integer type;
	/** 状态 */
	private Integer status;
	
	private Integer isView;
	
	private Long clientUserId;
	
	private String[] userList;
	
	private Integer subType;
	
	private Long orderId;

	private Integer isImportant;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("isImportant", getIsImportant())
			.append("title", getTitle())
			.append("content", getContent())
			.append("type", getType())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
