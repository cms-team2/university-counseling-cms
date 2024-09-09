package com.counseling.cms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.Stdnt_Dscsn_join_dto;
import com.counseling.cms.repo.Dscsn_Aply_Info_Repo;

@Service
public class adminApplyServiceImpl implements adminApplyService{
	
	@Autowired
	private Dscsn_Aply_Info_Repo dair;
	
	@Override
	public List<Stdnt_Dscsn_join_dto> dscsn_Aply_List(String keyword,String type) {
		List<Stdnt_Dscsn_join_dto> list= dair.dscsn_Aply_List(keyword,type); 
		return list;
	}
	
}
