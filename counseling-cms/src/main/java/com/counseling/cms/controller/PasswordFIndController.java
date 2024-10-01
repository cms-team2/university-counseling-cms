package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.EmailConfirmDto;
import com.counseling.cms.service.PasswordFindService;

import jakarta.servlet.http.HttpSession;

@RestController
public class PasswordFIndController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private PasswordFindService passwordFindService;
	
	//사용자 정보 확인
	@PostMapping("/pw/email-confirm")
	public ResponseEntity<String> emailConfirm(@RequestBody EmailConfirmDto emailConfirmDto) {
		return passwordFindService.findUser(emailConfirmDto);
	}
	
	//인증 번호 전송
	@PostMapping("/pw/verification-confirm")
	public  ResponseEntity<String> verificationConfirm(@RequestBody EmailConfirmDto emailConfirmDto){

		return passwordFindService.verificationConfirm(emailConfirmDto);
	}
	
	//비밀번호 변경
	@PostMapping("/pw/update")
	public ResponseEntity<String> passwordUpdate(@RequestBody String changePassword){

		return passwordFindService.passwordChange(changePassword);
	}
	
	//인증번호 재전송
	@PostMapping("/pw/retransfer")
	public ResponseEntity<String> retransfer(@RequestBody EmailConfirmDto emailConfirmDto){
		session.removeAttribute("verificationCode");
		return passwordFindService.findUser(emailConfirmDto);
	}


}
