package com.counseling.cms.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.counseling.cms.mapper.LoginMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	 @Autowired
	    private LoginMapper loginMapper;

	    //사용자 이름으로 사용자 세부 정보를 로드
	    @Override
	    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
	        User user = loginMapper.findByUserId(userId);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found");			//사용자가 없는 경우
	        }
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	    }
}

