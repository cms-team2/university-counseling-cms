package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.CounselingStatisticsDto;

@Mapper
public interface StatisticsMapper {
	
    @Select("SELECT C_CD_CLSF_NM, COUNT(*) AS counselCount " +
            "FROM DSCSN_RSLT " +
            "GROUP BY C_CD_CLSF_NM")
    @Results({
        @Result(property = "counselCategory", column = "C_CD_CLSF_NM"),  // 상담 유형을 counselCategory로 매핑
        @Result(property = "counselCount", column = "counselCount")      // 상담 횟수를 counselCount로 매핑
    })
    List<CounselingStatisticsDto> selectCounselingStatistics();
}
