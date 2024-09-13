package com.counseling.cms.entity;

import lombok.Data;

@Data
public class UserInfoEntity {
	
	private int userNo;
	
	private String userId;
	
	private String userPassword;
	
	private int passwordFail;
	
	private String resentConnectionDate;
	
	private String accountLocking;
	
	private String userAuthority;
	
	public UserInfoEntity() {
		
	}
	
	
}
