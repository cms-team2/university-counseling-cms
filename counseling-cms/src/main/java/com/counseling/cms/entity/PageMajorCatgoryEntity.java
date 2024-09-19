package com.counseling.cms.entity;

import com.counseling.cms.dto.PageMajorCategoryDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageMajorCatgoryEntity {
	String menuCode,menuName,menuSection,menuYn,menuUrl;
	int menuSequence;
	
	public PageMajorCatgoryEntity() {
		
	}
	
	public void pageMarjorCategoryEntityCk(PageMajorCategoryDto pageMajorCategoryDto,String checkValue) {
		this.setMenuCode(pageMajorCategoryDto.getMenuCode());
		this.setMenuName(pageMajorCategoryDto.getMenuName());
		this.setMenuSection(pageMajorCategoryDto.getMenuSection());
		this.setMenuSequence(pageMajorCategoryDto.getMenuSequence());
		this.setMenuYn(checkValue);
		this.setMenuUrl(pageMajorCategoryDto.getMenuUrl());
	}
}
