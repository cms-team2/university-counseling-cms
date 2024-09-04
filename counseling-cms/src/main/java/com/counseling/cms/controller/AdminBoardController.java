package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.service.AdminBoardService;


@Controller
public class AdminBoardController {
	
	@Autowired
	private AdminBoardService adminBoardService;
	
	@GetMapping("/admin/getPost")
	public String getPostController(Model model, 
			@RequestParam(value="boardNumber", defaultValue = "1") int boardNumber,
			@RequestParam(value="page", defaultValue = "1") int page) {
		
		model.addAttribute("boardNumber", boardNumber);
		model.addAttribute("page", page);
		model.addAttribute("post",adminBoardService.getPostService(boardNumber, page));
	
		return "/admin/managePost"; 
	}
}
