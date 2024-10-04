package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 访客表 shooting_visitor
 * 
 * @author goose
 * @date 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Visitor extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 邮箱 */
	private String email;
	/** 验证码 */
	private String captcha;
	/** 状态 */
	private Integer status;
	/** 登陆时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime;
	private  String time;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("email", getEmail())
			.append("captcha", getCaptcha())
			.append("status", getStatus())
			.append("loginTime", getLoginTime())
				.append("time", getTime())
			.toString();
    }
}
