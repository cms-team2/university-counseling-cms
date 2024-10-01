package com.counseling.cms.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainMajorCategoryEntity {
	private String majorCategoryCode, majorCategoryName, pageCode, majorUrlAddress;
	private int exposureSequence;
}
