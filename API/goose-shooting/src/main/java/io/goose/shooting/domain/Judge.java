package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 裁判表 shooting_judge
 * 
 * @author goose
 * @date 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Judge extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 昵称 */
	private String nickname;
	/** 姓名 */
	private String name;
	/** 联系 */
	private String contact;
	/** 裁判类别 */
	private Integer category;
	/** 国籍 */
	private Integer nationality;
	/** 排名 */
	private Integer rankings;
	/** 状态 */
	private Integer status;
	/** 头像 */
	private String avatar;
	/** 职责 */
	private String duty;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("nickname", getNickname())
			.append("name", getName())
			.append("contact", getContact())
			.append("category", getCategory())
			.append("nationality", getNationality())
			.append("rankings", getRankings())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("avatar", getAvatar())
			.append("duty", getDuty())
			.toString();
    }
}
