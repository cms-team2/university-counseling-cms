package com.counseling.cms.dto;

import lombok.Data;

@Data
public class AddApplyDto {
	String studentNo;
	String consultationDate;
	String counselorNo;
	String consultationWay;
	String consultationCategory;
	String applyContent;
}
