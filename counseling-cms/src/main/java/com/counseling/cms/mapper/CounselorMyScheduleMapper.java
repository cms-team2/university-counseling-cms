package com.counseling.cms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.entity.CounseleeListEntity;

@Mapper
public interface CounselorMyScheduleMapper {
	@Select("SELECT "
	        + "APLY_NO, FLNM, STDNT_NO, DEPT_NM, STDNT_TELNO, DSCSN_RSVT_YMD, C_SCLSF_NM,C_TYPE_NM "
	        + "FROM VIEW_COUNSELEE_LIST "
	        + "WHERE EMP_NO = #{counselor} "
	        + "AND DSCSN_RSVT_YMD BETWEEN #{startdate} AND #{endDate}")
	@Results({
		@Result(property="applyNo",column="APLY_NO"),
		@Result(property="studentName",column="FLNM"),
		@Result(property="studentNo",column="STDNT_NO"),
		@Result(property="studentMajor",column="DEPT_NM"),
		@Result(property="studentTelNo",column="STDNT_TELNO"),
		@Result(property="consultationDate",column="DSCSN_RSVT_YMD"),
		@Result(property="consultationCategory",column="C_SCLSF_NM"),
		@Result(property="consultationWay",column="C_TYPE_NM")
	})
	List<CounseleeListEntity> counsellor_listall(String counselor,String startdate,String endDate);
	
    @Update("UPDATE DSCSN_APLY_INFO SET "
            + "DSCSN_RSVT_YMD = #{newDate} "
            + "WHERE APLY_NO = #{applyNo}")
    Integer update_dscsninfo(String newDate, Integer applyNo);
	
}
