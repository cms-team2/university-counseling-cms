package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.Dscsn_Aply_Info_dto;
import com.counseling.cms.repo.Dscsn_Aply_Info_Repo;

@Service
public class adminApplyServiceImpl implements adminApplyService{
	
	@Autowired
	private Dscsn_Aply_Info_Repo dair;
	
	@Override
	public List<Dscsn_Aply_Info_dto> dscsn_Aply_List(String keyword,String type) {
		List<Dscsn_Aply_Info_dto> list= dair.dscsn_Aply_List(keyword,type); 
		return list;
	}
	
}
