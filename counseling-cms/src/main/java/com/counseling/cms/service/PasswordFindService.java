package com.counseling.cms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.EmailConfirmDto;
import com.counseling.cms.mapper.PasswordFindMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class PasswordFindService {
	
	@Autowired
	private PasswordFindMapper passwordFindMapper;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<String> findUser(EmailConfirmDto emailConfirmDto){

		int result=passwordFindMapper.findUser(emailConfirmDto);

		if(result>0) {
			this.sendEmail(emailConfirmDto);
			return ResponseEntity.ok("ok");		
		} else {
			return  ResponseEntity.status(704).build();
		}
	}
	
	//메일 발송
    public void sendEmail(EmailConfirmDto emailConfirmDto) {
    	String verificationCode=generateCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("centraluniversity@nate.com"); // 발신자 이메일
        message.setTo(emailConfirmDto.getUserEmail()); // 수신자 이메일
        message.setSubject("[중앙공과대학교] 비밀번호 변경을 위한 인증번호 발송"); // 메일 제목
        message.setText("인증번호는 ["+verificationCode+"]입니다."); // 메일 내용
 
        javaMailSender.send(message);
        
        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("userId", emailConfirmDto.getUserId());
        
    }
	
	//인증번호 생성
	public String generateCode() {
		StringBuilder randomCode = new StringBuilder();
	        for (int i = 0; i < 6; i++) {
	            int number = (int) Math.floor(Math.random() * 10);
	            randomCode.append(number);
	        }
	        return randomCode.toString();
	}
	
	//인증 번호 확인
	public ResponseEntity<String> verificationConfirm(EmailConfirmDto emailConfirmDto){
		String savedCode=session.getAttribute("verificationCode").toString();
		String savedId=session.getAttribute("userId").toString();
	
		if(savedCode.equals(emailConfirmDto.getVerificationCode())&&savedId.equals(emailConfirmDto.getUserId())) {
			session.removeAttribute("verificationCode");
			return ResponseEntity.ok("ok");
		} else {
			return ResponseEntity.status(704).build();
		}
	}
	
	//비밀번호 변경
	public ResponseEntity<String> passwordChange(String changePassword){
		Map<String, String> userData=new HashMap<>();
		userData.put("changePassword", passwordEncoder.encode(changePassword));
		userData.put("userId", session.getAttribute("userId").toString());
		
		int result=passwordFindMapper.updatePassword(userData);

		if(result>0) {
			session.removeAttribute("userId");
			return ResponseEntity.ok("ok");
		}else {
			return ResponseEntity.status(704).build();
		}
	}
	
}
