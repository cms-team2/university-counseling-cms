package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.counseling.cms.utility.GetUserInfoUtility;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class KakaoApiController {

	@Autowired
	private GetUserInfoUtility userInfoUtility;
	
	@PostMapping("/user/isLogin")
	public ResponseEntity<Map<String, Object>> loginId(HttpServletRequest req){
		System.out.println(userInfoUtility.getUserId());
		
		Map<String, Object> a = null;
		
		return ResponseEntity.ok(a);
	}
}
