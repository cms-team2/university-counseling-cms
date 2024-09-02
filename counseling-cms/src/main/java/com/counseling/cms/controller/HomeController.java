package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String homePage() {
		return "welcome";
	}
	
	@GetMapping("/admin/counselor-list")
	public String counselorListPage() {
		return "admin/counselorList";
	}
	
	@GetMapping("/admin/admin-list")
	public String adminListPage() {
		return "admin/adminList";
	}
	
	@GetMapping("/admin/banner-list")
	public String bannerListPage() {
		return "admin/bannerList";
	}
	
	@GetMapping("/admin/banner-create")
	public String bannerCreatePage() {
		return "admin/bannerCreate";
	}
	
	@GetMapping("/admin/menu-list1")
	public String menuListPage1() {
		return "admin/menuList-M";
	}
	
	@GetMapping("/admin/menu-list2")
	public String menuListPage2() {
		return "admin/menuList-C";
	}
}
