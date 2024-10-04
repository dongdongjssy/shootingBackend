package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 时间段表 shooting_time_range
 * 
 * @author goose
 * @date 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TimeRange extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 时间段名称 */
	private String name;
	/** 多少月以内 */
	private Integer monthCount;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("name", getName())
			.append("monthCount", getMonthCount())
			.toString();
    }
}
