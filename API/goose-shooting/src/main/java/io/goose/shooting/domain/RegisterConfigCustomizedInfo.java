package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 报名配置-自定义项目表 shooting_register_config_customized_info
 * 
 * @author goose
 * @date 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterConfigCustomizedInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 关联配置的主键 */
	private Long registerConfigId;
	/** 项目名称 */
	private String itemName;
	/** 项目类型 */
	private String itemType;
	/** 是否必须 */
	private Integer isRequired;
	/** 是否显示备注 */
	private Integer isRemarkDisplay;
	/** 项目备注 */
	private String itemRemark;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("registerConfigId", getRegisterConfigId())
			.append("itemName", getItemName())
			.append("itemType", getItemType())
			.append("isRequired", getIsRequired())
			.append("isRemarkDisplay", getIsRemarkDisplay())
			.append("itemRemark", getItemRemark())
			.toString();
    }
}
