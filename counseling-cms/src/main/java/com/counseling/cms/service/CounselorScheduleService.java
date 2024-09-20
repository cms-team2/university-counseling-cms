package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.CounselorScheduleDto;
import com.counseling.cms.mapper.CounselorScheduleMapper;

@Service
public class CounselorScheduleService {
    
	@Autowired
    private CounselorScheduleMapper counselorScheduleMapper;

    public List<CounselorScheduleDto> getMonthlySchedules(String name, String month) {
        return counselorScheduleMapper.getMonthlySchedules(name, month);
    }

    public List<CounselorScheduleDto> getAllMonthlySchedules(String month) {
        return counselorScheduleMapper.getAllMonthlySchedules(month);
    }

}
