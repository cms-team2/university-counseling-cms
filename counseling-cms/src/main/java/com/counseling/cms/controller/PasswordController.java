package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.EmailConfirmDto;
import com.counseling.cms.service.PasswordService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PasswordController {
	
	@Autowired
	private PasswordService passwordService;
	
	@PostMapping("/pw/email-confirm")
	public ResponseEntity<String> emailConfirm(@RequestBody EmailConfirmDto emailConfirmDto) {
		return passwordService.findUser(emailConfirmDto);
	}
	
	@PostMapping("/pw/verification-confirm")
	public  ResponseEntity<String> verificationConfirm(@RequestBody EmailConfirmDto emailConfirmDto){

		return passwordService.verificationConfirm(emailConfirmDto);
	}
	
	@PostMapping("/pw/update")
	public ResponseEntity<String> passwordUpdate(@RequestBody String changePassword){

		return passwordService.passwordChange(changePassword);
	}

}
