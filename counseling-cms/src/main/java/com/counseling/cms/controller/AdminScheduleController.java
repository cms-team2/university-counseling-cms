package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminScheduleController {

	//상담 목록 출력
	@GetMapping()
	public String getCounselingList() {
		
		return "/admin/scheduleList";
	}
	
	//상담 검색
	@GetMapping()
	public String searchCounselingList() {
		
		return "/admin/scheduleList";
	}
}
