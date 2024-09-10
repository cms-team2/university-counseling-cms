package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.AdminScheduleDto;

@Mapper
public interface AdminScheduleMapper {

	//스케줄 개수
	@Select("SELECT COUNT(*) FROM DSCSN_APLY_INFO WHERE DSCSN_YN='Y' ORDER BY DSCSN_RSVT_YMD DESC")
	int countApply();
	
	//전체 스케줄
	@Select("SELECT s.FLNM, s.STDNT_NO, d.APLY_NO, r.C_CD_CLSF_NM, "
			+ "s.STDNT_EML, s.STDNT_TELNO, d.DSCSN_RSVT_YMD "
			+ "FROM DSCSN_APLY_INFO d "
			+ "JOIN STDNT_INFO s ON d.STDNT_NO = s.STDNT_NO "
			+ "JOIN DSCSN_RSLT r ON d.APLY_NO = r.APLY_NO "
			+ "WHERE d.DSCSN_YN='Y' ORDER BY #{status} DESC")
	@Results({
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentNumber", column = "STDNT_NO"),
		@Result(property = "applyNumber", column = "APLY_NO"),
		@Result(property = "counselingClassify", column = "C_CD_CLSF_NM"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentTelNumber", column = "STDNT_TELNO"),
		@Result(property = "counselingReservationDate", column = "DSCSN_RSVT_YMD")
	})
	List<AdminScheduleDto> selectSchedules(String status);

	
	
	
}
