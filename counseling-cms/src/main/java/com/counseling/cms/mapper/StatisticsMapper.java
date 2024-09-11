package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
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
            + "GROUP BY YEAR(r.DSCSN_DT), r.C_CD_CLSF_NM")
    @Results({
        @Result(property = "year", column = "YEAR(r.DSCSN_DT)"),
        @Result(property = "counselCategory", column = "C_CD_CLSF_NM"),
        @Result(property = "counselCount", column = "counselCount")
    })
    List<CounselingStatisticsDto> selectCounselingStatisticsByYear();
}
