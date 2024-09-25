package com.counseling.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.PageBannerDto;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.entity.PageBannerEntity;
import com.counseling.cms.service.PageBannerService;

@Controller
public class pageBannerController {
	@Autowired
	private PageBannerService pageBannerService;
	
	@PostMapping("/admin/banner/create")
	@ResponseBody
	public ResponseEntity<String> createBanner(@ModelAttribute PageBannerDto adminBannerDto){
		
		return pageBannerService.createBannerService(adminBannerDto);
	}
	
	@GetMapping("/admin/banner-modify")
	public String bannerModify(Model m, @RequestParam(value="idx", defaultValue = "") Integer idx) {
		Map<String, Object> result = pageBannerService.getBannerModifyService(idx);
		
		m.addAttribute("bannerData",result.get("bannerData"));
		m.addAttribute("file_name",result.get("file_name"));
		m.addAttribute("file_no",result.get("file_no"));
		m.addAttribute("file_path",result.get("file_path"));
		
		return "admin/bannerModify";
	}
	
	@PostMapping("/admin/banner/modify")
	@ResponseBody
	public ResponseEntity<String> modifyBannerSubmit(
			@ModelAttribute PageBannerDto pageBannerDto,
			@RequestParam(value="old_filename", defaultValue = "N") String old_filename,
			@RequestParam(value="old_filenumber", defaultValue = "") Integer old_filenumber,
			@RequestParam(value="old_filepath", defaultValue = "") String old_filepath
			){
		
		return pageBannerService.submitBannerModifyService(pageBannerDto,old_filename,old_filenumber,old_filepath);
	}
	
	
	@GetMapping("/admin/bannerList")
	public String getBannerList(
			Model m, 
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="searchText", defaultValue = "") String searchText
			) {
		m.addAttribute("searchText",searchText);
		
		Map<String, Object> totalList = pageBannerService.getBannerListService(page,searchText);
		m.addAttribute("page", page);
		m.addAttribute("pageSize", totalList.get("pageSize"));

		m.addAttribute("totalPages",totalList.get("totalPages"));
		m.addAttribute("listResult",totalList.get("listResult"));
		m.addAttribute("totalPosts",totalList.get("totalPosts"));
		
		return "admin/bannerList";
	}
	
	@PostMapping("/admin/selectBannerInfo")
	@ResponseBody
    public ResponseEntity<Map<String,String>> selectBannerInfo(@RequestBody Map<String, String> selectNo){
		Integer bnr_number =  Integer.valueOf(selectNo.get("bnr_no"));
		Map<String,String> result = pageBannerService.selectBannerInfoService(bnr_number);
		
		return ResponseEntity.ok(result);
    }
	
	@PostMapping("/admin/bannerDelete")
	@ResponseBody
	public ResponseEntity<String> bannerDelete(@RequestBody Map<String, Integer> deleteNo) {
	    Integer delete_number = deleteNo.get("bnr_no");
	    
	    return pageBannerService.deleteBannerListService(delete_number);
	}
	
	@GetMapping("/admin/seqCheck")
	@ResponseBody
	public ResponseEntity<String> seqCheck(@RequestParam Integer seq, @RequestParam String page, @RequestParam(required=false) String majorCode){
		return pageBannerService.seqCheck(seq, page, majorCode);
	}

}
