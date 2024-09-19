package com.counseling.cms.service;

import java.util.ArrayList;
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
	
	public Map<String, Object> getCounseleeList(HttpServletRequest req, int page, String searchValue, String category){
		
		List<CounseleeListEntity> counseleeList=new ArrayList<CounseleeListEntity>();
		
		int listCount=15;
		int totalList=0;
		int start= (page - 1) * listCount;
		
		String counselorId=this.getCounselorId(req);
		Map<String, Object> result=new HashMap<>();
		
		if(!searchValue.equals("")) {
			if(category.equals("A")) {		//검색어만 있는 경우
				System.out.println("Test3");
				totalList=counseleeListMapper.counseleeListSearchCount(counselorId, searchValue);
				counseleeList=counseleeListMapper.getCounseleeSearchList(counselorId, start, listCount, searchValue);		
			} else {		//검색어와 카테고리 모두 있는 경우
				System.out.println("Test4");
				totalList=counseleeListMapper.counseleeListCateSearchCount(counselorId, searchValue, category);
				counseleeList=counseleeListMapper.getCounseleeCateSearchList(counselorId, start, listCount,searchValue, category);	
			}
		} else  {
			if(category.equals("A")) {		//검색어 카테고리 모두 없는 경우
				totalList=counseleeListMapper.counseleeListCount(counselorId);
				counseleeList=counseleeListMapper.getCounseleeList(counselorId, start, listCount);	
				System.out.println("Test1");
			} else {
				System.out.println("Test2");  //카테고리만 있는 경우
				totalList=counseleeListMapper.counseleeListCateCount(counselorId,category);
				counseleeList=counseleeListMapper.getCounseleeCateList(counselorId, start, listCount, category);	
			}
		}
			
		int totalPage = (int)Math.ceil((double)totalList/listCount);	
		if(totalPage==0) {totalPage=1;}
			
		result.put("totalPage", totalPage);
		result.put("totalList", totalList);
		result.put("counseleeList", counseleeList);
			
		return result;
	}

}
