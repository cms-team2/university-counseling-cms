package com.counseling.cms.entity;

import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.ApplyDto;
import com.counseling.cms.utility.GetUserInfoUtility;

import lombok.Data;

@Data
public class ApplyEntity {
	private int applyNumber;
	private String studentNumber;
	private String inDate;
	private String applyDate;
	private String applyStatus;
	private String counselorNumber;
	private String status="B";
	private MultipartFile applyFile[];
	private String applyMethod;
	private String applyContent;
	private String applyCategory;
	
	private ApplyEntity(ApplyDto applyDto){
		this.setStudentNumber(GetUserInfoUtility.getUserId());
		this.setApplyDate(applyDto.getApplyDate());
		
	}
	

}
