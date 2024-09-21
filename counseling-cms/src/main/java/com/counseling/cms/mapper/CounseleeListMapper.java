package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.CounselingRecordDto;
import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.entity.CounselingRecordEntity;

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
		@Result(property = "recordTitle", column = "DSCSN_TTL"),
		@Result(property = "writeDate", column = "DSCSN_RSLT_YDM")
	})
	CounselingRecordEntity getcounselingRecord(int applyNo);
	
	@Select("SELECT FLNM FROM EMP_INFO WHERE EMP_NO=#{counselorId}")
	String getCounselorName(String counselorId);
	
	@Select("SELECT NOW()")
	String getToday();
	
	@Select("SELECT COUNT(*) FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId}")
	int counselingRecordListCount(String counselorId);
	
	@Select("SELECT COUNT(*) FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} AND FLNM LIKE CONCAT('%',#{searchValue},'%')")
	int counselingRecordListSearchCount(String counselorId, String searchValue);
	
	@Select("SELECT COUNT(*) FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} AND C_CD_CLSF_NM=#{category}")
	int counselingRecordListCateCount(String counselorId, String category);
	
	@Select("SELECT COUNT(*) FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} AND C_CD_CLSF_NM=#{category} AND FLNM LIKE CONCAT('%',#{searchValue},'%')")
	int counselingRecordListCateSearchCount(String counselorId, String searchValue, String category);
	
	@Select("SELECT * FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
	@Results({
		@Result(property = "recordNo", column = "DSCSN_END_NO"),
		@Result(property = "applyNo", column = "APLY_NO"),
		@Result(property = "studentNo", column = "STDNT_NO"),
		@Result(property = "counselorNo", column = "EMP_NO"),
		@Result(property = "consultationDate", column = "DSCSN_DT"),
		@Result(property = "consultationContent", column = "DSCSN_CN"),
		@Result(property = "consultationCategory", column = "C_CD_CLSF_NM"),
		@Result(property = "recordTitle", column = "DSCSN_TTL"),
		@Result(property = "writeDate", column = "DSCSN_RSLT_YDM"),
		@Result(property = "studentName", column = "FLNM")
	})
	List<CounselingRecordEntity> getCounselingRecordList(String counselorId, int start, int listCount);
	
	@Select("SELECT * FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} AND FLNM LIKE CONCAT('%',#{searchValue},'%') ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
	@Results({
		@Result(property = "recordNo", column = "DSCSN_END_NO"),
		@Result(property = "applyNo", column = "APLY_NO"),
		@Result(property = "studentNo", column = "STDNT_NO"),
		@Result(property = "counselorNo", column = "EMP_NO"),
		@Result(property = "consultationDate", column = "DSCSN_DT"),
		@Result(property = "consultationContent", column = "DSCSN_CN"),
		@Result(property = "consultationCategory", column = "C_CD_CLSF_NM"),
		@Result(property = "recordTitle", column = "DSCSN_TTL"),
		@Result(property = "writeDate", column = "DSCSN_RSLT_YDM"),
		@Result(property = "studentName", column = "FLNM")
	})
	List<CounselingRecordEntity> getCounselingRecordSearchList(String counselorId, int start, int listCount, String searchValue);
	
	@Select("SELECT * FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} AND C_CD_CLSF_NM=#{category} ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
	@Results({
		@Result(property = "recordNo", column = "DSCSN_END_NO"),
		@Result(property = "applyNo", column = "APLY_NO"),
		@Result(property = "studentNo", column = "STDNT_NO"),
		@Result(property = "counselorNo", column = "EMP_NO"),
		@Result(property = "consultationDate", column = "DSCSN_DT"),
		@Result(property = "consultationContent", column = "DSCSN_CN"),
		@Result(property = "consultationCategory", column = "C_CD_CLSF_NM"),
		@Result(property = "recordTitle", column = "DSCSN_TTL"),
		@Result(property = "writeDate", column = "DSCSN_RSLT_YDM"),
		@Result(property = "studentName", column = "FLNM")
	})
	List<CounselingRecordEntity> getCounselingRecordCateList(String counselorId, int start, int listCount, String category);
	
	@Select("SELECT * FROM VIEW_COUNSELING_RECORD WHERE EMP_NO=#{counselorId} AND C_CD_CLSF_NM=#{category} AND FLNM LIKE CONCAT('%',#{searchValue},'%') ORDER BY APLY_NO DESC LIMIT #{start},#{listCount}")
	@Results({
		@Result(property = "recordNo", column = "DSCSN_END_NO"),
		@Result(property = "applyNo", column = "APLY_NO"),
		@Result(property = "studentNo", column = "STDNT_NO"),
		@Result(property = "counselorNo", column = "EMP_NO"),
		@Result(property = "consultationDate", column = "DSCSN_DT"),
		@Result(property = "consultationContent", column = "DSCSN_CN"),
		@Result(property = "consultationCategory", column = "C_CD_CLSF_NM"),
		@Result(property = "recordTitle", column = "DSCSN_TTL"),
		@Result(property = "writeDate", column = "DSCSN_RSLT_YDM"),
		@Result(property = "studentName", column = "FLNM")
	})
	List<CounselingRecordEntity> getCounselingRecordCateSearchList(String counselorId, int start, int listCount, String searchValue, String category);
	
	@Insert("INSERT INTO DSCSN_RSLT VALUES ('0', #{applyNo}, #{studentNo}, #{counselorNo}, #{consultationDate}, #{recordContent}, #{consultationCategory}, #{recordTitle}, now())")
	int saveCounselingRecord(CounselingRecordDto counselingRecordDto);
	
	@Update("UPDATE DSCSN_APLY_INFO SET C_PRGRS_YN='C' WHERE APLY_NO=#{applyNo}")
	int updateCounselingProgress(String applyNo);
	
	@Update("UPDATE DSCSN_RSLT SET  DSCSN_TTL=#{recordTitle}, DSCSN_CN=#{recordContent} WHERE APLY_NO=#{applyNo}")
	int modifyCounselingRecord(CounselingRecordDto counselingRecordDto);
	
}
