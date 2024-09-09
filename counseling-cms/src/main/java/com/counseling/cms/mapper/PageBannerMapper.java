package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.AdminBannerDto;
import com.counseling.cms.entity.AdminBannerEntity;

@Mapper
public interface PageBannerMapper {
	@Select("SELECT COUNT(*) FROM BNR")
    int countBannerList();
	
	@Select("SELECT * FROM BNR")
//	@Results({
//		@Result(property="bnr_no",column="BNR_NO"),
//		@Result(property="file_no",column="FILE_NO"),
//		@Result(property="bnr_seq",column="BNR_SEQ"),
//		@Result(property="bnr_ttl",column="BNR_TTL"),
//		@Result(property="bnr_cn",column="BNR_CN"),
//		@Result(property="bnr_pstg_yn",column="BNR_PSTG_YN")
//	})
	List<AdminBannerEntity> getBannerList();
	
    @Insert("INSERT INTO BNR (FILE_NO, BNR_SEQ, BNR_TTL, BNR_CN, BNR_PSTG_YN) " +
            "VALUES (#{file_no}, #{bnr_seq}, #{bnr_ttl}, #{bnr_cn}, #{bnr_pstg_yn})")
	int insertBanner(AdminBannerEntity adminBannerEntity);
	
}
