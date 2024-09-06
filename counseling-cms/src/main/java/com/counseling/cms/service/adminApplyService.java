package com.counseling.cms.service;

import java.util.List;

import com.counseling.cms.dto.Dscsn_Aply_Info_dto;

public interface adminApplyService {

	public List<Dscsn_Aply_Info_dto> dscsn_Aply_List(String keyword,String type);
	
}
