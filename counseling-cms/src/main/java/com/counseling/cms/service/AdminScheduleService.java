package com.counseling.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.AdminScheduleCounselorDto;
import com.counseling.cms.dto.AdminScheduleDto;
import com.counseling.cms.dto.AdminScheduleModalDto;
import com.counseling.cms.mapper.AdminScheduleMapper;

@Service
public class AdminScheduleService {

	@Autowired
	private AdminScheduleMapper scheduleMapper;
	
	
	public AdminScheduleService(AdminScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}
	
	//상담 목록
	public List<AdminScheduleDto> getScheduleList(String search_type, String search_value, String status, Integer pageno, Integer ea){
		return scheduleMapper.selectSchedules(search_type, search_value, status, pageno, ea);
	}
	
	public int getCountSchedules(String search_type, String search_value) {
		return scheduleMapper.countSchedules(search_type, search_value);
	}
	
	public Map<String, Object> getScheduleModals(String student_number, String apply_number){
		
		Map<String, Object> scheduleModalMap = new HashMap<String, Object>();

		try {
			AdminScheduleModalDto scheduleModal = scheduleMapper.selectSchedulesModal(apply_number);
			String oldestDate = scheduleMapper.firstCounseling(student_number);
			String latestDate = scheduleMapper.lastCounseling(student_number);
			String reservateDate = scheduleModal.getCounselingReservateDate();
			List<String> counselors = scheduleMapper.getCounslerList(reservateDate);
			List<AdminScheduleCounselorDto> haveTimeCounselors = scheduleMapper.getCounselors(counselors);
			
			scheduleModalMap.put("scheduleModal", scheduleModal);
			scheduleModalMap.put("oldestDate", oldestDate);
			scheduleModalMap.put("latestDate", latestDate);
			scheduleModalMap.put("counselors", haveTimeCounselors);
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return scheduleModalMap;
	}
	
	public String getResponseUpdate(String employee_number, String apply_number) {
		
		String result = null;
		try {
			int response = scheduleMapper.updateCounselorAllotment(employee_number, apply_number);
			if(response>0) {
				result="ok";
			}
			else {
				result="no";
			}
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
}
