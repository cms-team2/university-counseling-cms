package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.PostEntity;

@Mapper
public interface AdminBoardMapper {
	@Select("SELECT COUNT(*) FROM PST WHERE BBS_NO=#{boardNumber}")
    int countPosts(int boardNumber);
	
	
	
	
	@Select("SELECT * FROM PST WHERE BBS_NO = #{boardNumber} ORDER BY PST_NO desc LIMIT #{start}, #{pageSize}")
	@Results({
        @Result(property = "postNumber", column = "PST_NO"),
        @Result(property = "postTitle", column = "PST_TTL"),
        @Result(property = "postContent", column = "PST_CN"),
        @Result(property = "fileNumber", column = "FILE_NO"),
        @Result(property = "fixedUsable", column = "PST_FIX"),
        @Result(property = "category", column = "CTGRY"),
        @Result(property = "postViews", column = "PST_INQ_CNT"),
        @Result(property = "postingDate", column = "PSTG_YMD"),
        @Result(property = "userName", column = "PBLR_NM"),
        @Result(property = "postUsable", column = "PSTG_YN"),
        @Result(property = "boardNumber", column = "BBS_NO")
    })
	List<PostEntity> getPostMapper(int boardNumber, int start, int pageSize);

	@Insert("INSERT INTO PST(`PST_TTL`,`PST_CN`,`FILE_NO`,`PST_FIX`,`CTGRY`,`PST_INQ_CNT`,`PSTG_YMD`,`PBLR_NM`,`PSTG_YN`,`BBS_NO`)"
			+ " VALUES (#{postTitle},#{postContent},#{fileNumber},#{fixedUsable},#{category},#{postViews},now(),#{userName},#{postUsable},#{boardNumber})")
	int createPost(PostEntity postEntity);
	
	@Select("SELECT * FROM PST WHERE PST_NO=#{postNumber}")
	@Results({
        @Result(property = "postNumber", column = "PST_NO"),
        @Result(property = "postTitle", column = "PST_TTL"),
        @Result(property = "postContent", column = "PST_CN"),
        @Result(property = "fileNumber", column = "FILE_NO"),
        @Result(property = "fixedUsable", column = "PST_FIX"),
        @Result(property = "category", column = "CTGRY"),
        @Result(property = "postViews", column = "PST_INQ_CNT"),
        @Result(property = "postingDate", column = "PSTG_YMD"),
        @Result(property = "userName", column = "PBLR_NM"),
        @Result(property = "postUsable", column = "PSTG_YN"),
        @Result(property = "boardNumber", column = "BBS_NO")
    })
	PostEntity getOnePostMapper(int postNumber);
}
