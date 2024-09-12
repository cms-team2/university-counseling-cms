package com.counseling.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.CounselorScheduleDto;
import com.counseling.cms.service.CounselorScheduleService;

@RestController
public class CounselorScheduleController {

	@Autowired
    private final CounselorScheduleService counselorScheduleService;

    public CounselorScheduleController(CounselorScheduleService counselorScheduleService) {
        this.counselorScheduleService = counselorScheduleService;
    }

    @GetMapping("/counselor/schedule")
    public List<CounselorScheduleDto> getCounselorSchedule(@RequestParam String name) {
        return counselorScheduleService.getSchedulesByCounselorName(name);
    }
	
}
