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
    private CounselorScheduleService counselorScheduleService;
    
    // 특정 상담사의 월별 일정
    @GetMapping("/admin/schedule")
    public List<CounselorScheduleDto> getSchedule(@RequestParam(required = false) String name, @RequestParam String month) {
        // 상담사 이름이 있으면 해당 상담사 일정만 반환, 없으면 전체 일정 반환
        if (name != null && !name.isEmpty()) {
            return counselorScheduleService.getMonthlySchedules(name, month);
        } else {
            return counselorScheduleService.getAllMonthlySchedules(month);
        }
    }

    // 전체 상담사의 월별 일정
    @GetMapping("/admin/schedule/all")
    public List<CounselorScheduleDto> getAllSchedule(@RequestParam String month) {
        return counselorScheduleService.getAllMonthlySchedules(month);

    }
}
