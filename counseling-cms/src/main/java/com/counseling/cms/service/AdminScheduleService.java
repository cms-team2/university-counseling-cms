package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.AdminScheduleDto;
import com.counseling.cms.mapper.AdminScheduleMapper;

@Service
public class AdminScheduleService {

	@Autowired
	private AdminScheduleMapper scheduleMapper;
	
	
	//상담 목록
	public AdminScheduleService(AdminScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}
	
	public List<AdminScheduleDto> getScheduleList(String search_type, String search_value, String status, Integer pageno, Integer ea){
		return scheduleMapper.selectSchedules(search_type, search_value, status, pageno, ea);
	}
	
	public int getCountSchedules(String search_type, String search_value) {
		return scheduleMapper.countSchedules(search_type, search_value);
	}
	
}
