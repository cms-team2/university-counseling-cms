package com.counseling.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.counseling.cms.service.CounselorService;

import cms.counseling.cms.dto.CounselorDto;

@Controller
public class CounselorController {

	@Autowired
	private CounselorService counselorService;
	
	@GetMapping("/admin/counselor-list")
	public String getCounselorList(Model model) {
		List<CounselorDto> counselors = counselorService.getCounselorList();
		System.out.println(counselors);
		model.addAttribute("counselors", counselors);
		return "/admin/counselorList";
	}
}
