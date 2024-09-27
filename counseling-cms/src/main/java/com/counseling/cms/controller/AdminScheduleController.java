package com.counseling.cms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	//상담 목록 모달
	@PostMapping("/admin/schedule-list-modal")
	public ResponseEntity<Map<String, Object>> getScheduleListingModal(
			@RequestParam("student_id") String student_id, 
			@RequestParam("apply_number") String apply_number){
		Map<String, Object> scheduleModalData = scheduleService.getScheduleModals(student_id, apply_number);
		return ResponseEntity.ok(scheduleModalData);
	}
	
	//상담 일정 모달 상담사 변경
	@PostMapping("/admin/schedules_allotment")
    public ResponseEntity<String> updateSchedulesAllotment(
            @RequestParam("employee_number") String employee_number,
            @RequestParam("apply_number") String apply_number) {
    	String result = scheduleService.getResponseUpdate(employee_number, apply_number);
    		return ResponseEntity.ok(result);
    }
    
    //상담사 일정표
    @GetMapping("/admin/counselorscheduling")
    public String counselorscheduling() {
    	return "/admin/counselorSchedule";
    }
}
