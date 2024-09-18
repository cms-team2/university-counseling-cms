package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.CounselingStatisticsDto;
import com.counseling.cms.mapper.StatisticsMapper;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsMapper statisticsMapper;
	
	//성별 상담횟수 통계
	public List<CounselingStatisticsDto> getCounselingStatisticsByGender(){
		return statisticsMapper.selectCounselingStatisticsByGender();
	}
	
	//연도별 상담횟수 통계
	public List<CounselingStatisticsDto> getCounselingStatisticsByYear() {
	    return statisticsMapper.selectCounselingStatisticsByYear();
	}
	
    // 월별 상담 통계 조회
    public List<CounselingStatisticsDto> getCounselingStatisticsByMonth() {
        return statisticsMapper.selectCounselingStatisticsByMonth();
    }
    
    // 일별 상담 통계 조회 (추가)
    public List<CounselingStatisticsDto> getCounselingStatisticsByDay(String selectedMonth) {
        return statisticsMapper.selectCounselingStatisticsByDay(selectedMonth);
    }    
}
