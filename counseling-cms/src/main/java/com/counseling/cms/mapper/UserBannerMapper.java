package com.counseling.cms.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.MainNoticeEntity;
import com.counseling.cms.entity.PageBannerEntity;

@Mapper
public interface UserBannerMapper {
	@Select("SELECT bnr.*, com_file.FILE_PATH FROM BNR bnr " +
	        "JOIN COM_FILE com_file ON com_file.file_no = bnr.file_no " +
	        "WHERE bnr.BNR_PSTG_YN = 'Y' " +
	        "ORDER BY bnr.BNR_SEQ ASC")
	@Results({
		@Result(property="bnr_no",column="BNR_NO"),
		@Result(property="file_no",column="FILE_NO"),
		@Result(property="bnr_seq",column="BNR_SEQ"),
		@Result(property="bnr_ttl",column="BNR_TTL"),
		@Result(property="bnr_cn",column="BNR_CN"),
		@Result(property="bnr_pstg_yn",column="BNR_PSTG_YN"),
		@Result(property="bnr_ymd",column="BNR_YMD"),
		@Result(property="file_path",column="FILE_PATH")
	})
	ArrayList<PageBannerEntity> getUserBannerMapper();
	
	@Select("SELECT  PST_NO, PST_TTL, PSTG_YMD FROM PST WHERE BBS_NO=2 ORDER BY PST_NO DESC LIMIT 0,5")
	@Results({
		@Result(property="noticeNo",column="PST_NO"),
		@Result(property="noticeTitle",column="PST_TTL"),
		@Result(property="postDate",column="PSTG_YMD")
	})
	ArrayList<MainNoticeEntity> getNoticeList();
}
