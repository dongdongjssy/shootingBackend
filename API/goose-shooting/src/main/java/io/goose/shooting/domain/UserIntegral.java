package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 用户电子积分卡表 shooting_user_integral
 * 
 * @author goose
 * @date 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserIntegral extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 姓名 */
	@Excel(name = "姓名")
	private String name;
	/** 射手卡编号 */
	@Excel(name = "射手卡编号")
	private String member;
	/** 年龄组 */
	@Excel(name = "年龄组")
	private String ageGroup;
	/** 枪组 */
	@Excel(name = "枪组")
	private String gunGroup;
	/** 总积分 */
	@Excel(name = "总积分")
	private String totalScore;
	/** 团队编号 */
	@Excel(name = "团队编号")
	private String teamNo;
	/** 状态 */
	private Integer status;


	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("name", getName())
			.append("member", getMember())
			.append("ageGroup", getAgeGroup())
			.append("gunGroup", getGunGroup())
			.append("totalScore", getTotalScore())
			.append("teamNo", getTeamNo())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
