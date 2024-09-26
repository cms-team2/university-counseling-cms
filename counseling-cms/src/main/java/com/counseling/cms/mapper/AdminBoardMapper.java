package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.PostDto;
import com.counseling.cms.dto.ReplyDto;
import com.counseling.cms.entity.FaqEntity;
import com.counseling.cms.entity.PostEntity;

@Mapper
public interface AdminBoardMapper {
	@Select("SELECT COUNT(*) FROM PST WHERE BBS_NO=#{boardNumber}")
    int countPosts(int boardNumber);
	
	@Select("SELECT COUNT(*) FROM FAQ_BBS")
    int countFaqs();

	@Select("SELECT COUNT(*) FROM PST WHERE BBS_NO=#{boardNumber} AND PSTG_YN=#{searchValue}")
	int countSearchPostUsable(int boardNumber, String searchPart, String searchValue);
	
	@Select("SELECT COUNT(*) FROM PST WHERE BBS_NO=#{boardNumber} AND PST_FIX=#{searchValue}")
	int countSearchFixedUsable(int boardNumber, String searchPart, String searchValue);
	
	@Select("SELECT COUNT(*) FROM PST WHERE BBS_NO=#{boardNumber} AND PST_TTL LIKE CONCAT('%',#{searchValue},'%')")
	int countSearchTitle(int boardNumber, String searchPart, String searchValue);
	
	

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
	
	
	@Select("SELECT * FROM FAQ_BBS ORDER BY FAQ_NO desc LIMIT #{start}, #{pageSize}")
	@Results({
		@Result(property = "postNumber", column = "FAQ_NO"),
        @Result(property = "postTitle", column = "FAQ_PST_TTL"),
        @Result(property = "postContent", column = "FAQ_PST_CN"),
        @Result(property = "postingDate", column = "FAQ_YMD"),
        @Result(property = "postUsable", column = "FAQ_PSTG_YN"),
    })
	List<FaqEntity> getFaqMapper(int start, int pageSize);
	
	@Select("SELECT * FROM PST WHERE BBS_NO = #{boardNumber} AND PSTG_YN = #{searchValue}"
			+ "ORDER BY PST_NO desc LIMIT #{start}, #{pageSize}")
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
	List<PostEntity> getSearchPostUsableMapper(int boardNumber, int start, int pageSize, String searchPart, String searchValue);
	
	@Select("SELECT * FROM PST WHERE BBS_NO = #{boardNumber} AND PST_FIX = #{searchValue}"
			+ "ORDER BY PST_NO desc LIMIT #{start}, #{pageSize}")
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
	List<PostEntity> getSearchFixedUsableMapper(int boardNumber, int start, int pageSize, String searchPart, String searchValue);
	
	@Select("SELECT * FROM PST WHERE BBS_NO = #{boardNumber} AND PST_TTL LIKE CONCAT('%',#{searchValue},'%') "
			+ "ORDER BY PST_NO desc LIMIT #{start}, #{pageSize}")
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
	List<PostEntity> getSearchTitleMapper(int boardNumber, int start, int pageSize, String searchPart, String searchValue);
	
	

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
	
	@Select("SELECT * FROM FAQ_BBS WHERE FAQ_NO=#{postNumber}")
	@Results({
		@Result(property = "postNumber", column = "FAQ_NO"),
        @Result(property = "postTitle", column = "FAQ_PST_TTL"),
        @Result(property = "postContent", column = "FAQ_PST_CN"),
        @Result(property = "postingDate", column = "FAQ_YMD"),
        @Result(property = "postUsable", column = "FAQ_PSTG_YN"),
    })
	FaqEntity getOneFaqMapper(int postNumber);
	
	@Update("UPDATE PST SET PST_TTL=#{postTitle}, PST_CN=#{postContent} , PST_FIX=#{fixedUsable} , PSTG_YN=#{postUsable}, MDF_YMD=now()"
			+ "WHERE PST_NO=#{postNumber}")
	int modifyPostMapper(PostDto postDto);
	
	@Delete({"<script>",
		    "DELETE FROM PST WHERE PST_NO IN",
		    "<foreach item='item' collection='postNumber' open='(' separator=',' close=')'>",
		    "#{item}",
		    "</foreach>",
		    "</script>"})
	int deleteCheckedPostMapper(List<Integer> postNumber);
	
	@Delete("DELETE FROM PST WHERE PST_NO=#{postNumber}")
	int deletePostMapper(int postNumber);
	
	@Insert("INSERT INTO CMNT (PST_NO,CMNT_CN,CMNT_YMD) VALUES (#{postNumber},#{replyContent},now())")
	int createReplyMapper(ReplyDto replyDto);
	
	@Select("SELECT CMNT_CN FROM CMNT WHERE PST_NO=#{postNumber}")
	String getReplyContentMapper(int postNumber);
	
	@Select("SELECT CASE WHEN COUNT(c.CMNT_CN) > 0 THEN 'Y' ELSE 'N' END " +
            "FROM CMNT c WHERE c.PST_NO = #{postNumber}")
	String checkReplyExists(int boardNumber);
	
	@Insert("INSERT INTO FAQ_BBS (`FAQ_PST_TTL`,`FAQ_PST_CN`,`FAQ_PSTG_YN`,`FAQ_YMD`) VALUES "
			+ "(#{postTitle},#{postContent},#{postUsable},now())")
	int createFaq(PostDto postDto);
	
	@Update("UPDATE FAQ_BBS SET FAQ_PST_TTL=#{postTitle}, FAQ_PST_CN=#{postContent} ,  FAQ_PSTG_YN=#{postUsable}, FAQ_YMD=now()"
			+ "WHERE FAQ_NO=#{postNumber}")
	int modifyFaqMapper(PostDto postDto);
}

