package com.counseling.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.service.CounselorService;

import cms.counseling.cms.dto.CounselorDto;

@Controller
public class CounselorController {

	@Autowired
	private CounselorService counselorService;
	
	//상담사 목록 출력
	@GetMapping("/admin/list-of-counselors")
	public String getCounselorList(Model model) {
		List<CounselorDto> counselors = counselorService.getCounselorList();
		model.addAttribute("counselors", counselors);
		return "/admin/counselorList";
	}
	
	//상담사 검색
	@GetMapping("/admin/counselors/search")
	public String searchCounselors(@RequestParam("searchType") String searchType,
								   @RequestParam("searchKeyword") String searchKeyword,
								   @RequestParam(value = "counselCategory", required = false) String counselCategory,
								   @RequestParam("status") String status,
								   Model model) {
		List<CounselorDto> counselors = counselorService.searchCounselors(searchType, searchKeyword,counselCategory, status);
		model.addAttribute("counselors", counselors);
	    model.addAttribute("searchType", searchType);  // 검색 유형을 다시 전달
	    model.addAttribute("searchKeyword", searchKeyword);  // 검색어를 다시 전달
	    model.addAttribute("counselCategory", counselCategory);
	    model.addAttribute("status", status);
		return "/admin/counselorList";
	}
	
}
