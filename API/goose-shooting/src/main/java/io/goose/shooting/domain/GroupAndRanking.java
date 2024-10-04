package io.goose.shooting.domain;

import java.util.List;

import lombok.Data;

@Data
public class GroupAndRanking {

	
	private String cateName;
	
	private Long contestGroupId;
	
	private List<ContestContestRanking> items;
}
