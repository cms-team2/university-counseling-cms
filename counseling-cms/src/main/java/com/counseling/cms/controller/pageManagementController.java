package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class pageManagementController {
	@PostMapping("/admin/banner/create")
	public String createBanner() {
		System.out.println("여기");
		
		return null;
	}
}
