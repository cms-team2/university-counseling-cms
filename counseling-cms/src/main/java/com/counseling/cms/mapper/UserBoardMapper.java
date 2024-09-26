package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.FaqBbsDto;
import com.counseling.cms.dto.PstDto;

@Mapper
public interface UserBoardMapper {

    @Select({
        "<script>",
        "SELECT * FROM PST WHERE BBS_NO = #{boardId} AND PSTG_YN = 'Y'",
        "<if test='keyword != null and keyword != \"\"'>",
        "AND (PST_TTL LIKE CONCAT('%', #{keyword}, '%') OR PBLR_NM LIKE CONCAT('%', #{keyword}, '%'))",
        "</if>",
        "<if test='category != null and category != \"\"'>",
        "<choose>",
            "<when test='category == \"title\"'>",
            "AND PST_TTL LIKE CONCAT('%', #{keyword}, '%')",
            "</when>",
            "<when test='category == \"writer\"'>",
            "AND PBLR_NM LIKE CONCAT('%', #{keyword}, '%')",
            "</when>",
            "<otherwise>",
            "AND CATEGORY_COLUMN = #{category}",  
            "</otherwise>",
        "</choose>",
        "</if>",
        "ORDER BY PST_NO DESC LIMIT #{limit} OFFSET #{offset}",
        "</script>"
    })
    List<PstDto> getBoardList(int boardId, int limit, int offset, String keyword, String category);

    @Select({
        "<script>",
        "SELECT COUNT(*) as cnt FROM PST WHERE BBS_NO = #{boardId} AND PSTG_YN = 'Y'",
        "<if test='keyword != null and keyword != \"\"'>",
        "AND (PST_TTL LIKE CONCAT('%', #{keyword}, '%') OR PBLR_NM LIKE CONCAT('%', #{keyword}, '%'))",
        "</if>",
        "<if test='category != null and category != \"\"'>",
        "<choose>",
            "<when test='category == \"title\"'>",
            "AND PST_TTL LIKE CONCAT('%', #{keyword}, '%')",
            "</when>",
            "<when test='category == \"writer\"'>",
            "AND PBLR_NM LIKE CONCAT('%', #{keyword}, '%')",
            "</when>",
            "<otherwise>",
            "AND CATEGORY_COLUMN = #{category}",  
            "</otherwise>",
        "</choose>",
        "</if>",
        "</script>"
    })
    int getBoardCount(int boardId, String keyword, String category);

    @Select("SELECT * FROM PST WHERE BBS_NO=#{boardId} AND PSTG_YN='Y' AND STDNT_NO=#{userId} ORDER BY PST_NO DESC LIMIT #{limit} OFFSET #{offset}")
    List<PstDto> getInquiryList(int boardId, int limit, int offset, String userId);
    
    @Select("SELECT COUNT(*) as cnt FROM PST WHERE BBS_NO=#{boardId} AND PSTG_YN='Y' AND STDNT_NO=#{stdntNo}")
    int getInquiryCount(int boardId, String stdntNo);

    @Select("SELECT * FROM FAQ_BBS WHERE FAQ_PSTG_YN='Y' ORDER BY FAQ_NO DESC LIMIT #{limit} OFFSET #{offset}")
    List<FaqBbsDto> getFaqList(int limit, int offset);

    @Select("SELECT COUNT(*) as cnt FROM FAQ_BBS WHERE FAQ_PSTG_YN='Y'")
    int getFaqCount();
    
    @Select("SELECT * FROM PST WHERE BBS_NO=#{boardId} AND PST_NO=#{pstNo}")
    PstDto getBoardView(int boardId, String pstNo);
    
    @Update("UPDATE PST SET PST_INQ_CNT=#{inqCnt} WHERE PST_NO=#{pstNo}")
    void upInqCnt(String pstNo, int inqCnt);
}