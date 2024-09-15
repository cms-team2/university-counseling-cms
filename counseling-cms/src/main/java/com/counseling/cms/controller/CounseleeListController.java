package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.service.CounseleeListService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CounseleeListController {
	
	@Autowired
	private CounseleeListService counseleeListService;
	
	@GetMapping("/counselor/getCounseleeList")
	public String getCounseleeList(Model model, HttpServletRequest req) {
		Map<String, Object> result=counseleeListService.getCounseleeList(req);
		model.addAttribute("counseleeList",result.get("counseleeList"));
		return "/counselor/counseleeList";
	}
	
}
