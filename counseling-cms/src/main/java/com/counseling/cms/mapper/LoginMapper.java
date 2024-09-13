package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.userdetails.User;

import com.counseling.cms.entity.UserInfoEntity;

@Mapper
public interface LoginMapper {
	
	//사용자 정보 비밀번호 암호화 후 insert
	@Insert("INSERT INTO USER_INFO VALUES (0,#{userId},#{userEmail},#{userPassword},0,now(),'N','학생',#{userAuthority})")
	int insertUserInfo(UserInfoEntity userInfoEntity);
	
	
	//사용자 아이디로 관리자 정보 조회
	@Select("SELECT USER_ID,PSWD,PSWD_FAIL_NMTM,RCNT_CNTN_DT,ACNT_LCK_YN,AUTHRT  FROM USER_INFO WHERE USER_ID=#{userId} AND (AUTHRT='M' OR AUTHRT='A')")
	@Results	({
        @Result(property = "userId", column = "USER_ID"),
        @Result(property = "userPassword", column = "PSWD"),
        @Result(property = "passwordFail", column = "PSWD_FAIL_NMTM"),
        @Result(property = "resentConnectionDate", column = "RCNT_CNTN_DT"),
        @Result(property = "accountLocking", column = "ACNT_LCK_YN"),
        @Result(property = "userAuthority", column = "AUTHRT")
	})
	UserInfoEntity findByAdminId(String userId);
	
	//사용자 아이디로 사용자 정보 조회
		@Select("SELECT USER_ID,PSWD,PSWD_FAIL_NMTM,RCNT_CNTN_DT,ACNT_LCK_YN,AUTHRT  FROM USER_INFO WHERE USER_ID=#{userId} AND AUTHRT='N'")
		@Results	({
	        @Result(property = "userId", column = "USER_ID"),
	        @Result(property = "userPassword", column = "PSWD"),
	        @Result(property = "passwordFail", column = "PSWD_FAIL_NMTM"),
	        @Result(property = "resentConnectionDate", column = "RCNT_CNTN_DT"),
	        @Result(property = "accountLocking", column = "ACNT_LCK_YN"),
	        @Result(property = "userAuthority", column = "AUTHRT")
		})
		UserInfoEntity findByUserId(String userId);
		
		//사용자 아이디로 상담사 정보 조회
		@Select("SELECT USER_ID,PSWD,PSWD_FAIL_NMTM,RCNT_CNTN_DT,ACNT_LCK_YN,AUTHRT  FROM USER_INFO WHERE USER_ID=#{userId} AND AUTHRT='C'")
		@Results	({
	        @Result(property = "userId", column = "USER_ID"),
	        @Result(property = "userPassword", column = "PSWD"),
	        @Result(property = "passwordFail", column = "PSWD_FAIL_NMTM"),
	        @Result(property = "resentConnectionDate", column = "RCNT_CNTN_DT"),
	        @Result(property = "accountLocking", column = "ACNT_LCK_YN"),
	        @Result(property = "userAuthority", column = "AUTHRT")
		})
		UserInfoEntity findByCounselorId(String userId);
	
	@Select("SELECT PSWD_FAIL_NMTM FROM USER_INFO WHERE USER_ID=#{userId}")
	int getPasswordFail(String userId);
	
	@Update("UPDATE USER_INFO SET PSWD_FAIL_NMTM=#{failCount} WHERE USER_ID=#{userId}")
	int updatePasswordFail(int failCount, String userId);
	
	@Update("UPDATE USER_INFO SET RCNT_CNTN_DT=now() WHERE USER_ID=#{userId}")
	int updateLastConnectDate(String userId);
	
}
