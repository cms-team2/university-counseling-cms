package com.counseling.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counseling.cms.dto.PageBannerDto;
import com.counseling.cms.dto.PageMajorCategoryDto;
import com.counseling.cms.dto.PageSubCategoryDto;
import com.counseling.cms.entity.PageMajorCatgoryEntity;
import com.counseling.cms.entity.PageSubCategoryEntity;
import com.counseling.cms.mapper.PageMajorCategoryMapper;
import com.counseling.cms.mapper.PageSubCategoryMapper;

@Service
public class PageCategoryService {
	@Autowired
	private PageMajorCategoryMapper pageMajorCategoryMapper;
	
	@Autowired
	private PageSubCategoryMapper pageSubCategoryMapper;
	
	public Map<String, Object> getMajorCatgoryService(String sec,String searchPart,String searchText){
		List<PageMajorCatgoryEntity> result = null;
		if(searchPart.equals("menuName")) {
			result = pageMajorCategoryMapper.nameMajorCatgoryList(searchText);	
		}else if(searchPart.equals("menuCode")){
			result = pageMajorCategoryMapper.codeMajorCatgoryList(searchText);
		}else {
			if(sec.equals("")) {
				result = pageMajorCategoryMapper.allMajorCatgoryList();
			}else {
				result = pageMajorCategoryMapper.secMajorCatgoryList(sec);									
			}				
		}
		
		Map<String, Object> majorCategoryList = new HashMap<>();
		majorCategoryList.put("categoryList", result);
		majorCategoryList.put("categoryListCount", result.size());
		
		return majorCategoryList;
	}
	
	public ResponseEntity<String> createMajorCategoryService(PageMajorCategoryDto pageMajorCategoryDto){
		String checkValue = "";
		if(pageMajorCategoryDto.getMenuYn() == "true") {
			checkValue = "Y";
		}else {
			checkValue = "N";
		}
		PageMajorCatgoryEntity pageMajorCatgoryEntity = new PageMajorCatgoryEntity(); 
		pageMajorCatgoryEntity.pageMarjorCategoryEntityCk(pageMajorCategoryDto,checkValue);
		
		try {
			int result = pageMajorCategoryMapper.insertMajorCatgory(pageMajorCatgoryEntity);
			if(result > 0) {
				return ResponseEntity.ok().build();				
			}else {
				return ResponseEntity.status(606).body("대메뉴 등록 실패");	
			}
		}catch(org.springframework.dao.DuplicateKeyException e) {
			return ResponseEntity.status(607).body("대메뉴 코드 중복");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(606).body("대메뉴 등록 실패");
		}
	}
	
	public Map<String, Object> modifyMajorCategoryService(String menuCode) {
		PageMajorCatgoryEntity result = pageMajorCategoryMapper.selectByCodeMajorCategoryMapper(menuCode);
		
		Map<String, Object> majorSelectOne = new HashMap<>();
		majorSelectOne.put("majorSelectOne", result);
		
		return majorSelectOne;
	}
	
	public ResponseEntity<String> updateMajorCategoryService(PageMajorCategoryDto pageMajorCategoryDto) {
		String checkValue = "";
		if(pageMajorCategoryDto.getMenuYn() == "true") {
			checkValue = "Y";
		}else {
			checkValue = "N";
		}
		PageMajorCatgoryEntity pageMajorCatgoryEntity = new PageMajorCatgoryEntity(); 
		pageMajorCatgoryEntity.pageMarjorCategoryEntityCk(pageMajorCategoryDto,checkValue);
		
		try {
			int result = pageMajorCategoryMapper.updateMajorCatgory(pageMajorCatgoryEntity);
			if(result > 0) {
				return ResponseEntity.ok().build();				
			}else {
				return ResponseEntity.status(606).body("대메뉴 수정 실패");	
			}
		}catch(org.springframework.dao.DuplicateKeyException e) {
			return ResponseEntity.status(607).body("대메뉴 코드 중복");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(606).body("대메뉴 등록 실패");
		}
	}
	
	public ResponseEntity<String> deleteMajorCategoryService(String menuCode) {
		try {
			int result = pageMajorCategoryMapper.deleteMajorCategoryMapper(menuCode);
			if(result > 0) {
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.status(604).body("대메뉴 삭제 실패");
			}
		}catch(org.springframework.dao.DataIntegrityViolationException e) {
			return ResponseEntity.status(605).body("하위 소메뉴로 인한 삭제 실패");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(604).body("대메뉴 삭제 실패");
		}
	}
	
	/*  sub menu  */
	public Map<String, Object> getSubCatgoryService(String majorMenuCode,String searchPart,String searchText){
		
		List<PageSubCategoryEntity> result = null;
		if(searchPart.equals("menuName")) {
			result = pageSubCategoryMapper.nameSubCatgoryList(searchText);	
		}else if(searchPart.equals("menuCode")){
			result = pageSubCategoryMapper.codeSubCatgoryList(searchText);
		}else {
			if(majorMenuCode.equals("")) {
				result = pageSubCategoryMapper.selectAllSubCategoryMapper();
			}else {
				result = pageSubCategoryMapper.selectByCodeSubCategoryMapper(majorMenuCode);			
			}			
		}	
		
		int categoryListCount = result.size();
		
		Map<String, Object> subCategoryList = new HashMap<>();
		subCategoryList.put("subcategoryList", result);
		subCategoryList.put("categoryListCount", categoryListCount);
		
		return subCategoryList;
	}
	
	public ResponseEntity<String> createSubCategoryService(PageSubCategoryDto pageSubCategoryDto){
		String checkValue = "";
		if(pageSubCategoryDto.getMenuYn() == "true") {
			checkValue = "Y";
		}else {
			checkValue = "N";
		}
		PageSubCategoryEntity pageSubCategoryEntity = new PageSubCategoryEntity();
		pageSubCategoryEntity.PageSubCategoryEntityCk(pageSubCategoryDto,checkValue);
		
		try {
			int result = pageSubCategoryMapper.insertSubCatgory(pageSubCategoryEntity);
			if(result > 0) {
				return ResponseEntity.ok().build();				
			}else {
				return ResponseEntity.status(606).body("소메뉴 등록 실패");	
			}
		}catch(org.springframework.dao.DuplicateKeyException e) {
			return ResponseEntity.status(607).body("소메뉴 코드 중복");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(606).body("소메뉴 등록 실패");
		}
	}
	
	public Map<String, Object> modifySubCategoryService(String menuCode) {
		PageSubCategoryEntity result = pageSubCategoryMapper.selectOneByCodeSubCategoryMapper(menuCode);
		
		Map<String, Object> majorSelectOne = new HashMap<>();
		majorSelectOne.put("majorSelectOne", result);
		
		return majorSelectOne;
	}
	
	public ResponseEntity<String> updateSubCategoryService(PageSubCategoryDto pageSubCategoryDto) {
		String checkValue = "";
		if(pageSubCategoryDto.getMenuYn() == "true") {
			checkValue = "Y";
		}else {
			checkValue = "N";
		}
		PageSubCategoryEntity pageSubCategoryEntity = new PageSubCategoryEntity();
		pageSubCategoryEntity.PageSubCategoryEntityCk(pageSubCategoryDto, checkValue);
		
		try {
			int result = pageSubCategoryMapper.updateSubCatgory(pageSubCategoryEntity);
			if(result > 0) {
				return ResponseEntity.ok().build();				
			}else {
				return ResponseEntity.status(606).body("소메뉴 수정 실패");	
			}
		}catch(org.springframework.dao.DuplicateKeyException e) {
			return ResponseEntity.status(607).body("소메뉴 코드 중복");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(606).body("소메뉴 등록 실패");
		}
	}
	
	public ResponseEntity<String> deleteSubCategoryService(String menuCode) {
		try {
			int result = pageSubCategoryMapper.deleteSubCategoryMapper(menuCode);
			if(result > 0) {
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.status(604).body("대메뉴 삭제 실패");
			}
		}catch(Exception e) {
			return ResponseEntity.status(604).body("대메뉴 삭제 실패");
		}
	}
	
	
}
