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
	private String applyStatus="N";
	private String counselorNumber;
	private String status="B";
	private int fileNumber;
	private String applyMethod;
	private String applyContent;
	private String applyCategory;
		
	public ApplyEntity(ApplyDto applyDto, int fileNumber){
		this.setStudentNumber(GetUserInfoUtility.getUserId());
		this.setApplyDate(applyDto.getApplyDate());
		this.setFileNumber(fileNumber);
		this.setApplyMethod(applyDto.getApplyMethod());
		this.setApplyContent(applyDto.getApplyContent());
		this.setApplyCategory(applyDto.getApplyCategory());
	}
	

}
