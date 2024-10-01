package com.counseling.cms.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.CounselorDto;
import com.counseling.cms.mapper.CounselorMapper;

@Service
public class CounselorService {

	@Autowired
	private CounselorMapper counselorMapper;
	
	
	//상담사 목록
	public CounselorService(CounselorMapper counselorMapper) {
		this.counselorMapper = counselorMapper;
	}
	
	public List<CounselorDto> getCounselorList(){
		return counselorMapper.selectCounselors();
	}

	
	//상담사 검색
	public List<CounselorDto> searchCounselors(String searchType, String searchKeyword, String counselCategory, String status){

		return counselorMapper.searchCounselors(searchType, searchKeyword, counselCategory, status);
	}
	
}
