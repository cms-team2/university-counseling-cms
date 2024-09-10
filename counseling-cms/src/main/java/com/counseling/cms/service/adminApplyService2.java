package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.counseling.cms.dto.StdntDscsnJoinDto;
import com.counseling.cms.entity.ApplyListEntity;
import com.counseling.cms.mapper.DscsnAplyInfoRepo;

@Repository("admin_apply_module")
public class adminApplyService2 {
	
	@Autowired
	private DscsnAplyInfoRepo dr;
	

	public List<StdntDscsnJoinDto> apply_list(String keyword,String type){
		ArrayList<String> al = new ArrayList<String>();
		al.add(type);
		al.add(keyword);
		List<StdntDscsnJoinDto> list =dr.dscsn_Aply_List(keyword,type);
		return list;
	}
	
	public ApplyListEntity Details(String stdnt_id){
		ApplyListEntity data= dr.getApplyListMapper(stdnt_id);
		return data;
	}
	
	public List<String> getCounslerList(String DscsnRsvtYmd ,String CSclsfNm){
		List<String> data = dr.getCounslerList(DscsnRsvtYmd);
		if(data.size()>0) {
			if(CSclsfNm=="교수상담") {
				for(String one : data) {
					String counsler =	dr.getCounsler(one);					
				}
			}else if(CSclsfNm!="교수상담") {
				
			}
		}
		return null;
	}
	
}
