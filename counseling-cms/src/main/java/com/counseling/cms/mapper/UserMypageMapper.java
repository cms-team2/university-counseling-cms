package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.UserMyActivityEntity;
import com.counseling.cms.entity.UserMypageEntity;

@Mapper
public interface UserMypageMapper {

	@Select("Select * from VIEW_MYPAGE where USER_ID=#{userId}")
	@Results ({
        @Result(property = "userId", column = "USER_ID"),
        @Result(property = "eml", column = "EML"),
        @Result(property = "flnm", column = "FLNM"),
        @Result(property = "rcntCntnDt", column = "RCNT_CNTN_DT"),
        @Result(property = "deptNm", column = "DEPT_NM"),
        @Result(property = "stdntTelno", column = "STDNT_TELNO"),
        @Result(property = "stdntZip", column = "STDNT_ZIP"),
        @Result(property = "stdntAddr", column = "STDNT_ADDR"),
        @Result(property = "stdntDaddr", column = "STDNT_DADDR")
    })
	UserMypageEntity dto(String userId);
	
	@Select("select * from VIEW_MYACTIVITY where STDNT_NO=#{stdnt_no}")
	 @Results({
	        @Result(property = "stdntNo", column = "STDNT_NO"),
	        @Result(property = "cCdClsfNm", column = "C_CD_CLSF_NM"),
	        @Result(property = "dscsnDt", column = "DSCSN_DT"),
	        @Result(property = "dscsnCn", column = "DSCSN_CN"),
	        @Result(property = "studentName", column = "STUDENT_NAME"),
	        @Result(property = "employeeName", column = "EMPLOYEE_NAME")
	    })
	List<UserMyActivityEntity> getMyActivity(String stdnt_no);
	
}
