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
	
	//성별 상담횟수 통계
	@GetMapping("/counseling-by-gender")
	public ResponseEntity<List<CounselingStatisticsDto>> getCounselingStatisticsByGender(){
		List<CounselingStatisticsDto> statistics = statisticsService.getCounselingStatisticsByGender();
		return ResponseEntity.ok(statistics);
	}
	
	//연도별 상담횟수 통계
	@GetMapping("/counseling-by-year")
	public ResponseEntity<List<CounselingStatisticsDto>> getCounselingStatisticsByYear() {
	    List<CounselingStatisticsDto> statistics = statisticsService.getCounselingStatisticsByYear();
	    return ResponseEntity.ok(statistics);
	}
}
