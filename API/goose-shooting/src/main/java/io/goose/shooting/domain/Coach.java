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
 * 教官表 shooting_coach
 * 
 * @author goose
 * @date 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Coach extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	
	private String ids;
	/** 昵称 */
	@Excel(name = "昵称")
	private String nickname;
	/** 姓名 */
	@Excel(name = "姓名")
	private String name;
	/** 联系 */
	private String contact;
	/** 教官类别 */
	private Integer category;
	@Excel(name = "教官类别")
	private String categoryName;
	/** 排名 */
	private Integer rankings;
	/** 状态 */
	private Integer status;

	@Excel(name = "状态")
	private String statusName;
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
