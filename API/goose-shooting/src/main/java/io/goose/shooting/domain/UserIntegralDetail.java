package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 用户电子积分卡明细表 shooting_user_integral_detail
 * 
 * @author goose
 * @date 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserIntegralDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 姓名 */
	private String name;
	/** 射手卡编号 */
	private String member;
	/** 赛事 */
	private String contest;
	/** 名称 */
	private String ranking;
	/** 积分 */
	private String score;
	/** 状态 */
	private Integer status;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("name", getName())
			.append("member", getMember())
			.append("contest", getContest())
			.append("ranking", getRanking())
			.append("score", getScore())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
