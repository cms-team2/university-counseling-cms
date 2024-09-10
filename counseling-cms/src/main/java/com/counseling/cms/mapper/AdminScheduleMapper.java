package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.counseling.cms.dto.AdminScheduleDto;

@Mapper
public interface AdminScheduleMapper {

	//전체 스케줄
	@SelectProvider(type = ScheduleSqlProvider.class, method = "searchSchedule")
	@Results({
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentNumber", column = "STDNT_NO"),
		@Result(property = "applyNumber", column = "APLY_NO"),
		@Result(property = "counselingClassify", column = "C_CD_CLSF_NM"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentTelNumber", column = "STDNT_TELNO"),
		@Result(property = "counselingReservationDate", column = "DSCSN_RSVT_YMD")
	})
	List<AdminScheduleDto> selectSchedules();

	
	
	
}
