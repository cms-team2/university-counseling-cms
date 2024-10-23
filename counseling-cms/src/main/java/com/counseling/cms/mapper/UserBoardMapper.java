package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.FaqBbsDto;
import com.counseling.cms.dto.PstDto;
import com.counseling.cms.entity.UserBoardEntity;
import com.counseling.cms.entity.userReviewEntity;

@Mapper
public interface UserBoardMapper {

    @Select({
        "<script>",
        "SELECT * FROM VIEW_PST WHERE BBS_NO = #{boardId} AND PSTG_YN = 'Y'",
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
    List<UserBoardEntity> getBoardList(int boardId, int limit, int offset, String keyword, String category);

    @Select({
        "<script>",
        "SELECT COUNT(*) as cnt FROM VIEW_PST WHERE BBS_NO = #{boardId} AND PSTG_YN = 'Y'",
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

    @Select("SELECT * FROM VIEW_PST WHERE BBS_NO=#{boardId} AND PSTG_YN='Y' AND STDNT_NO=#{userId} ORDER BY PST_NO DESC LIMIT #{limit} OFFSET #{offset}")
    List<UserBoardEntity> getInquiryList(int boardId, int limit, int offset, String userId);
    
    @Select("SELECT COUNT(*) as cnt FROM VIEW_PST WHERE BBS_NO=#{boardId} AND PSTG_YN='Y' AND STDNT_NO=#{stdntNo}")
    int getInquiryCount(int boardId, String stdntNo);

    @Select("SELECT * FROM FAQ_BBS WHERE FAQ_PSTG_YN='Y' ORDER BY FAQ_NO DESC LIMIT #{limit} OFFSET #{offset}")
    List<FaqBbsDto> getFaqList(int limit, int offset);

    @Select("SELECT COUNT(*) as cnt FROM FAQ_BBS WHERE FAQ_PSTG_YN='Y'")
    int getFaqCount();
    
    @Select("select COUNT(*) as cnt from PST where BBS_NO='1' and FAQ_PSTG_YN='Y'")
    int getCountCounsler();
    
    @Select("SELECT * FROM VIEW_PST WHERE BBS_NO=#{boardId} AND PST_NO=#{pstNo} LIMIT 1")
    UserBoardEntity getBoardView(int boardId, String pstNo);

    @Select("SELECT  CMNT_CN  FROM CMNT AS a JOIN PST AS b ON a.PST_NO = b.PST_NO WHERE CMNT_NO=#{cmntNo}")
    String getComment(String cmntNo);
    
    @Select("select FLNM from STDNT_INFO where STDNT_NO=#{stdntNo}")
    String getUserName(String stdntNo);
    
    @Select("SELECT f.FILE_PATH " +
            "FROM PST p " +
            "LEFT JOIN COM_FILE f ON p.FILE_NO = f.FILE_NO " +
            "WHERE p.PST_NO = #{pstNo}")
    String getPstWithFilePath(String pstNo);

    @Select("select AUTHRT from USER_INFO where USER_ID=#{userId}")
    String getAuthrt(String userId);
    
    @Select("select * from PST where BBS_NO='1' and PSTG_YN='Y'")
    List<UserBoardEntity> getCounSlerList();
    
    @Update("UPDATE PST SET PST_INQ_CNT=#{inqCnt} WHERE PST_NO=#{pstNo}")
    void upInqCnt(String pstNo, int inqCnt);
    
    @Update("UPDATE COM_FILE SET FILE_SEQ=#{fileUuid}, FILE_NM=#{file}, FILE_PATH=#{filePath2},FILE_SZ=#{fileSize},USE_YN='Y' WHERE FILE_PATH=#{filePath}")
    int modifyComFile(String fileUuid, String file, String filePath2, long fileSize, String filePath);


    @Update("UPDATE PST SET PST_TTL=#{title}, PST_CN=#{content}, CTGRY=#{category}, PBLR_NM=#{author}, MDF_YMD=CURRENT_TIMESTAMP WHERE PST_NO=#{pstNo}")
    int updateBoard(String pstNo, String title, String content, String category, String author);

    @Update("UPDATE PST SET PST_TTL=#{title}, PST_CN=#{content}, CTGRY=#{category}, FILE_NO=#{fileNo}, PBLR_NM=#{author}, MDF_YMD=CURRENT_TIMESTAMP WHERE PST_NO=#{pstNo}")
    int updateBoard2(String pstNo, String title, String content, Integer fileNo, String category, String author);
    
    
    @Insert("INSERT INTO COM_FILE (FILE_NO, FILE_SEQ, FILE_NM, FILE_PATH, FILE_SZ, USE_YN) " +
            "VALUES (#{fileCode}, #{fileUuid}, #{filenm}, #{filePath}, #{size}, 'Y')")
	int boardfileWrite(Integer fileCode, String fileUuid, String filenm, String filePath, long size);
    
    @Insert("INSERT INTO PST (PST_TTL, PST_CN, FILE_NO, PST_FIX, CTGRY, PST_INQ_CNT, PSTG_YMD, PBLR_NM, STDNT_NO, PSTG_YN, BBS_NO) " +
            "VALUES (#{title}, #{content}, #{fileCode}, 'N', #{category}, 0, CURRENT_TIMESTAMP, #{author}, #{userId}, 'Y', #{BBS_NO})")
    int boardPstWrite(String userId, String title, String content, Integer fileCode, String category, String author, int BBS_NO);

    @Delete("delete from PST where PST_NO=#{pstNo}")
    int deleteBoard(String pstNo);
    
    @Delete("delete From COM_FILE where FILE_PATH=#{filePath}")
    int deleteComFile(String filePath);

	
    
}
