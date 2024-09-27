package com.counseling.cms.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ApplyDto {
	private String applyCategory;
    private String applyMethod;
    private String applyDepartment;
    private String applyContact;
    private String applyDate;
    private String applyTime;
    private String applyEmail;
    private MultipartFile applyFile[]; 
    private String applyGender;
    private String applyContent;
}
