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
		Integer ea = 15;
		List<AdminScheduleDto> schedules = scheduleService.getScheduleList(search_type, search_value, status, pageno, ea);
		int countSchedules = scheduleService.getCountSchedules(search_type, search_value);
		
		if(pageno==null) {
			pageno=1;
		}
		model.addAttribute("pageno",pageno);
		
		int pg = (int) Math.ceil((double)countSchedules/ea);
		
		model.addAttribute("schedules",schedules);
		model.addAttribute("pg",pg);
		model.addAttribute("ea",ea);
		model.addAttribute("search_type",search_type);
		model.addAttribute("search_value",search_value);
		model.addAttribute("status",status);
		return "/admin/scheduleList";
	}
	
	//상담사 일정표
	@GetMapping("/admin/counselorscheduling")
	public String getCounselingSchedules() {
		return "admin/counselorSchedule";
	}
}
