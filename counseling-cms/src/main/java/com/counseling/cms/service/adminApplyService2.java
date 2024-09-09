package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.counseling.cms.dto.Dscsn_Aply_Info_dto;
import com.counseling.cms.dto.Stdnt_Dscsn_join_dto;

@Repository("admin_apply_module")
public class adminApplyService2 {
	
	@Autowired
	private adminApplyService aas;
	

	public List<Stdnt_Dscsn_join_dto> apply_list(String keyword,String type){
		ArrayList<String> al = new ArrayList<String>();
		al.add(type);
		al.add(keyword);
		List<Stdnt_Dscsn_join_dto> list =aas.dscsn_Aply_List(keyword,type);
		return list;
	}
	
}
