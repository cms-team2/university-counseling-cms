package com.counseling.cms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselingStatisticsDto {

	private String counselCategory;
	private String gender;
	private int counselCount;
	private int year;
}
