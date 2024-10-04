package io.goose.shooting.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContestStatsNew {

	private static final long serialVersionUID = 1L;
	
	/** 年份 */
	private Integer year;
	/** 排名 */
	private Integer rank;
	/** 积分  */
	private Double point;
	/** 总分 */
	private Double totalScore;
	/** 总数 */
	private Integer totalCount;
	/** 总平均分 */
	private Double totalAvgScore;
	/** 最高分数 */
	private Double bestScore;
	/** 最大总数 */
	private Integer bestCount;
	/** 最高平均分 */
	private Double bestAvgScore;
	/** 备注 */
	private String note;
	
	
}
