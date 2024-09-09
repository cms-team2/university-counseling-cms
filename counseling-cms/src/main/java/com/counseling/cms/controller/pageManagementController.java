package com.counseling.cms.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.AdminBannerDto;
import com.counseling.cms.service.PageBannerService;

@Controller
public class pageManagementController {
	@Autowired
	private PageBannerService pageBannerService;
	
	@PostMapping("/admin/banner/create")
	@ResponseBody
	public ResponseEntity<String> createBanner(@ModelAttribute AdminBannerDto adminBannerDto){
		
		return pageBannerService.createBannerService(adminBannerDto);
	}
	
	@GetMapping("/admin/bannerList")
	public String getBannerList(AdminBannerDto adminBannerDto) {
		Map<String,Object> abc = pageBannerService.getBannerList();
		
		return "admin/bannerList";
	}
}
