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
	
	public List<CounselingStatisticsDto> getCounselingStatistics(){
		return statisticsMapper.selectCounselingStatistics();
	}
}
