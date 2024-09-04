package com.counseling.cms.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.counseling.cms.mapper.CounselorMapper;
import cms.counseling.cms.dto.CounselorDto;

@Service
public class CounselorService {

	private final CounselorMapper counselorMapper;
	
	public CounselorService(CounselorMapper counselorMapper) {
		this.counselorMapper = counselorMapper;
	}
	
	public List<CounselorDto> getCounselorList(){
		return counselorMapper.selectCounselors();
	}
}
