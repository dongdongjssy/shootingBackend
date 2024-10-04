package io.goose.shooting.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Stats {
	
	private Long clientUserId;

	private String nickName;
	
	private String englishName;
	
	private String realName;
	
	private Integer rank;
	
	private boolean isItMe;
	
	
	private Double point;
	
	private Integer cc;

	private Double score;
	
	private List<ContestStatsNew> contestStatsList;
	
	
	
}
