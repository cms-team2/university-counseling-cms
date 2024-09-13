package com.counseling.cms.controller;

import java.util.List;
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
import com.counseling.cms.dto.ReplyDto;
import com.counseling.cms.service.AdminBoardService;

@Controller
public class AdminBoardController {

	@Autowired
	private AdminBoardService adminBoardService;

	@GetMapping("/admin/getPost")
	public String getPostController(Model model,
			@RequestParam(value = "boardNumber", defaultValue = "1") int boardNumber,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchPart", defaultValue = "") String searchPart,
			@RequestParam(value = "searchValue", defaultValue = "") String searchValue) {

		Map<String, Object> result = adminBoardService.getPostService(boardNumber, page, searchPart, searchValue);

		model.addAttribute("searchPart", searchPart);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("boardNumber", boardNumber);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", result.get("totalPages"));
		model.addAttribute("post", result.get("posts"));

		return "/admin/managePost";
	}

	@PostMapping("/admin/createPost")
	public ResponseEntity<String> createPostController(@ModelAttribute PostDto postDto) {
		return adminBoardService.createPostService(postDto);
	}

	@GetMapping("/admin/getOnePost")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getOnePostController(String postNumber, String boardNumber) {
		return adminBoardService.getOnePostService(postNumber, boardNumber);
	}

	@PostMapping("/admin/modifyPost")
	public ResponseEntity<String> modifyPostController(@ModelAttribute PostDto postDto) {
		return adminBoardService.modifyPostService(postDto);
	}

	@PostMapping("/admin/deleteCheckedPost")
	public ResponseEntity<String> deleteCheckedPostController(@RequestBody List<Integer> postNumber) {
		return adminBoardService.deleteCheckedPostService(postNumber);
	}

	@GetMapping("/admin/deletePost")
	public ResponseEntity<String> deletePostController(String postNumber) {
		return adminBoardService.deletePostService(Integer.valueOf(postNumber));
	}

	@PostMapping("admin/createReply")
	public ResponseEntity<String> createReplyController(@RequestBody ReplyDto replyDto) {
		return adminBoardService.createReplyService(replyDto);
	}

	@PostMapping("admin/createFaq")
	public ResponseEntity<String> createFaq(@RequestBody PostDto postDto) {
		return adminBoardService.createFaqService(postDto);
	}

	 @PostMapping("admin/modifyFaq") 
	 public ResponseEntity<String> modifyFaqController(@RequestBody PostDto postDto){
		 return adminBoardService.modifyFaqService(postDto);
	}
	 

}
