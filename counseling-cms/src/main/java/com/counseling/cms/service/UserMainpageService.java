package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.MainMajorCategoryEntity;
import com.counseling.cms.entity.MainSubmenuCategoryEntity;
import com.counseling.cms.mapper.UserMainpageMapper;

@Service
public class UserMainpageService {

	@Autowired
	private UserMainpageMapper mainpageMapper;
	
	public UserMainpageService(UserMainpageMapper mainpageMapper) {
		this.mainpageMapper = mainpageMapper;
	}
	
	public List<MainMajorCategoryEntity> getMajorMenuCategory(String code){
		
		if(code.equals("C")) {
			code="2";
		}
		else {
			code="3";
		}
		
		return mainpageMapper.getMajorMenu(code);
	}
	
	public List<MainSubmenuCategoryEntity> getSubMenuCategory(){
		return mainpageMapper.getSubmenu();
	}
}
