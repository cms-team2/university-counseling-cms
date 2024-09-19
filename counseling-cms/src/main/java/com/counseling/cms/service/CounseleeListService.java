package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.entity.CounselingRecordEntity;
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
	
	private String counselorName;
	
	public String getCounselorId(HttpServletRequest req) {
		String accessToken = CookieUtility.getCookie(req, "accessToken");
		String counselorId=jwtUtil.extractUserId(accessToken);		//token에서 id 추출
		String authority=jwtUtil.extractAuthority(accessToken);		//token에서 권한 추출
		this.counselorName=counseleeListMapper.getCounselorName(counselorId);
		return counselorId;
	}
	
	public Map<String, Object> getCounseleeList(HttpServletRequest req, int page, String searchValue, String category){
		
		List<CounseleeListEntity> counseleeList=new ArrayList<CounseleeListEntity>();		
		Map<String, Object> result=new HashMap<>();
		String counselorId=this.getCounselorId(req);
		
		int listCount=15;
		int totalList=0;
		int start= (page - 1) * listCount;
		
		if(!searchValue.equals("")) {
			if(category.equals("A")) {		//검색어만 있는 경우
				totalList=counseleeListMapper.counseleeListSearchCount(counselorId, searchValue);
				counseleeList=counseleeListMapper.getCounseleeSearchList(counselorId, start, listCount, searchValue);		
			} else {		//검색어와 카테고리 모두 있는 경우
				totalList=counseleeListMapper.counseleeListCateSearchCount(counselorId, searchValue, category);
				counseleeList=counseleeListMapper.getCounseleeCateSearchList(counselorId, start, listCount,searchValue, category);	
			}
		} else  {
			if(category.equals("A")) {		//검색어 카테고리 모두 없는 경우
				totalList=counseleeListMapper.counseleeListCount(counselorId);
				counseleeList=counseleeListMapper.getCounseleeList(counselorId, start, listCount);	
			} else {	 //카테고리만 있는 경우
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
	
	public CounseleeListEntity getApplyView(HttpServletRequest req, int applyNo) {
		String counselorId=this.getCounselorId(req);
		CounseleeListEntity counseleeListEntity=counseleeListMapper.getApplyView(counselorId, applyNo);
		return counseleeListEntity;
	}
	
	public Map<String, Object> getCounselingRecord(int applyNo) {
		Map<String, Object> result=new HashMap<>();
		
		int recordCount=counseleeListMapper.counselingRrcordCount(applyNo);
		String today=counseleeListMapper.getToday();
		if(recordCount>0) {
			CounselingRecordEntity counselingRecordEntity=counseleeListMapper.getcounselingRecord(applyNo);
			result.put("recordList", counselingRecordEntity);
		}
		result.put("today", today);
		result.put("recordCount", recordCount);
		result.put("counselorName", counselorName);
		
		return result;
	}
	
	public Map<String, Object> getCounselingRecordList(HttpServletRequest req, int page, String searchValue, String category){
		List<CounselingRecordEntity> recordList=new ArrayList<CounselingRecordEntity>();
		Map<String, Object> result=new HashMap<>();
		String counselorId=this.getCounselorId(req);
		
		int listCount=15;
		int totalList=0;
		int start= (page - 1) * listCount;
		System.out.println(category);
		
		if(!searchValue.equals("")) {
			if(category.equals("A")) {		//검색어만 있는 경우
				System.out.println("test1");
				totalList=counseleeListMapper.counselingRecordListSearchCount(counselorId, searchValue);
				recordList=counseleeListMapper.getCounselingRecordSearchList(counselorId, start, listCount, searchValue);		
			} else {		//검색어와 카테고리 모두 있는 경우
				totalList=counseleeListMapper.counselingRecordListCateSearchCount(counselorId, searchValue, category);
				recordList=counseleeListMapper.getCounselingRecordCateSearchList(counselorId, start, listCount,searchValue, category);	
			}
		} else  {
			if(category.equals("A")) {		//검색어 카테고리 모두 없는 경우
				System.out.println("test");
				totalList=counseleeListMapper.counselingRecordListCount(counselorId);
				recordList=counseleeListMapper.getCounselingRecordList(counselorId, start, listCount);	

			} else {  //카테고리만 있는 경우
				totalList=counseleeListMapper.counselingRecordListCateCount(counselorId,category);
				recordList=counseleeListMapper.getCounselingRecordCateList(counselorId, start, listCount, category);	
			}
		}
		
		System.out.println(totalList);
		System.out.println(recordList.size());
		
		int totalPage = (int)Math.ceil((double)totalList/listCount);	
		if(totalPage==0) {totalPage=1;}
		
		result.put("totalPage", totalPage);
		result.put("totalList", totalList);
		result.put("recordList", recordList);
		
		return result;
	}

}
