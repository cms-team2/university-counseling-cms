package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO user_tokens VALUES(#{userId},#{refreshToken},now())")
	int saveRefreshToken(String userId, String refreshToken);
}
