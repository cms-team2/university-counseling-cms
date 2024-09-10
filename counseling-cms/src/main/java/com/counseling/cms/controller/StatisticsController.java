package com.counseling.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.CounselingStatisticsDto;
import com.counseling.cms.service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;
	
	@GetMapping("/counseling")
	public ResponseEntity<List<CounselingStatisticsDto>> getCounselingStatistics(){
		List<CounselingStatisticsDto> statistics = statisticsService.getCounselingStatistics();
		return ResponseEntity.ok(statistics);
		
	}
}
