package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.service.CounseleeListService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CounseleeListController {
	
	@Autowired
	private CounseleeListService counseleeListService;
	
	@GetMapping("/counselor/getCounseleeList")
	public String getCounseleeList(Model model, HttpServletRequest req, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			@RequestParam(value="category", defaultValue="A") String category) {
		Map<String, Object> result=counseleeListService.getCounseleeList(req, page, searchValue, category);
		
		model.addAttribute("category",category);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("totalPage",result.get("totalPage"));
		model.addAttribute("counseleeList",result.get("counseleeList"));
		model.addAttribute("totalList",result.get("totalList"));
		return "/counselor/counseleeList";
	}
	
	@GetMapping("/counselor/applyContent")
	public String getApplyContent(@RequestParam(value="applyNo", required = true) int applyNo, HttpServletRequest req, Model model) {
		CounseleeListEntity applyList=counseleeListService.getApplyView(req, applyNo);
		model.addAttribute("applyList", applyList);
		return "/counselor/counseleeVIew";
	}
	
	@GetMapping("/counselor/writeCounselingRecord")
	public String counselingRecord(@RequestParam(value="applyNo", required = true) int applyNo, HttpServletRequest req, Model model) {
		CounseleeListEntity applyList=counseleeListService.getApplyView(req, applyNo);
		model.addAttribute("applyList", applyList);
		
		Map<String, Object> result=counseleeListService.getCounselingRecord(applyNo);
		
		model.addAttribute("today", result.get("today"));
		model.addAttribute("recordCounct", result.get("recordCount"));
		model.addAttribute("recordList", result.get("recordList"));
		model.addAttribute("counselorName", result.get("counselorName"));
		return "/counselor/counselingRecord";
	}
	
	@GetMapping("/counselor/counselingRecordList")
	public String getcounselingRecordList(Model model, HttpServletRequest req, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			@RequestParam(value="category", defaultValue="A") String category) {
		Map<String, Object> result=counseleeListService.getCounselingRecordList(req, page, searchValue, category);
		
		model.addAttribute("category",category);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("totalPage",result.get("totalPage"));
		model.addAttribute("recordList",result.get("recordList"));
		model.addAttribute("totalList",result.get("totalList"));
		
		return "/counselor/counselingRecordList";
	}
	
}
