package com.counseling.cms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminScheduleEntity {

	private int applyNumber;
	private String studentNumber;
	private String counselingApplyDate;
	private String counselingReservationDate;
	private String counselingAllotmentYn;
	private String counselorEmployeeNumber;
	private String counselingProgressYn;
	private int fileNumber;
	private String counselingType;
	private String counselingClassifyCode;
	
	private String counselingLargeClassifyCode;
	private String counselingClassifyName;
	private String counselingExplain;
	private int expressionSequence;
	
	private int counselingResultNumber;
	private String counselingDate;
	private String counselingContent;
	
	//상담결과 테이블의 상담분류와 상담분류 테이블의 상담명의 차이?
	//상담코드=상담 일련번호 같은거인데 코드형식 뭔지 모르겠음
	//상담방식은 대면,비대면 같은거고 상담분류?상담명?은 또래상담 이런거?
	
	public AdminScheduleEntity() {
		
	}
}
