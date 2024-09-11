package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.counseling.cms.dto.StdntDscsnJoinDto;
import com.counseling.cms.entity.ApplyListEntity;
import com.counseling.cms.entity.CounslerListEntity;
import com.counseling.cms.mapper.DscsnAplyInfoRepo;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

@Repository("admin_apply_module")
public class adminApplyService2 {
	
	@Autowired
	private DscsnAplyInfoRepo dr;
	

	public List<StdntDscsnJoinDto> apply_list(String keyword,String type){
		List<StdntDscsnJoinDto> list =null;

		if(type!=null) {
		if(type.equals("FLNM")) {
			list=dr.selectByFlNM(keyword);
		}else if(type.equals("STDNT_NO")){
			list=dr.selectByStdntNo(keyword);
		}}
		else {
			list=dr.selectByList();			
		}
		
		return list;
	}
	
	public Map<String, Object> getCounslerList(String stdnt_id){
			ApplyListEntity details= dr.getApplyListMapper(stdnt_id);
			List<CounslerListEntity> call =new ArrayList<CounslerListEntity>();
		List<String> data = dr.getCounslerList(details.getDscsnRsvtYmd());
		if(data.size()>0) {
			if(details.getCSclsfNm().equals("교수상담")) {
				call=dr.getProfessor(data);
			}else if(!details.getCSclsfNm().equals("교수상담")) {
				call=dr.getCounsler(data); 
			}
		}else {
			call=dr.getCounslerAll();
		}
		
		Map<String, Object> allData=new HashMap<String, Object>();
		allData.put("details", details);
		allData.put("counsler", call);
		return allData;
	}
	
	public String CounslerAllotment(String empNo,String stdntNo,String dscsnRsvtYmd) {
		String result="";
		try {
		int callback =dr.putDscsnAllotment(empNo,stdntNo,dscsnRsvtYmd);
		if(callback>0) {
			result="ok";
		}else {
			result="no";
		}}catch(Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
}
