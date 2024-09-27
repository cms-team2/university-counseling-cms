package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.entity.UserMyActivityEntity;
import com.counseling.cms.entity.UserMypageEntity;

@Mapper
public interface UserMypageMapper {

	@Select("Select * from VIEW_MYPAGE where USER_ID=#{userId}")
	@Results ({
        @Result(property = "userId", column = "USER_ID"),
        @Result(property = "eml", column = "EML"),
        @Result(property = "flnm", column = "FLNM"),
        @Result(property = "deptNm", column = "DEPT_NM"),
        @Result(property = "stdntTelno", column = "STDNT_TELNO"),
        @Result(property = "stdntZip", column = "STDNT_ZIP"),
        @Result(property = "stdntAddr", column = "STDNT_ADDR"),
        @Result(property = "stdntDaddr", column = "STDNT_DADDR")
    })
	UserMypageEntity dto(String userId);
	
	@Select("SELECT * FROM VIEW_MYACTIVITY WHERE STDNT_NO = #{stdnt_no} ORDER BY CASE C_PRGRS_YN WHEN 'B' THEN 1 WHEN 'C' THEN 2 ELSE 3 END, DSCSN_DT DESC")
	@Results({
	    @Result(property = "cCdClsfNm", column = "C_CD_CLSF_NM"),
	    @Result(property = "stdntNo", column = "STDNT_NO"),
	    @Result(property = "dscsnDt", column = "DSCSN_DT"),
	    @Result(property = "dscsnCn", column = "DSCSN_CN"),
	    @Result(property = "studentFlNm", column = "STUDENT_FLNM"),
	    @Result(property = "empFlNm", column = "EMP_FLNM"),
	    @Result(property = "cPrgrsYn", column = "C_PRGRS_YN"),
	    @Result(property = "cSclsfNm", column = "C_SCLSF_NM"),
	    @Result(property = "aplyNo", column = "APLY_NO")
	})
	List<UserMyActivityEntity> getMyActivity(String stdnt_no);
	
	@Select("SELECT COUNT(*) FROM VIEW_MYACTIVITY WHERE STDNT_NO = #{userId}")
	int getDscsnCount(String userId);
	
	@Update("UPDATE DSCSN_APLY_INFO SET C_PRGRS_YN = 'S' WHERE APLY_NO = #{idx}")
	int canselDscsn(String idx);
}
