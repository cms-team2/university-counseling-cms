package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.PostEntity;

@Mapper
public interface AdminBoardMapper {
	
	@Select("SELECT COUNT(*) FROM PST WHERE BBS_NO=#{boardNumber}")
    int countPosts(int boardNumber);
	
	@Select("SELECT * FROM PST WHERE BBS_NO = #{boardNumber} LIMIT #{start}, #{pageSize}")
    @Results({
        @Result(property = "postNumber", column = "PST_NO"),
        @Result(property = "postTitle", column = "PST_TTL"),
        @Result(property = "postContent", column = "PST_CN"),
        @Result(property = "fileNumber", column = "FILE_SN"),
        @Result(property = "fixedUsable", column = "PST_FIX"),
        @Result(property = "categoryUsable", column = "CTGRY_USE_YN"),
        @Result(property = "postViews", column = "PST_INQ_CNT"),
        @Result(property = "postingDate", column = "PSTG_YMD"),
        @Result(property = "userName", column = "PBLR_NM"),
        @Result(property = "postUsable", column = "PSTG_YN"),
        @Result(property = "boardNumber", column = "BBS_NO")
    })
	List<PostEntity> getPostMapper(int boardNumber, int start, int pageSize);
}
