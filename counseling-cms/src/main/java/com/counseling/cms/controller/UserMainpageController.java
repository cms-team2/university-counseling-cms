package com.counseling.cms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.service.UserMainpageService;
import com.counseling.cms.utility.GetUserInfoUtility;

@Controller
public class UserMainpageController {

	@Autowired
	private UserMainpageService mainpageService;
	
	//유저 권한
	@GetMapping("/user/main-menu-auth")
	public ResponseEntity<Map<String, String>> getUserAuth(){
		Map<String, String> result = new HashMap<String, String>();
		String userAuth = GetUserInfoUtility.getUserAuthority();
		result.put("userAuth", userAuth);
		return ResponseEntity.ok(result);
	}
	
	//권한에 따른 메뉴
	@PostMapping("/user/main-menu")
	public ResponseEntity<Map<String, Object>> getMenu(@RequestParam("code") String code){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("majorMenu", mainpageService.getMajorMenuCategory(code));
		result.put("subMenu", mainpageService.getSubMenuCategory());
		return ResponseEntity.ok(result);
	}
	
}
