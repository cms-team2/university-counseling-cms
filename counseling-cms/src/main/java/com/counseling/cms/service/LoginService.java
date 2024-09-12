package com.counseling.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.counseling.cms.dto.EmailConfirmDto;
import com.counseling.cms.dto.LoginDto;
import com.counseling.cms.entity.UserInfoEntity;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.mapper.LoginMapper;
import com.counseling.cms.mapper.TokenMapper;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private TokenMapper tokenMapper;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<String> loginService(LoginDto loginInfo, HttpServletResponse res){
		
		//사용자가 입력한 아이디 및 패스워드
		String userPassword=loginInfo.getUserPassword();
		String userId=loginInfo.getUserId();
		String dbAuthority=null;
		String dbPassword=null;

		
		try {
			UserInfoEntity userInfoEntity=loginMapper.findByUserId(userId);			
			//Database에 저장된 권한 및 패스워드
			dbAuthority=userInfoEntity.getUserAuthority();	
			dbPassword=userInfoEntity.getUserPassword();
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		
		if(dbPassword == null) {//사용자 정보 없음
			return  ResponseEntity.status(433).build();								
		} else if(!passwordEncoder.matches(userPassword, dbPassword)) {		//비밀번호 오류(비밀 번호 오류 횟수 추가해야 함)
			
			int failCount=0;
			failCount=loginMapper.getPasswordFail(userId);
			
			loginMapper.updatePasswordFail(++failCount, userId);

			if(failCount<6) {
				return ResponseEntity.status(434).build();													
			} else {
				return ResponseEntity.status(435).build();		//비밀번호 실패 횟수 초과시
			}
			
		} else {																						//로그인 성공(비밀 번호 오류 횟수 초기화 추가해야 함)
			loginMapper.updatePasswordFail(0, userId);
			String accessToken=jwtUtil.generateToken(userId, dbAuthority);
			String refreshToken="";
			if(loginInfo.getAutoLogin().equals("Y")) {
				refreshToken=jwtUtil.autoLoginGenerateRefreshToken(userId, dbAuthority);
			} else {
				refreshToken=jwtUtil.generateRefreshToken(userId, dbAuthority);				
			}
			
			tokenMapper.saveRefreshToken(userId, refreshToken); // DB에 refreshToken 저장
			
			//HttpOnly 쿠키에 accessToken 저장
			Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
		    accessTokenCookie.setHttpOnly(true);
		    accessTokenCookie.setPath("/");
		    accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); 
		    res.addCookie(accessTokenCookie);
		    
		    loginMapper.updateLastConnectDate(userId);				//최근 접속일 저장
		    
			return ResponseEntity.ok(accessToken); 
		}
		
	}
			
		//admin 로그아웃 service
		public String logoutService(HttpServletResponse res, HttpServletRequest req){
			
			String accessToken=CookieUtility.getCookie(req, "accessToken");
			String userId=jwtUtil.extractUserId(accessToken);
			
			tokenMapper.removeResfredhToken(userId);				
			
			CookieUtility.deleteCookie(res, "accessToken", "/");
			req.getSession().invalidate();
			
			res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0"); // 캐시 방지
		    res.setHeader("Pragma", "no-cache"); // HTTP 1.0
		    res.setDateHeader("Expires", 0);
			
			return "redirect:/admin/login";
		}
		
		//5분 후 비밀번호 실패 횟수 초기화
		public ResponseEntity<String> resetFailCount(LoginDto loginInfo){
			int result=loginMapper.updatePasswordFail(0,loginInfo.getUserId());
			if(result>0) {
				return ResponseEntity.ok("ok");		
			} else {
				return ResponseEntity.status(435).build();
			}			
		}
		
		
		//사용자 정보 비밀번호 암호화 후 저장
		public int insertUserInfo() {
			UserInfoEntity userInfo=new UserInfoEntity();
			userInfo.setUserId("2007003203");
			userInfo.setUserPassword(passwordEncoder.encode("2007003203"));
			userInfo.setUserAuthority("C");
			int result=loginMapper.insertUserInfo(userInfo);
			return result;
		}
}
