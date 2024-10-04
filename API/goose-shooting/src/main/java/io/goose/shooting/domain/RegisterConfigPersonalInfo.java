package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 报名配置-个人配置项表 shooting_register_config_personal_info
 * 
 * @author goose
 * @date 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterConfigPersonalInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 关联配置的主键 */
	private Long registerConfigId;
	/** 项目名称 */
	private String infoName;
	private String infoText;
	/** 是否必须 */
	private Integer isRequired;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("registerConfigId", getRegisterConfigId())
			.append("infoName", getInfoName())
			.append("isRequired", getIsRequired())
			.toString();
    }
}
