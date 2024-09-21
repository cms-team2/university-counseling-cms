package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO user_tokens VALUES(#{userId},#{refreshToken},now())")
	int saveRefreshToken(String userId, String refreshToken);
	
	@Select("SELECT expiry_date FROM user_tokens WHERE user_id=#{userId} ")
	String getResfredhToken(String userId);
	
	@Delete("DELETE FROM user_tokens WHERE user_id=#{userId}")
	int removeResfredhToken(String userId);
}
