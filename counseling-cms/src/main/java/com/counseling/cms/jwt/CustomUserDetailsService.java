package com.counseling.cms.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.UserInfoEntity;
import com.counseling.cms.mapper.LoginMapper;
import com.counseling.cms.mapper.TokenMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	 @Autowired
	    private LoginMapper loginMapper;
	 
	 @Autowired
	 private TokenMapper tokenMapper;
 
	 @Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserInfoEntity userInfoEntity=loginMapper.findByUserId(userId);
		
		if (userInfoEntity==null) {
			throw new UsernameNotFoundException("사용자 정보 찾을 수 없음");
		}
		
		
		return new CustomUserDetails(userInfoEntity);
	}
	
	 //DB에서 refreshToken select
	 public String getRefreshToken(String userId) {
		 String dbRefreshToken=tokenMapper.getResfredhToken(userId);
		 return dbRefreshToken;
	 }
	 
}

