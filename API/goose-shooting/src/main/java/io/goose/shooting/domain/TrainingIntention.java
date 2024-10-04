package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 培训意向管理表 shooting_training_intention
 * 
 * @author goose
 * @date 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TrainingIntention extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 培训意向id */
	private Long id;
	/** 用户id */
	private Long clientUserId;
	/** 邮箱 */
	private String email;
	/** 手机号 */
	private String phone;
	/** 用户微信 */
	private String openId;
	/** 城市 */
	private String city;
	/** 身份证号 */
	private String idNumber;
	/** 真实姓名 */
	private String realName;
	/** 年龄 */
	private Integer age;
	/** 培训类型 */
	private Integer trainingType;
	/** 培训时间 */
	private Integer trainingTime;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
				.append("clientUserId", getClientUserId())
			.append("email", getEmail())
			.append("phone", getPhone())
			.append("openId", getOpenId())
			.append("city", getCity())
			.append("idNumber", getIdNumber())
			.append("realName", getRealName())
			.append("age", getAge())
			.append("trainingType", getTrainingType())
			.append("trainingTime", getTrainingTime())
			.append("remark", getRemark())
			.toString();
    }
}
