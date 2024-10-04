package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 级别系数表 shooting_contest_level_coeff
 * 
 * @author goose
 * @date 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContestLevelCoeff extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 级别名称 */
	private String levelName;
	/** 级别系数 */
	private Double levelCoeff;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("levelName", getLevelName())
			.append("levelCoeff", getLevelCoeff())
			.toString();
    }
}
