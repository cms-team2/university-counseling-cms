package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.CounselorScheduleDto;

@Mapper
public interface CounselorScheduleMapper {

    @Select("SELECT EMP_INFO.FLNM, DSCSN_APLY_INFO.DSCSN_RSVT_YMD, DSCSN_APLY_INFO.C_TYPE_NM "
    		+ "FROM DSCSN_APLY_INFO "
    		+ "JOIN EMP_INFO ON DSCSN_APLY_INFO.EMP_NO = EMP_INFO.EMP_NO "
    		+ "WHERE EMP_INFO.FLNM = #{name} AND DSCSN_APLY_INFO.DSCSN_YN = 'Y'")
    @Results({
    	@Result(property = "counselorName", column = "FLNM"),                // 상담사 이름
    	@Result(property = "reservationDate", column = "DSCSN_RSVT_YMD"),    // 상담 예약 날짜
    	@Result(property = "counselingType", column = "C_TYPE_NM")          // 상담 방식	
    })
    List<CounselorScheduleDto> findSchedulesByCounselorName(@Param("name") String name);
}
