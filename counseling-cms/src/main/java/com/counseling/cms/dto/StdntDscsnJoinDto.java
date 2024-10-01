package com.counseling.cms.dto;

import lombok.Data;

@Data
public class StdntDscsnJoinDto {
	private String flNm;              // 학생 이름
    private String gndr;              // 성별
    private String stdntNo;           // 학생 번호
    private String stdntEml;          // 학생 이메일
    private String stdntTelno;        // 학생 전화번호
    private int aplyNo;               // 신청 번호
    private String cTypeNm;           // 상담 유형 명
    private String cAplyDt;           // 상담 신청 일자
    private String dscsnYn;           // 상담 여부
    private String deptNm;            // 학과 명
    

}
