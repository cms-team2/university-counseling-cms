package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/welcome")
	public String homePage() {
		return "welcome";
	}
	
	@GetMapping("/")
	public String index() {
		return "/index.html";
	}
	
	@GetMapping("/user/login")
	public String userLoginPage() {
		return "/user/userLogin.html";
	}
	
	@GetMapping("/user/find")
	public String userFindPassword() {
		return "/pw/find.html";
	}
	
	@GetMapping("/user/change")
	public String userChangePassword() {
		return "/pw/change.html";
	}
	
	@GetMapping("/counselor/monthly-calendar")
	public String test() {
		return "/counselor/monthlyCalendar.html";
	}
	
	@GetMapping("/counselor/weekly-calendar")
	public String test2() {
		return "/counselor/weeklyCalendar.html";
	}
	
	
}
