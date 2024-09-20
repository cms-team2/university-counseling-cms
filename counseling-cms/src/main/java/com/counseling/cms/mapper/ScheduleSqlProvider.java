package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Param;

public class ScheduleSqlProvider {

	public String searchSchedule(@Param("search_type") String search_type,
			@Param("search_value") String search_value,
			@Param("status") String status,
			@Param("pageno") Integer pageno,
			@Param("ea") Integer ea) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT s.FLNM, s.STDNT_NO, d.APLY_NO, c.C_SCLSF_NM, "
				+ "s.STDNT_EML, s.STDNT_TELNO, d.DSCSN_RSVT_YMD "
				+ "FROM DSCSN_APLY_INFO AS d "
				+ "JOIN STDNT_INFO AS s ON d.STDNT_NO = s.STDNT_NO "
				+ "JOIN DSCSN_CATEGORY AS c ON d.C_SCLSF_CD = c.C_SCLSF_CD "
				+ "JOIN EMP_INFO AS e ON d.EMP_NO = e.EMP_NO "
				+ "WHERE d.DSCSN_YN='Y'	");
		
        //검색 유형 조건
		if(search_type==null || search_value==null || search_type.equals("") || search_value.equals("")) {
			
		}
		else if("student".equals(search_type)) {
        	sql.append("AND s.FLNM LIKE CONCAT('%', #{search_value}, '%') ");
        }else if("counselor".equals(search_type)) {
        	sql.append("AND e.FLNM LIKE CONCAT('%', #{search_value}, '%') ");
        }
        
        //정렬 조건 추가
        if (status == null || status.equals("")) {
        }else if(status.equals("counseling_date")) {
        	sql.append("ORDER BY DSCSN_RSVT_YMD DESC "); //상담예정일 내림차순 정렬
        }
        else if ("student_name".equals(status)) {
            sql.append("ORDER BY FLNM ASC "); // 이름순 정렬
        }
        
        //페이지 보이기 조건 추가
        if(pageno==null) {
        	sql.append("LIMIT 0,${ea}");
        }
        else if(pageno==1) {
        	sql.append("LIMIT 0,${ea}");
        }
        else {
        	sql.append("LIMIT ${(pageno-1)*ea},${ea}");
        }
        
        return sql.toString();
	}
	
	public String scheduleCount(@Param("search_type") String search_type,
			@Param("search_value") String search_value) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) "
				+ "FROM DSCSN_APLY_INFO AS d "
				+ "JOIN STDNT_INFO AS s ON d.STDNT_NO = s.STDNT_NO "
				+ "JOIN DSCSN_CATEGORY AS c ON d.C_SCLSF_CD = c.C_SCLSF_CD "
				+ "JOIN EMP_INFO AS e ON d.EMP_NO = e.EMP_NO "
				+ "WHERE d.DSCSN_YN='Y'	");
		
        //검색 유형 조건
		if(search_type==null || search_value==null || search_type.equals("") || search_value.equals("")) {
			
		}
		else if("student".equals(search_type)) {
        	sql.append("AND s.FLNM LIKE CONCAT('%', #{search_value}, '%') ");
        }else if("counselor".equals(search_type)) {
        	sql.append("AND e.FLNM LIKE CONCAT('%', #{search_value}, '%') ");
        }

		return sql.toString();
	}
}
