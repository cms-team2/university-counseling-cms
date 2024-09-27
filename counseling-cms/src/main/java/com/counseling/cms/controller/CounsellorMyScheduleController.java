package com.counseling.cms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.counseling.cms.dto.PageMajorCategoryDto;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.service.CounsellorMyScheduleService;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CounsellorMyScheduleController {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CounsellorMyScheduleService counsellorMyScheduleService;
	
	@PostMapping("/counselor/monthly-data")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getThisMonthlyList(@RequestBody Map<String, String> data,HttpServletRequest request) {
		String accessToken = CookieUtility.getCookie(request, "accessToken");
        String id = jwtUtil.extractUserId(accessToken);
        String startDate = data.get("date").split("-")[0]+"-"+data.get("date").split("-")[1]+"-"+"01";
        String endDate = data.get("date") + " 23:59:59";
        
        Map<String, Object> result = counsellorMyScheduleService.getMySchedule(id,startDate,endDate);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/counselor/monthly-modify")
	@ResponseBody
	public ResponseEntity<String> modifyMonthly(@RequestBody Map<String, String> data) {
		String newDate = data.get("modify_date") + " " + data.get("modify_time");
		Integer applyNo = Integer.valueOf(data.get("applyNo"));
		
		return counsellorMyScheduleService.updateMonthlySchedule(newDate, applyNo);
	}
	
	@PostMapping("/counselor/weekly-data")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getThisWeeklyList(@RequestBody Map<String, String> data,HttpServletRequest request) {
		String accessToken = CookieUtility.getCookie(request, "accessToken");
        String id = jwtUtil.extractUserId(accessToken);
        
        String startDate = data.get("startdate");
        String endDate = data.get("enddate") + " 23:59:59";
        
        Map<String, Object> result = counsellorMyScheduleService.getMySchedule(id,startDate,endDate);
        
		return ResponseEntity.ok(result);
	}
	

}
