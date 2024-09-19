package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.entity.counselingRecordEntity;

@Mapper
public interface CounseleeListMapper {
	
	@Select("SELECT COUNT(*) AS CTN FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId}")
	int counseleeListCount(String counselorId);
	
	@Select("SELECT COUNT(*) AS CTN FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND FLNM LIKE CONCAT('%',#{searchValue},'%')")
	int counseleeListSearchCount(String counselorId, String searchValue);
	
	@Select("SELECT COUNT(*) AS CTN FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND C_PRGRS_YN=#{category}")
	int counseleeListCateCount(String counselorId, String category);
	
	@Select("SELECT COUNT(*) AS CTN FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND C_PRGRS_YN=#{category} AND FLNM LIKE CONCAT('%',#{searchValue},'%')")
	int counseleeListCateSearchCount(String counselorId, String searchValue, String category);
	
	@Select("SELECT * FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
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
		@Result(property = "consultationCategory", column = "C_SCLSF_NM"),
		@Result(property = "applyContent", column = "DSCSN_APLY_CN"),
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentGender", column = "GNDR"),
		@Result(property = "studentTelNo", column = "STDNT_TELNO"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentMajor", column = "DEPT_NM")
	})
	List<CounseleeListEntity> getCounseleeList(String counselorId, int start, int listCount);
	
	@Select("SELECT * FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND FLNM LIKE CONCAT('%',#{searchValue},'%') ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
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
		@Result(property = "consultationCategory", column = "C_SCLSF_NM"),
		@Result(property = "applyContent", column = "DSCSN_APLY_CN"),
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentGender", column = "GNDR"),
		@Result(property = "studentTelNo", column = "STDNT_TELNO"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentMajor", column = "DEPT_NM")
	})
	List<CounseleeListEntity> getCounseleeSearchList(String counselorId, int start, int listCount, String searchValue);
	
	@Select("SELECT * FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND C_PRGRS_YN=#{category} ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
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
		@Result(property = "consultationCategory", column = "C_SCLSF_NM"),
		@Result(property = "applyContent", column = "DSCSN_APLY_CN"),
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentGender", column = "GNDR"),
		@Result(property = "studentTelNo", column = "STDNT_TELNO"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentMajor", column = "DEPT_NM")
	})
	List<CounseleeListEntity> getCounseleeCateList(String counselorId, int start, int listCount, String category);
	
	@Select("SELECT * FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND C_PRGRS_YN=#{category} AND FLNM LIKE CONCAT('%',#{searchValue},'%') ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
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
		@Result(property = "consultationCategory", column = "C_SCLSF_NM"),
		@Result(property = "applyContent", column = "DSCSN_APLY_CN"),
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentGender", column = "GNDR"),
		@Result(property = "studentTelNo", column = "STDNT_TELNO"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentMajor", column = "DEPT_NM")
	})
	List<CounseleeListEntity> getCounseleeCateSearchList(String counselorId, int start, int listCount, String searchValue, String category);
	
	@Select("SELECT * FROM VIEW_COUNSELEE_LIST WHERE EMP_NO=#{counselorId} AND APLY_NO=#{applyNo}")
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
		@Result(property = "consultationCategory", column = "C_SCLSF_NM"),
		@Result(property = "applyContent", column = "DSCSN_APLY_CN"),
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentGender", column = "GNDR"),
		@Result(property = "studentTelNo", column = "STDNT_TELNO"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentMajor", column = "DEPT_NM")
	})
	CounseleeListEntity getApplyView(String counselorId, int applyNo);
	
	@Select("SELECT COUNT(*) AS CTN FROM DSCSN_RSLT WHERE APLY_NO=#{applyNo}")
	int counselingRrcordCount(int applyNO);
	
	@Select("SELECT * FROM DSCSN_RSLT WHERE APLY_NO=#{applyNo}")
	@Results({
		@Result(property = "recordNo", column = "DSCSN_END_NO"),
		@Result(property = "applyNo", column = "APLY_NO"),
		@Result(property = "studentNo", column = "STDNT_NO"),
		@Result(property = "counselorNo", column = "EMP_NO"),
		@Result(property = "consultationDate", column = "DSCSN_DT"),
		@Result(property = "consultationContent", column = "DSCSN_CN"),
		@Result(property = "consultationCategory", column = "C_SCLSF_NM"),
	})
	counselingRecordEntity getcounselingRecord(int applyNo);
}
