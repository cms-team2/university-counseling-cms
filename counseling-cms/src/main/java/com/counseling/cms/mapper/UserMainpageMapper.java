package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.MainMajorCategoryEntity;
import com.counseling.cms.entity.MainSubmenuCategoryEntity;


@Mapper
public interface UserMainpageMapper {
	
	@Select("SELECT LCLSF_CD, LCLSF_NM, PG_SE_NM, EXPSR_SEQ, URL_ADDR "
			+ "FROM MAJOR_CATEGORY "
			+ "WHERE EXPSR_YN='N' AND PG_SE_NM=#{code} "
			+ "ORDER BY EXPSR_SEQ")
	@Results({
		@Result(property = "majorCategoryCode", column = "LCLSF_CD"),
		@Result(property = "majorCategoryName", column = "LCLSF_NM"),
		@Result(property = "pageCode", column = "PG_SE_NM"),
		@Result(property = "exposureSequence", column = "EXPSR_SEQ"),
		@Result(property = "majorUrlAddress", column = "URL_ADDR")
	})
	List<MainMajorCategoryEntity> getMajorMenu(String code);
	
	@Select("SELECT LCLSF_CD, SUB_LCLSF_CD, SUB_LCLSF_NM, SUB_EXPSR_SEQ, URL_ADDR, SUB_EXPLN "
			+ "FROM SUBMENU_CATEGORY "
			+ "WHERE SUB_EXPSR_YN='N' "
			+ "ORDER BY SUB_EXPSR_SEQ")
	@Results({
		@Result(property = "majorCategoryCode", column = "LCLSF_CD"),
		@Result(property = "submenuCategoryCode", column = "SUB_LCLSF_CD"),
		@Result(property = "submenuCategoryName", column = "SUB_LCLSF_NM"),
		@Result(property = "submenuExposureSequence", column = "SUB_EXPSR_SEQ"),
		@Result(property = "submenuUrlAddress", column = "URL_ADDR"),
		@Result(property = "submenuCategoryExplain", column = "SUB_EXPLN")
	})
	List<MainSubmenuCategoryEntity> getSubmenu();

}
