package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.EmailConfirmDto;
import com.counseling.cms.dto.LoginDto;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    
    @Autowired
    private LoginService loginService;

	@PostMapping("/user/admin-loginok")
	@ResponseBody
	public ResponseEntity<String> adminLoginController(@RequestBody LoginDto loginInfo, HttpServletResponse res) throws Exception {

		return loginService.loginService(loginInfo, res);
	}
	
	@GetMapping("/admin/logoutok")
	public String adminLogout(HttpServletResponse res, HttpServletRequest req){ 
		
		return loginService.logoutService(res, req);
	}
	
	@PostMapping("/user/loginok")
	@ResponseBody
	public ResponseEntity<String> userLoginController(@RequestBody LoginDto loginInfo, HttpServletResponse res) throws Exception {

		return loginService.loginService(loginInfo, res);
	}
	
	@GetMapping("/user/logoutok")
	public String userLogout(HttpServletResponse res, HttpServletRequest req){ 
		
		return loginService.logoutService(res, req);
	}
	
	
	
	//로그인 실패 횟수 초기화
	@PostMapping("/pw/update-pw-fail")
	public ResponseEntity<String> resetPwFail(@RequestBody LoginDto loginDto){
		return loginService.resetFailCount(loginDto);
	}
	
	//비밀번호 암호화 후 USER_INFO 입력 메소드
	@GetMapping("/user/userinfo")
	public void userInfo() {
		System.out.println(loginService.insertUserInfo());
	}
	
	
}
