package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Param;

public class CounselorSqlProvider {
	
	public String searchCounselors(@Param("searchType") String searchType,
								   @Param("searchKeyword") String searchKeyword,
								   @Param("counselCategory") String counselCategory,
								   @Param("status") String status) {
		StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.EMP_NO, e.FLNM, d.DEPT_NM, e.EMP_SE_NM, e.EMP_EML, e.EMP_TELNO, e.APNTMN_YMD, c.C_SCLSF_NM ");
        sql.append("FROM EMP_INFO e ");
        sql.append("JOIN DEPT_INFO d ON e.DEPT_NO = d.DEPT_NO ");
        sql.append("JOIN CNSLR_INFO c ON e.EMP_NO = c.EMP_NO ");
        
        
        boolean whereAdded = false; // WHERE 절이 추가되었는지 확인
        
        //검색 유형 조건
        if("name".equals(searchType)) {
        	sql.append("WHERE e.FLNM LIKE CONCAT('%', #{searchKeyword}, '%')");
        	whereAdded = true;
        }else if("facultyNumber".equals(searchType)) {
        	sql.append("WHERE e.EMP_NO LIKE CONCAT('%', #{searchKeyword}, '%')");
        	whereAdded = true;
        }
        
        // 상담분류 필터
        if (counselCategory != null && !counselCategory.isEmpty()) {
            if (whereAdded) {
                sql.append("AND c.C_SCLSF_NM = #{counselCategory} ");
            } else {
                sql.append("WHERE c.C_SCLSF_NM = #{counselCategory} ");
                whereAdded = true;
            }
        }
        
        //정렬 조건 추가
        if ("orderByName".equals(status)) {
            sql.append("ORDER BY e.FLNM ASC ");
        } else if ("orderBydate".equals(status)) {
            sql.append("ORDER BY e.APNTMN_YMD DESC ");
        }       
  
        return sql.toString();
	}
}
