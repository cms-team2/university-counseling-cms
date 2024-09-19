package com.counseling.cms.entity;

import com.counseling.cms.dto.PageMajorCategoryDto;
import com.counseling.cms.dto.PageSubCategoryDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSubCategoryEntity {
	String menuCode,majorMenuCode,menuName,menuYn,menuUrl,menuDescription;
	int menuSequence;
	
	public PageSubCategoryEntity() {
		
	}
	
	public void PageSubCategoryEntityCk(PageSubCategoryDto pageSubCategoryDto,String checkValue) {
		this.setMenuCode(pageSubCategoryDto.getMenuCode());
		this.setMajorMenuCode(pageSubCategoryDto.getMajorMenuCode());
		this.setMenuName(pageSubCategoryDto.getMenuName());
		this.setMenuSequence(pageSubCategoryDto.getMenuSequence());
		this.setMenuYn(checkValue);
		this.setMenuUrl(pageSubCategoryDto.getMenuUrl());
		this.setMenuDescription(pageSubCategoryDto.getMenuDescription());
	}
}
