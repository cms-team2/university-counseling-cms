package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String homePage() {
		return "welcome";
	}
	
	@GetMapping("/admin")
	public String adminPage() {
		return "/admin/admin";
	}
	
	@GetMapping("/applyList")
	public String applyListPage() {
		return "/admin/applyList";
	}
	
	@GetMapping("/scheduleList")
	public String scheduleList() {
		return "/admin/scheduleList";
	}
	
	@GetMapping("boardManagement")
	public String boardManagement() {
		return "/admin/boardManagement";
	}
	
}
