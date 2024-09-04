package com.counseling.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import cms.counseling.cms.dto.CounselorDto;

@Mapper
public interface CounselorMapper {

	@Select("SELECT e.EMP_NO, e.FLNM, d.DEPT_NM, e.EMP_SE_NM, e.EMP_EML, e.EMP_TELNO, e.APNTMN_YMD "
			+ "FROM EMP_INFO e "
			+ "JOIN DEPT_INFO d ON e.DEPT_NO = d.DEPT_NO")
	List<CounselorDto> selectCounselors();
}
