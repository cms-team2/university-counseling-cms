package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.CounselingStatisticsDto;

@Mapper
public interface StatisticsMapper {
	
	//성별 상담횟수 통계
    @Select("SELECT r.C_CD_CLSF_NM, s.GNDR, COUNT(*) AS counselCount "
    		+ "FROM DSCSN_RSLT r "
    		+ "JOIN STDNT_INFO s ON r.STDNT_NO = s.STDNT_NO "
    		+ "GROUP BY r.C_CD_CLSF_NM, s.GNDR")
    @Results({
        @Result(property = "counselCategory", column = "C_CD_CLSF_NM"),  // 컬럼명 C_CD_CLSF_NM을 counselCategory로 매핑
        @Result(property = "gender", column = "GNDR"),                   // 컬럼명 GNDR을 gender로 매핑
        @Result(property = "counselCount", column = "counselCount")      // counselCount는 그대로 사용
    })
    List<CounselingStatisticsDto> selectCounselingStatisticsByGender();
    
    //년도별 상담횟수 통계
    @Select("SELECT YEAR(r.DSCSN_DT), r.C_CD_CLSF_NM, COUNT(*) AS counselCount "
            + "FROM DSCSN_RSLT r "
            + "WHERE YEAR(r.DSCSN_DT) IN (2022, 2023, 2024) "
            + "GROUP BY YEAR(r.DSCSN_DT), r.C_CD_CLSF_NM "
            + "ORDER BY YEAR(r.DSCSN_DT) ASC")
    @Results({
        @Result(property = "year", column = "YEAR(r.DSCSN_DT)"),
        @Result(property = "counselCategory", column = "C_CD_CLSF_NM"),
        @Result(property = "counselCount", column = "counselCount")
    })
    List<CounselingStatisticsDto> selectCounselingStatisticsByYear();

    // 월별 상담 통계
    @Select("SELECT DATE_FORMAT(C_APLY_DT, '%Y-%m') AS applyMonth, "
          + "CASE "
          + "  WHEN c.LCLSF_CD = 'M3010' THEN '심리상담' "
          + "  WHEN c.LCLSF_CD = 'M3011' THEN '학업상담' "
          + "  WHEN c.LCLSF_CD = 'M3012' THEN '기타상담' "
          + "END AS counselName, "
          + "COUNT(*) AS counselCount "
          + "FROM DSCSN_APLY_INFO a "
          + "JOIN DSCSN_CATEGORY c ON a.C_SCLSF_CD = c.C_SCLSF_CD "
          + "GROUP BY DATE_FORMAT(C_APLY_DT, '%Y-%m'), c.LCLSF_CD")
    List<CounselingStatisticsDto> selectCounselingStatisticsByMonth();
    
    // 일별 상담 통계 쿼리
    @Select("SELECT DATE_FORMAT(C_APLY_DT, '%Y-%m-%d') AS applyDate, "
    	      + "SUM(CASE WHEN c.LCLSF_CD = 'M3010' THEN 1 ELSE 0 END) AS psychologyCount, "
    	      + "SUM(CASE WHEN c.LCLSF_CD = 'M3011' THEN 1 ELSE 0 END) AS academicCount, "
    	      + "SUM(CASE WHEN c.LCLSF_CD = 'M3012' THEN 1 ELSE 0 END) AS otherCount "
    	      + "FROM DSCSN_APLY_INFO a "
    	      + "JOIN DSCSN_CATEGORY c ON a.C_SCLSF_CD = c.C_SCLSF_CD "
    	      + "WHERE DATE_FORMAT(C_APLY_DT, '%Y-%m') = #{selectedMonth} "
    	      + "GROUP BY applyDate "
    	      + "ORDER BY applyDate")
    List<CounselingStatisticsDto> selectCounselingStatisticsByDay(@Param("selectedMonth") String selectedMonth);

}    


