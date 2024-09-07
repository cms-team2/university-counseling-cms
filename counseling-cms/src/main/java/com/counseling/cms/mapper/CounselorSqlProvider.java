package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Param;

public class CounselorSqlProvider {
	
	public String searchCounselors(@Param("searchType") String searchType,
								   @Param("searchKeyword") String searchKeyword,
								   @Param("counselCategory") String counselCategory,
								   @Param("status") String status) {
		StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.EMP_NO, e.FLNM, d.DEPT_NM, e.EMP_SE_NM, e.EMP_EML, e.EMP_TELNO, e.APNTMN_YMD, c.C_SCLSF_NM ");
        sql.append("f.FILE_PATH, f.FILE_NM "); //파일 경로와 파일 이름 추가
        sql.append("FROM EMP_INFO e ");
        sql.append("JOIN DEPT_INFO d ON e.DEPT_NO = d.DEPT_NO ");
        sql.append("JOIN CNSLR_INFO c ON e.EMP_NO = c.EMP_NO ");
        sql.append("LEFT JOIN COM_FILE f ON e.FILE_NO = f.FILE_NO "); // FILE_NO로 조인
        
        
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
        if (status == null || status.isEmpty()) {
            // 기본 상태로 초기화 (사번으로 정렬 또는 원하는 초기 정렬 기준을 설정)
            sql.append(" ORDER BY EMP_NO ASC"); // 기본 정렬 (사번)
        } else if ("orderByName".equals(status)) {
            sql.append(" ORDER BY FLNM ASC"); // 이름순 정렬
        } else if ("orderBydate".equals(status)) {
            sql.append(" ORDER BY APNTMN_YMD DESC"); // 임용일자순 정렬
        }
  
        return sql.toString();
	}
}
