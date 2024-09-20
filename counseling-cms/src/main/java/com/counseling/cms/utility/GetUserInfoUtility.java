package com.counseling.cms.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.counseling.cms.jwt.CustomUserDetails;

public class GetUserInfoUtility {
	public static String getUserId() { // 유저 아이디 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		return userDetails.getUserId();
	}
	public static String getUserAuthority() { // 유저 권한 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		return userDetails.getUserAuthority();
	}
}
