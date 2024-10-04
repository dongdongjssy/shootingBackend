package io.goose.shooting.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MyMark {

	
	private Long id;
	private Integer cpsaRank;
	private Double score;
	private Date startDate;
	private Date endDate;
	private String title;
	private String areaName;
	private String groupName;
	private Double levelCoeff;
	private Double rankCoeff;
	private Double point;
	
}
