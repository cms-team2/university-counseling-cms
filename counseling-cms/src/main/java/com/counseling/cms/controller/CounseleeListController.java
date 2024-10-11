package com.counseling.cms.controller;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counseling.cms.dto.AddApplyDto;
import com.counseling.cms.dto.CounselingRecordDto;
import com.counseling.cms.service.CounseleeListService;
import com.counseling.cms.utility.FileUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CounseleeListController {
	
	@Autowired
	private CounseleeListService counseleeListService;
	
	@Autowired
	private FileUtility fileUtility;
	
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
		Map<String, Object> applyResult=new HashMap<>();
		applyResult=counseleeListService.getApplyView(req, applyNo);

		model.addAttribute("applyList", applyResult.get("applyList"));
		model.addAttribute("fileList", applyResult.get("fileList"));
		return "/counselor/counseleeView";
	}
	
	@GetMapping("/counselor/writeCounselingRecord")
	public String counselingRecord(@RequestParam(value="applyNo", required = true) int applyNo, HttpServletRequest req, Model model) {
		Map<String, Object> applyResult=new HashMap<>();
		
		applyResult=counseleeListService.getApplyView(req, applyNo);
		model.addAttribute("applyList", applyResult.get("applyList"));
		model.addAttribute("fileList", applyResult.get("fileList"));
		
		Map<String, Object> result=counseleeListService.getCounselingRecord(applyNo, req);
		
		model.addAttribute("tomorrow", result.get("tomorrow"));
		model.addAttribute("today", result.get("today"));
		model.addAttribute("recordCount", result.get("recordCount"));
		model.addAttribute("recordList", result.get("recordList"));
		model.addAttribute("counselorName", result.get("counselorName"));
		return "/counselor/counselingRecord";
	}
	
	@GetMapping("/counselor/counselingRecordList")
	public String getCounselingRecordList(Model model, HttpServletRequest req, 
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
	
	@PostMapping("/counselor/counselingRecordSave")
	@ResponseBody
	public ResponseEntity<String> counselingRecordSave(@RequestBody CounselingRecordDto counselingRecordDto, HttpServletRequest req){

		return counseleeListService.saveCounselingRecord(counselingRecordDto, req);
	}
	
	@PostMapping("/counselor/counselingRecordModify")
	@ResponseBody
	public ResponseEntity<String> counselingRecordModify(@RequestBody CounselingRecordDto counselingRecordDto){

		return counseleeListService.modifyCounselingRecord(counselingRecordDto);
	}
	
	@PostMapping("/counselor/addApply")
	public ResponseEntity<String> addApply(@RequestBody AddApplyDto addApplyDto, HttpServletRequest req){
		
		return counseleeListService.addApply(addApplyDto, req);
	}
	
	@GetMapping("/counselor/downloadFile")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")

	public ResponseEntity<UrlResource> downloadFile(@RequestParam String fileSeq, HttpServletResponse res) throws MalformedURLException {
		return fileUtility.downloadFile(fileSeq, res);	
	}
	
	@GetMapping("/counselor/todaySchedule")
	public ResponseEntity<List<String>> todaySchedule(HttpServletRequest req){
		
		return counseleeListService.getTodaySchedule(req);
	}
	
}
