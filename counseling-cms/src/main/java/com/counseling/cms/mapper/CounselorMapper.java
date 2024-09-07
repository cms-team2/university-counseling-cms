package com.counseling.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import cms.counseling.cms.dto.CounselorDto;

@Mapper
public interface CounselorMapper {
	
	//상담사 목록
	@Select("SELECT e.EMP_NO, e.FLNM, d.DEPT_NM, e.EMP_SE_NM, e.EMP_EML, e.EMP_TELNO, "
			+ "e.APNTMN_YMD, c.C_SCLSF_NM, f.FILE_PATH, f.FILE_NM "
			+ "FROM EMP_INFO e "
			+ "JOIN DEPT_INFO d ON e.DEPT_NO = d.DEPT_NO "
			+ "JOIN CNSLR_INFO c ON e.EMP_NO = c.EMP_NO "
			+ "LEFT JOIN COM_FILE f ON e.FILE_NO = f.FILE_NO "
			+ "WHERE e.EMP_SE_NM = '상담사'")
	@Results({
		@Result(property = "facultyNumber", column = "EMP_NO"),
		@Result(property = "facultyName", column = "FLNM"),
		@Result(property = "facultyPart", column = "DEPT_NM"),
		@Result(property = "facultyClassification", column = "EMP_SE_NM"),
		@Result(property = "facultyEmail", column = "EMP_EML"),
		@Result(property = "facultyTel", column = "EMP_TELNO"),
		@Result(property = "facultyAppointmentDate", column = "APNTMN_YMD"),
		@Result(property = "facultyPartNumber", column = "DEPT_NO"),
		@Result(property = "counselCategory", column = "C_SCLSF_NM"), // 상담분류
	    @Result(property = "filePath", column = "FILE_PATH"),   // 파일 경로 추가
	    @Result(property = "fileName", column = "FILE_NM")     // 파일 이름 추가
	})
	List<CounselorDto> selectCounselors();
	

	//상담사 검색
	@SelectProvider(type = CounselorSqlProvider.class, method = "searchCounselors")
	@Results({
        @Result(property = "facultyNumber", column = "EMP_NO"),
        @Result(property = "facultyName", column = "FLNM"),
        @Result(property = "facultyPart", column = "DEPT_NM"),
        @Result(property = "facultyClassification", column = "EMP_SE_NM"),
        @Result(property = "facultyEmail", column = "EMP_EML"),
        @Result(property = "facultyTel", column = "EMP_TELNO"),
        @Result(property = "facultyAppointmentDate", column = "APNTMN_YMD"),
        @Result(property = "counselCategory", column = "C_SCLSF_NM")
	})
	List<CounselorDto> searchCounselors(@Param("searchType") String searchType,
										@Param("searchKeyword") String searchKeyword,
										@Param("counselCategory") String counselCategory,
										@Param("status") String status);
}