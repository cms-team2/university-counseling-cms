package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.CounseleeListEntity;

@Mapper
public interface CounseleeListMapper {
	
	@Select("SELECT * FROM COUNSELEE_LIST WHERE EMP_NO=#{counselorId}")
	@Results({
		@Result(property = "applyNo", column = "APLY_NO"),
		@Result(property = "studentNo", column = "STDNT_NO"),
		@Result(property = "applyDate", column = "C_APLY_DT"),
		@Result(property = "consultationDate", column = "DSCSN_RSVT_YMD"),
		@Result(property = "applicationStatus", column = "DSCSN_YN"),
		@Result(property = "counselorNo", column = "EMP_NO"),
		@Result(property = "progressStatus", column = "C_PRGRS_YN"),
		@Result(property = "fileNo", column = "FILE_NO"),
		@Result(property = "consultationWay", column = "C_TYPE_NM"),
		@Result(property = "consultationCode", column = "C_SCLSF_CD"),
		@Result(property = "applyContent", column = "DSCSN_APLY_CN"),
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentGender", column = "GNDR"),
		@Result(property = "studentTelNo", column = "STDNT_TELNO"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
	})
	List<CounseleeListEntity> getCounseleeList(String counselorId);
}
