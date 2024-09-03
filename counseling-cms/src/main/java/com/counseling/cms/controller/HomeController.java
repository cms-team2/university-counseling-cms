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
		return "/user/login.html";
	}
	
	@GetMapping("/user/pw/find")
	public String userFindPassword() {
		return "/user/pw/find.html";
	}
	
	@GetMapping("/user/pw/change")
	public String userChangePassword() {
		return "/user/pw/change.html";
	}
	
	@GetMapping("/user/main/introduction")
	public String userIntroduction() {
		return "/user/introduction.html";
	}
	
}
