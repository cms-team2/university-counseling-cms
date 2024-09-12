package com.counseling.cms.dto;

import lombok.Data;

@Data
public class EmailConfirmDto {
	
	String userName;
	
	String userEmail;
	
	String verificationCode;
	
}
