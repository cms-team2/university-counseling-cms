package com.counseling.cms.entity;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("UserMypageEntity")
public class UserMypageEntity {

	private String userId;         // USER_ID
	private String eml;            // EML
	private String flnm;           // FLNM
	private String deptNm;         // DEPT_NM
	private String stdntTelno;     // STDNT_TELNO
	private String stdntZip;       // STDNT_ZIP
	private String stdntAddr;      // STDNT_ADDR
	private String stdntDaddr;     // STDNT_DADDR


	
}
