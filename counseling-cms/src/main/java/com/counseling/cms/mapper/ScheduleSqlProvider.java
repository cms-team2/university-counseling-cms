package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Param;

public class ScheduleSqlProvider {

	public String searchSchedule(@Param("search_type") String search_type,
			@Param("search_type") String search_value,
			@Param("search_type") String status) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT s.FLNM, s.STDNT_NO, d.APLY_NO, r.C_CD_CLSF_NM, "
				+ "s.STDNT_EML, s.STDNT_TELNO, d.DSCSN_RSVT_YMD "
				+ "FROM DSCSN_APLY_INFO d "
				+ "JOIN STDNT_INFO s ON d.STDNT_NO = s.STDNT_NO "
				+ "JOIN DSCSN_RSLT r ON d.APLY_NO = r.APLY_NO "
				+ "JOIN EMP_INFO e ON d.EMP_NO = e.EMP_NO "
				+ "WHERE d.DSCSN_YN='Y'	AND ");
		
        //검색 유형 조건
        if("student".equals(search_type)) {
        	sql.append("s.FLNM LIKE CONCAT('%', #{search_value}, '%')");
        }else if("counselor".equals(search_type)) {
        	sql.append("e.FLNM LIKE CONCAT('%', #{search_value}, '%')");
        }
        
        //정렬 조건 추가
        if (status == null || status.isEmpty() || status.equals("counseling_date")) {
            // 기본 상태로 초기화 (사번으로 정렬 또는 원하는 초기 정렬 기준을 설정)
            sql.append(" ORDER BY DSCSN_RSVT_YMD DESC"); // 기본 정렬 (상담예정일)
        } else if ("student_name".equals(status)) {
            sql.append(" ORDER BY FLNM ASC"); // 이름순 정렬
        }
        
        return sql.toString();
	}
}
