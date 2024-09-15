package com.counseling.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.mapper.CounseleeListMapper;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CounseleeListService {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CounseleeListMapper counseleeListMapper;
	
	public String getCounselorId(HttpServletRequest req) {
		String accessToken = CookieUtility.getCookie(req, "accessToken");
		String counselorId=jwtUtil.extractUserId(accessToken);
		String authority=jwtUtil.extractAuthority(accessToken);
		return counselorId;
	}
	
	public Map<String, Object> getCounseleeList(HttpServletRequest req){
		
		String counselorId=this.getCounselorId(req);
		Map<String, Object> result=new HashMap<>();
		try {
			List<CounseleeListEntity> counseleeList=counseleeListMapper.getCounseleeList(counselorId);
			result.put("counseleeList", counseleeList);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}

}
