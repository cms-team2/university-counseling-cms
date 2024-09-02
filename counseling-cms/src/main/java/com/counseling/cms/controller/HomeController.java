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
	
	@GetMapping("/apply-list")
	public String applyListPage() {
		return "/admin/applyList";
	}
	
	@GetMapping("/schedule-list")
	public String scheduleList() {
		return "/admin/scheduleList";
	}
	
	@GetMapping("/board-management")
	public String boardManagement() {
		return "/admin/boardManagement";
	}
	
	@GetMapping("/counselor-schedule")
	public String counselorSchedule() {
		return "/admin/counselorSchedule";
	}
	
	@GetMapping("/manage-post")
	public String managePost() {
		return "/admin/managePost";
	}
	
}
