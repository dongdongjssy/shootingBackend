package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 成绩说明表 shooting_rank_instructions
 * 
 * @author goose
 * @date 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RankInstructions extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 我的成绩 */
	private String myRank;
	/** 赛事成绩 */
	private String matchRank;
	/** 年度积分 */
	private String annualIntegral;
	/** 状态 */
	private Integer status;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("myRank", getMyRank())
			.append("matchRank", getMatchRank())
			.append("annualIntegral", getAnnualIntegral())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
