package com.counseling.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.CounselorScheduleDto;
import com.counseling.cms.service.CounselorScheduleService;

@RestController
public class CounselorScheduleController {

    private final CounselorScheduleService counselorScheduleService;

    @Autowired
    public CounselorScheduleController(CounselorScheduleService counselorScheduleService) {
        this.counselorScheduleService = counselorScheduleService;
    }

    @GetMapping("/counselor/schedule")
    public ResponseEntity<List<CounselorScheduleDto>> getCounselorSchedule(@RequestParam String name) {
        List<CounselorScheduleDto> schedules = counselorScheduleService.getSchedulesByCounselorName(name);
        return ResponseEntity.ok(schedules); // List를 JSON 형태로 반환
    }
	
}
