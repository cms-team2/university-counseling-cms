package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.AdminScheduleCounselorDto;
import com.counseling.cms.dto.AdminScheduleDto;
import com.counseling.cms.dto.AdminScheduleModalDto;

@Mapper
public interface AdminScheduleMapper {

	//스케줄
	@SelectProvider(type = ScheduleSqlProvider.class, method = "searchSchedule")
	@Results({
		@Result(property = "studentName", column = "FLNM"),
		@Result(property = "studentNumber", column = "STDNT_NO"),
		@Result(property = "applyNumber", column = "APLY_NO"),
		@Result(property = "counselingClassifyName", column = "C_SCLSF_NM"),
		@Result(property = "studentEmail", column = "STDNT_EML"),
		@Result(property = "studentTelNumber", column = "STDNT_TELNO"),
		@Result(property = "counselingReservationDate", column = "DSCSN_RSVT_YMD")
	})
	List<AdminScheduleDto> selectSchedules(@Param("search_type") String search_type,
			@Param("search_value") String search_value,
			@Param("status") String status,
			@Param("pageno") Integer pageno,
			@Param("ea") Integer ea);

	
	//스케줄 개수
	@SelectProvider(type = ScheduleSqlProvider.class, method = "scheduleCount")
	int countSchedules(@Param("search_type") String search_type, @Param("search_value") String search_value);
	
	
	//스케줄 모달
	@Select("SELECT a.C_TYPE_NM, e.FLNM, d.DEPT_NM, a.DSCSN_RSVT_YMD, e.EMP_NO"
			+ " FROM DSCSN_APLY_INFO AS a"
			+ " JOIN EMP_INFO AS e ON a.EMP_NO = e.EMP_NO"
			+ " JOIN DEPT_INFO AS d ON e.DEPT_NO = d.DEPT_NO"
			+ " WHERE a.APLY_NO = #{apply_number};")
	 @Results({
		 @Result(property = "counselingTypeName", column = "C_TYPE_NM"),
		 @Result(property = "employeeName", column = "FLNM"),
		 @Result(property = "departmentName", column = "DEPT_NM"),
		 @Result(property = "counselingReservateDate", column = "DSCSN_RSVT_YMD")
	    })
	AdminScheduleModalDto selectSchedulesModal(@Param("apply_number") String apply_number);
	
	//최초 상담 일시
	@Select("SELECT DSCSN_DT FROM DSCSN_RSLT WHERE STDNT_NO=${student_number} ORDER BY DSCSN_DT ASC")
	String firstCounseling(@Param("student_number") String student_number);
	
	//최근 상담 일시
	@Select("SELECT DSCSN_DT FROM DSCSN_RSLT WHERE STDNT_NO=${student_number} ORDER BY DSCSN_DT DESC")
	String lastCounseling(@Param("student_number") String student_number);
	
	
	//상담이 잡혀 있는 상담사 사번만 가져옴
	@Select("SELECT EMP_NO FROM DSCSN_APLY_INFO WHERE DSCSN_YN = 'Y' AND DSCSN_RSVT_YMD = #{reservateDate};")
	List<String> getCounslerList(String reservateDate);

	//위 사번이 아닌 사람들만 출력하는 리스트
	@SelectProvider(type = ScheduleSqlProvider.class, method = "haveTimeCounselor")
	@Results({@Result(property = "employeeNumber", column = "EMP_NO"),@Result(property = "employeeName", column = "FLNM")})
	List<AdminScheduleCounselorDto> getCounselors(List<String> getCounselors);

	//상담사 변경
	@Update("UPDATE DSCSN_APLY_INFO SET EMP_NO=#{employee_number} WHERE APLY_NO=#{apply_number}")
	int updateCounselorAllotment(@Param("employee_number") String employee_number, @Param("apply_number") String apply_number);
	
}
