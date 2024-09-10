package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.userdetails.User;

import com.counseling.cms.entity.UserInfoEntity;

@Mapper
public interface LoginMapper {
	
	//사용자 정보 비밀번호 암호화 후 insert
	@Insert("INSERT INTO USER_INFO VALUES (0,#{userId},#{userPassword},0,now(),'N','박세은',#{userAuthority})")
	int insertUserInfo(UserInfoEntity userInfoEntity);
	
	
	//사용자 아이디로 사용자 정보 조회
	@Select("SELECT USER_ID,PSWD,PSWD_FAIL_NMTM,RCNT_CNTN_DT,ACNT_LCK_YN,AUTHRT  FROM USER_INFO WHERE USER_ID=#{userId}")
	@Results	({
        @Result(property = "userId", column = "USER_ID"),
        @Result(property = "userPassword", column = "PSWD"),
        @Result(property = "passwordFail", column = "PSWD_FAIL_NMTM"),
        @Result(property = "resentConnectionDate", column = "RCNT_CNTN_DT"),
        @Result(property = "accountLocking", column = "ACNT_LCK_YN"),
        @Result(property = "userAuthority", column = "AUTHRT")
	})
	UserInfoEntity findByUserId(String userId);
	
	
	
}
