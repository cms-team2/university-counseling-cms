package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.CounselingRecordDto;
import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.entity.CounselingRecordEntity;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.mapper.CounseleeListMapper;
import com.counseling.cms.mapper.FileMapper;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CounseleeListService {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CounseleeListMapper counseleeListMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
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
	
	public Map<String, Object> getApplyView(HttpServletRequest req, int applyNo) {
		Map<String, Object> result=new HashMap<>();
		String counselorId=this.getCounselorId(req);
		
		CounseleeListEntity counseleeListEntity=counseleeListMapper.getApplyView(counselorId, applyNo);
		List<FileEntity> fileList=getFileInfo(counseleeListEntity.getFileNo());
		
		result.put("fileList", fileList);
		result.put("applyList", counseleeListEntity);
		return result;
	}
	
	public Map<String, Object> getCounselingRecord(int applyNo) {
		Map<String, Object> result=new HashMap<>();
		CounselingRecordEntity counselingRecordEntity=null;
		
		int recordCount=counseleeListMapper.counselingRrcordCount(applyNo);
		String today=counseleeListMapper.getToday();
		if(recordCount>0) {
			counselingRecordEntity=counseleeListMapper.getcounselingRecord(applyNo);
		}

		result.put("recordList", counselingRecordEntity);
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
		
		if(!searchValue.equals("")) {
			if(category.equals("A")) {		//검색어만 있는 경우
				totalList=counseleeListMapper.counselingRecordListSearchCount(counselorId, searchValue);
				recordList=counseleeListMapper.getCounselingRecordSearchList(counselorId, start, listCount, searchValue);		
			} else {		//검색어와 카테고리 모두 있는 경우
				totalList=counseleeListMapper.counselingRecordListCateSearchCount(counselorId, searchValue, category);
				recordList=counseleeListMapper.getCounselingRecordCateSearchList(counselorId, start, listCount,searchValue, category);	
			}
		} else  {
			if(category.equals("A")) {		//검색어 카테고리 모두 없는 경우
				totalList=counseleeListMapper.counselingRecordListCount(counselorId);
				recordList=counseleeListMapper.getCounselingRecordList(counselorId, start, listCount);	

			} else {  //카테고리만 있는 경우
				totalList=counseleeListMapper.counselingRecordListCateCount(counselorId,category);
				recordList=counseleeListMapper.getCounselingRecordCateList(counselorId, start, listCount, category);	
			}
		}
		
		int totalPage = (int)Math.ceil((double)totalList/listCount);	
		if(totalPage==0) {totalPage=1;}
		
		result.put("totalPage", totalPage);
		result.put("totalList", totalList);
		result.put("recordList", recordList);
		
		return result;
	}

	public ResponseEntity<String> saveCounselingRecord(CounselingRecordDto counselingRecordDto, HttpServletRequest req){
		counselingRecordDto.setCounselorNo(getCounselorId(req));
		int insertResult=counseleeListMapper.saveCounselingRecord(counselingRecordDto);
		if(insertResult>0) {
			
			int updateResult=counseleeListMapper.updateCounselingProgress(counselingRecordDto.getApplyNo());
			int result=insertResult&updateResult;
			if(result> 0) {
				return ResponseEntity.ok("ok");							
			} else {
				return ResponseEntity.status(704).build();
			}
				
		} else {
			return ResponseEntity.status(704).build();
		}
	}
	
	public ResponseEntity<String> modifyCounselingRecord(CounselingRecordDto counselingRecordDto){
		int modifyResult=counseleeListMapper.modifyCounselingRecord(counselingRecordDto);
			
		if(modifyResult>0) {
			return ResponseEntity.ok("ok");		
		} else {
			return ResponseEntity.status(704).build();
		}
	}
	
	public List<FileEntity> getFileInfo(Integer fileNo) {
		 List<FileEntity> fileList=fileMapper.selectFilePathMapper(fileNo);
		return fileList;
	}
}
