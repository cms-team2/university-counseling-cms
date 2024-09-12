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
	
    private String applyMonth;      // 월별 통계를 위한 필드
    private String counselName;     // 대메뉴 또는 소메뉴 상담명
    
    private String applyDate;  // 일별 날짜
    private int psychologyCount;  // 심리 상담 횟수
    private int academicCount;  // 학업 상담 횟수
    private int otherCount;  // 기타 상담 횟수
}
