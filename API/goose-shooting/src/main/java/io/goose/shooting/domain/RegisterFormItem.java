package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 报名项目表 shooting_register_form_item
 * 
 * @author goose
 * @date 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterFormItem extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 关联shooting_register表的主键 */
	private Long registerId;
	/** 报名表项目 */
	private String name;
	/** 报名表项目类别(个人信息或自定义信息) */
	private String category;
	/** 项目类型 */
	private String type;
	/** 报名表项目值 */
	private String value;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("registerId", getRegisterId())
			.append("name", getName())
			.append("category", getCategory())
			.append("type", getType())
			.append("value", getValue())
			.append("remark", getRemark())
			.toString();
    }
}
