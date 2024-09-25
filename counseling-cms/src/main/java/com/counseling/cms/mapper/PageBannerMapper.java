package com.counseling.cms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.entity.PageBannerEntity;

@Mapper
public interface PageBannerMapper {
	@Select("SELECT COUNT(*) FROM BNR")
    int countBannerList();
	
    @Select("SELECT * FROM BNR " +
            "WHERE BNR_TTL LIKE CONCAT('%', #{searchText}, '%') " +
            "ORDER BY BNR_SEQ ASC " +
            "LIMIT #{start}, #{pageSize}")
	@Results({
		@Result(property="bnr_no",column="BNR_NO"),
		@Result(property="file_no",column="FILE_NO"),
		@Result(property="bnr_seq",column="BNR_SEQ"),
		@Result(property="bnr_ttl",column="BNR_TTL"),
		@Result(property="bnr_cn",column="BNR_CN"),
		@Result(property="bnr_pstg_yn",column="BNR_PSTG_YN"),
		@Result(property="bnr_ymd",column="BNR_YMD")
	})
	ArrayList<PageBannerEntity> getBannerListMapper(int start, int pageSize,String searchText);
    
    @Select("SELECT FILE_NO FROM BNR WHERE BNR_NO=#{bnr_no}")
    Integer selectFileNoMapper(Integer bnr_no);
    
    @Select("SELECT * FROM BNR WHERE BNR_NO=#{bnr_no}")
    @Results({
		@Result(property="bnr_no",column="BNR_NO"),
		@Result(property="file_no",column="FILE_NO"),
		@Result(property="bnr_seq",column="BNR_SEQ"),
		@Result(property="bnr_ttl",column="BNR_TTL"),
		@Result(property="bnr_cn",column="BNR_CN"),
		@Result(property="bnr_pstg_yn",column="BNR_PSTG_YN"),
		@Result(property="bnr_ymd",column="BNR_YMD")
	})
    PageBannerEntity selectAllbyBnrNo(Integer bnr_no);
    
    @Insert("INSERT INTO BNR (FILE_NO, BNR_SEQ, BNR_TTL, BNR_CN, BNR_PSTG_YN, BNR_YMD) " +
            "VALUES (#{file_no}, #{bnr_seq}, #{bnr_ttl}, #{bnr_cn}, #{bnr_pstg_yn},now())")
	int insertBanner(PageBannerEntity pageBannerEntity);
    
    @Delete("DELETE FROM BNR WHERE BNR_NO = #{bnr_no}")
    Integer deleteBannerByBnrNoMapper(Integer bnr_no);
    
    @Update("UPDATE BNR SET "
            + "BNR_SEQ = #{bnr_seq}, "
            + "BNR_TTL = #{bnr_ttl}, "
            + "BNR_CN = #{bnr_cn}, "
            + "BNR_PSTG_YN = #{bnr_pstg_yn}, "
            + "BNR_YMD = now() "
            + "WHERE FILE_NO = #{file_no}")
    int updateBnr(PageBannerEntity pageBannerEntity);
	
    @Select("SELECT BNR_SEQ FROM BNR")
    List<Integer> getBannerSequence();
    
    @Select("SELECT SUB_EXPSR_SEQ FROM SUBMENU_CATEGORY WHERE LCLSF_CD=#{majorCode}")
    List<Integer> getSubMenuSequence(String majorCode);
    
    @Select("SELECT EXPSR_SEQ FROM MAJOR_CATEGORY")
    List<Integer> getMajorMenuSequence();
}
