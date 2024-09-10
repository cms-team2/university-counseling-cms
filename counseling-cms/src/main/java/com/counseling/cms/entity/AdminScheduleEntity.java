package com.counseling.cms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminScheduleEntity {

	private int applyNumber;	//상담신청번호
	private String studentNumber;	//학번
	private String counselingApplyDate;	//상담신청날짜
	private String counselingReservationDate;	//상담희망날짜
	private String counselingAllotmentYn;	//상담배정여부
	private String counselorEmployeeNumber;	//상담사사번
	private String counselingProgressYn;	//상담진행여부
	private int fileNumber;	//상담자료파일번호
	private String counselingType;	//상담방식
	private String counselingClassifyCode;	//상담코드
	
	private String counselingLargeClassifyCode;	//상담대메뉴코드
	private String counselingClassifyName;	//상담명
	private String counselingExplain;	//상담소개
	private int expressionSequence;	//노출순서
	
	private int counselingResultNumber;	//상담결과번호
	private String counselingDate;	//상담한날짜
	private String counselingContent;	//상담내용
	private String counselingClassify;	//상담분류
	
	private String studentName;	//학생이름
	private String studentTelNumber;	//학생전화번호
	private String studentEmail;	//학생이메일
	
	public AdminScheduleEntity() {
		
	}
}
