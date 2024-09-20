package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.UserMyActivityEntity;
import com.counseling.cms.entity.UserMypageEntity;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.mapper.UserMypageMapper;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.HttpServletRequest;

@Service("UserMypageService")
public class UserMypageService {

	@Autowired
	private JwtUtil Ju;
	
	@Autowired
	private UserMypageMapper Umm;
	
	private String getUserId(HttpServletRequest req) {
		String accessToken=CookieUtility.getCookie(req,"accessToken");
    	String UserId = Ju.extractUserId(accessToken);
		return UserId;
	}
	
	public UserMypageEntity dto(HttpServletRequest req) {
    	UserMypageEntity dto = Umm.dto(getUserId(req));
    	System.out.println(dto);
		return dto;
	}
	
	public List<UserMyActivityEntity> getMyActivity(HttpServletRequest req) {
		List<UserMyActivityEntity> dto = Umm.getMyActivity(getUserId(req));
		return dto;
	}
	
	
}
