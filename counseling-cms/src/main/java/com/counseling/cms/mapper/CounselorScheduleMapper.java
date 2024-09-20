package com.counseling.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.counseling.cms.dto.CounselorScheduleDto;

@Mapper
public interface CounselorScheduleMapper {

    // 특정 상담사의 월별 일정 조회
    @Select("SELECT b.FLNM AS counselorName, " +
            "a.DSCSN_RSVT_YMD AS reservationDate " +
            "FROM DSCSN_APLY_INFO a " +
            "JOIN EMP_INFO b ON a.EMP_NO = b.EMP_NO " +
            "WHERE a.DSCSN_YN = 'Y' " +
            "AND b.FLNM = #{name} " +
            "AND DATE_FORMAT(a.DSCSN_RSVT_YMD, '%Y-%m') = #{month}")
    List<CounselorScheduleDto> getMonthlySchedules(@Param("name") String name, @Param("month") String month);

    // 전체 상담사의 월별 일정 조회
    @Select("SELECT b.FLNM AS counselorName, " +
            "a.DSCSN_RSVT_YMD AS reservationDate " +
            "FROM DSCSN_APLY_INFO a " +
            "JOIN EMP_INFO b ON a.EMP_NO = b.EMP_NO " +
            "WHERE a.DSCSN_YN = 'Y' " +
            "AND DATE_FORMAT(a.DSCSN_RSVT_YMD, '%Y-%m') = #{month}")
    List<CounselorScheduleDto> getAllMonthlySchedules(@Param("month") String month);
}