package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.LoginDto;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.jwt.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/authenticate")
public class LoginController {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

	@PostMapping("/admin/loginok")
	@ResponseBody
	public String adminLoginController(@RequestBody LoginDto loginDto, HttpServletResponse res) throws Exception {
		
		//사용자가 입력한 아이디 및 패스워드
		String userId=loginDto.getUserId();
		String userPassword=loginDto.getUserPassword();
		
		//사용자 이름과 비밀번호로 인증을 수행하고 JWT 토큰을 생성
		 try {
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(userId, userPassword));
	        } catch (Exception e) {
	            throw new Exception("Invalid username/password");
	        }
	        return jwtUtil.generateToken(userId);

	}
	
}
