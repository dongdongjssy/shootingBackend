package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 付款码管理表 shooting_payment_code
 * 
 * @author goose
 * @date 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentCode extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 标题 */
	private String title;
	/** 二维码图片 */
	private String pictureUrl;
	/** 描述 */
	private String detail;
	/** 状态 */
	private Integer status;
	/** 状态 */
	private Integer type;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("title", getTitle())
			.append("pictureUrl", getPictureUrl())
			.append("detail", getDetail())
			.append("status", getStatus())
			.append("type", getType())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
