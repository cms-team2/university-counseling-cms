package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.counseling.cms.dto.AdminScheduleDto;

@Mapper
public interface AdminScheduleMapper {

	//스케줄
	@SelectProvider(type = ScheduleSqlProvider.class, method = "searchSchedule")
	@Results({
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentNumber", column = "STDNT_NO"),
		@Result(property = "applyNumber", column = "APLY_NO"),
		@Result(property = "counselingClassifyName", column = "C_SCLSF_NM"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentTelNumber", column = "STDNT_TELNO"),
		@Result(property = "counselingReservationDate", column = "DSCSN_RSVT_YMD")
	})
	List<AdminScheduleDto> selectSchedules(@Param("search_type") String search_type,
			@Param("search_value") String search_value,
			@Param("status") String status,
			@Param("pageno") Integer pageno,
			@Param("ea") Integer ea);

	
	//스케줄 개수
	@SelectProvider(type = ScheduleSqlProvider.class, method = "scheduleCount")
	int countSchedules(@Param("search_type") String search_type, @Param("search_value") String search_value);
	
	
}
