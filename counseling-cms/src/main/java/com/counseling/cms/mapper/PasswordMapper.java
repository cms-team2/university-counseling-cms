package com.counseling.cms.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.EmailConfirmDto;

@Mapper
public interface PasswordMapper {
	@Select("SELECT COUNT(*) FROM VIEW_FIND_PW WHERE FLNM=#{userName} and EML=#{userEmail}")
	int findUser(EmailConfirmDto emailConfirmDto);
	
	@Update("UPDATE USER_INFO SET PSWD=#{changePassword} WHERE EML=#{userEmail}")
	int updatePassword(Map<String, String> userData);
}
