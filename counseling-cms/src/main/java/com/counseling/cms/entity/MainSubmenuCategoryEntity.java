package com.counseling.cms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainSubmenuCategoryEntity {
	private String majorCategoryCode, submenuCategoryCode, submenuCategoryName, submenuUrlAddress, submenuCategoryExplain;
	private int submenuExposureSequence;
}
