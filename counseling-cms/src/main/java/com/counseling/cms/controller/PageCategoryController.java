package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counseling.cms.dto.PageMajorCategoryDto;
import com.counseling.cms.dto.PageSubCategoryDto;
import com.counseling.cms.mapper.PageMajorCategoryMapper;
import com.counseling.cms.service.PageCategoryService;

@Controller
public class PageCategoryController {
	@Autowired
	private PageCategoryService pageCategoryService;
	
	@GetMapping("/admin/menu/major/list")
	public String listMajorCatgory(
			Model model,
			@RequestParam(value="sec", defaultValue = "") String sec,
			@RequestParam(value="searchPart", defaultValue = "") String searchPart,
			@RequestParam(value="searchText", defaultValue = "") String searchText
			) {
		
		Map<String, Object> result = pageCategoryService.getMajorCatgoryService(sec,searchPart,searchText);
		model.addAttribute("categoryList",result.get("categoryList"));
		model.addAttribute("sec",sec);
		model.addAttribute("searchPart",searchPart);
		model.addAttribute("searchText",searchText);
		model.addAttribute("categoryListCount",result.get("categoryListCount"));
		
		return "admin/menuList-M";
	}
	
	@PostMapping("/admin/major/create")
	public ResponseEntity<String> createMajorCategory(@RequestBody PageMajorCategoryDto pageMajorCategoryDto){
		
		
		return pageCategoryService.createMajorCategoryService(pageMajorCategoryDto);
	}
	
	@PostMapping("/admin/major/modify")
	@ResponseBody
	public Map<String, Object> modifyMajorCategory(Model model,@RequestBody PageMajorCategoryDto pageMajorCategoryDto) {
		String menuCode = pageMajorCategoryDto.getMenuCode();
		Map<String, Object> result = pageCategoryService.modifyMajorCategoryService(menuCode);
	    
	    return result;
	}
	
	@PostMapping("/admin/major/update")
	@ResponseBody
	public ResponseEntity<String> updateMajorCategory(@RequestBody PageMajorCategoryDto pageMajorCategoryDto) {
		
	    return pageCategoryService.updateMajorCategoryService(pageMajorCategoryDto);
	}
	
	@PostMapping("/admin/major/delete")
	@ResponseBody
	public ResponseEntity<String> deleteMajorCategory(@RequestBody Map<String, String> deleteCode) {
		String menuCode = deleteCode.get("menuCode");
		
	    return pageCategoryService.deleteMajorCategoryService(menuCode);
	}
	
	/*-- sub --*/
	@GetMapping("/admin/menu/sub/list")
	public String listSubCatgory(
			Model model,
			@RequestParam(value="code", defaultValue = "") String code,
			@RequestParam(value="searchPart", defaultValue = "") String searchPart,
			@RequestParam(value="searchText", defaultValue = "") String searchText
			) {
		String sec = "";
		if(!code.equals("")) {
			sec = String.valueOf(code.charAt(1));
		}
		
		if(!searchText.equals("")) {
			sec = "";
		}
		
		Map<String, Object> resultMajor = pageCategoryService.getMajorCatgoryService(sec, "", "");
		model.addAttribute("resultMajor",resultMajor.get("categoryList"));
		model.addAttribute("code",code);
		model.addAttribute("sec",sec);
		
		Map<String, Object> resultSub = pageCategoryService.getSubCatgoryService(code,searchPart,searchText);
		
		model.addAttribute("subcategoryList",resultSub.get("subcategoryList"));
		model.addAttribute("categoryListCount",resultSub.get("categoryListCount"));
		model.addAttribute("searchPart",searchPart);
		model.addAttribute("searchText",searchText);
		
		return "admin/menuList-C";
	}
	

	@PostMapping("/admin/sub/create")
	public ResponseEntity<String> createSubCategory(@RequestBody PageSubCategoryDto pageSubCategoryDto){
		
		return pageCategoryService.createSubCategoryService(pageSubCategoryDto);
	}
	
	@PostMapping("/admin/sub/modify")
	@ResponseBody
	public Map<String, Object> modifySubCategory(Model model,@RequestBody PageSubCategoryDto pageSubCategoryDto) {
		String menuCode = pageSubCategoryDto.getMenuCode();
		Map<String, Object> result = pageCategoryService.modifySubCategoryService(menuCode);
	    
	    return result;
	}
	
	@PostMapping("/admin/sub/update")
	@ResponseBody
	public ResponseEntity<String> updateSubCategory(@RequestBody PageSubCategoryDto pageSubCategoryDto) {
		
	    return pageCategoryService.updateSubCategoryService(pageSubCategoryDto);
	}
	
	@PostMapping("/admin/sub/delete")
	@ResponseBody
	public ResponseEntity<String> deleteSubCategory(@RequestBody Map<String, String> deleteCode) {
		String menuCode = deleteCode.get("menuCode");
		
	    return pageCategoryService.deleteSubCategoryService(menuCode);
	}
	
}
