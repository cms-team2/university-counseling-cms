package com.counseling.cms.service;

import java.util.List;

import com.counseling.cms.dto.Stdnt_Dscsn_join_dto;

public interface adminApplyService {

	public List<Stdnt_Dscsn_join_dto> dscsn_Aply_List(String keyword,String type);
	
}
