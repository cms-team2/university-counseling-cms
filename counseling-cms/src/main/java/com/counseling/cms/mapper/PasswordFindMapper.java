package com.counseling.cms.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.EmailConfirmDto;

@Mapper
public interface PasswordFindMapper {
	@Select("SELECT COUNT(*) FROM USER_INFO WHERE USER_ID=#{userId} AND EML=#{userEmail}")
	int findUser(EmailConfirmDto emailConfirmDto);
	
	@Update("UPDATE USER_INFO SET PSWD=#{changePassword} WHERE USER_ID=#{userId}")
	int updatePassword(Map<String, String> userData);
}
