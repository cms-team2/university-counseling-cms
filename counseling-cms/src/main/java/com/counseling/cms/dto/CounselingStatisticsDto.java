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
    
    // 일자별 통계를 위한 필드
    private String applyDay;        // 상담 신청 일자
    private int psychology;         // 심리상담 횟수
    private int academic;           // 학업상담 횟수
    private int etc;                // 기타상담 횟수
}
