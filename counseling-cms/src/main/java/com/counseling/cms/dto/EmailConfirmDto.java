package com.counseling.cms.dto;

import lombok.Data;

@Data
public class EmailConfirmDto {
	
	String userId;
	
	String userEmail;
	
	String verificationCode;
	
}
