package com.counseling.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.mapper.CounselorMyScheduleMapper;

@Service
public class CounsellorMyScheduleService {
	@Autowired CounselorMyScheduleMapper counselorMyScheduleMapper;
	
	public Map<String, Object> getMySchedule(String id,String startDate,String endDate) {
		List<CounseleeListEntity> result = counselorMyScheduleMapper.counsellor_listall(id,startDate,endDate);
		
		Map<String, Object> allMyList = new HashMap<>();
		allMyList.put("allList",result);
		
		return allMyList;
	}
	
	public ResponseEntity<String> updateMonthlySchedule(String newDate, Integer applyNo) {		
		try {
			Integer result = counselorMyScheduleMapper.update_dscsninfo(newDate, applyNo);
			if(result > 0) {
				return ResponseEntity.ok().build();				
			}else {
				return ResponseEntity.status(608).body("일정 수정 실패");	
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(608).body("일정 수정 실패");
		}
	}
}
