package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counseling.cms.dto.PostDto;
import com.counseling.cms.entity.PostEntity;
import com.counseling.cms.service.AdminBoardService;


@Controller
public class AdminBoardController {
	
	@Autowired
	private AdminBoardService adminBoardService;
	
	@GetMapping("/admin/getPost")
	public String getPostController(Model model, 
			@RequestParam(value="boardNumber", defaultValue = "1") int boardNumber,
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="searchPart", defaultValue="제목") String searchPart,
			@RequestParam(value="searchValue", defaultValue="") String searchValue) {
			
		System.out.println(searchPart+searchValue);
		
		model.addAttribute("searchPart", searchPart);
		model.addAttribute("boardNumber", boardNumber);
		model.addAttribute("page", page);
		model.addAttribute("totalPages",adminBoardService.getPostService(boardNumber, page).get("totalPages"));
		model.addAttribute("post",adminBoardService.getPostService(boardNumber, page).get("posts"));
	
		return "/admin/managePost"; 
	}
	
	@PostMapping("/admin/createPost")
	public ResponseEntity<String> createPostController(@ModelAttribute PostDto postDto) {

		return adminBoardService.createPostService(postDto);
	}
	
	@GetMapping("/admin/getOnePost")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getOnePostController(String postNumber) {
		return adminBoardService.getOnePostService(postNumber);
	}
	
	@PostMapping("/admin/modifyPost")
	public ResponseEntity<String> modifyPostController(@ModelAttribute PostDto postDto){
		return null;
	}
}
