package com.counseling.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.counseling.cms.dto.CounselorScheduleDto;

@Mapper
public interface CounselorScheduleMapper {

    @Select("SELECT DATE_FORMAT(a.DSCSN_RSVT_YMD, '%Y-%m') AS month, " +
            "DATE_FORMAT(a.DSCSN_RSVT_YMD, '%d') AS day, " +
            "b.FLNM AS counselorName, " +
            "a.DSCSN_RSVT_YMD AS reservationDate " +
            "FROM DSCSN_APLY_INFO a " +
            "JOIN EMP_INFO b ON a.EMP_NO = b.EMP_NO " +
            "WHERE a.DSCSN_YN = 'Y' " +
            "AND DATE_FORMAT(a.DSCSN_RSVT_YMD, '%Y-%m') = #{month}")
    List<CounselorScheduleDto> getMonthlySchedules(@Param("month") String month);
}
