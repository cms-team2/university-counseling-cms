package com.counseling.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.dto.AdminScheduleDto;
import com.counseling.cms.service.AdminScheduleService;


@Controller
public class AdminScheduleController {

	@Autowired
	private AdminScheduleService scheduleService;
	
	//상담 목록 출력
	@GetMapping("/admin/schedulelisting")
	public String getCounselingList(Model model,
			@RequestParam(value="", required = false)Integer pageno, 
			@RequestParam(value="", required = false)String search_type, 
			@RequestParam(value="", required = false)String search_value,
			@RequestParam(value="", required = false)String status) {
		List<AdminScheduleDto> schedules = scheduleService.getScheduleList();
		
		Integer pgn = 0;
		if(pageno==null) {
			pageno=1;
			pgn=0;
		}
		else if(pageno>0){ 
			pgn=pageno - 1 ;
		}
		
		model.addAttribute("pageno",pageno);
		
		int pg = (int) Math.ceil((double)schedules.size()/15);
		model.addAttribute("schedules",schedules);
		model.addAttribute("pg",pg);
		//model.addAttribute("page",);
		//model.addAttribute("pageno",);
		return "/admin/scheduleList";
	}
	
	//상담 검색
	@GetMapping("/admin/schedule/search")
	public String searchCounselingList(Model model, 
			@RequestParam(value="", required = false)String search_type, 
			@RequestParam(value="", required = false)String search_value,
			@RequestParam(value="", required = false)String status) {
		
		return "/admin/scheduleList";
	}
}
