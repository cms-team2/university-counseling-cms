package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.CounselingMenuDto;
import com.counseling.cms.mapper.CounselingApplyMapper;

@Service
public class CounselingApplyService {
	
	@Autowired
	private CounselingApplyMapper counselingApplyMapper;
	public List<CounselingMenuDto> getCounselingMenu() {
		return counselingApplyMapper.getCounselingMenuMapper();
	}
}
