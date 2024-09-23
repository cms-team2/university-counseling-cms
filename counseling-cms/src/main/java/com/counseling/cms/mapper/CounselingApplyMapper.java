package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.CounselingMenuDto;
import com.counseling.cms.entity.ApplyEntity;

@Mapper
public interface CounselingApplyMapper {
	
	@Select("SELECT C_SCLSF_CD as counselingCode ,C_SCLSF_NM as counselingName FROM DSCSN_CATEGORY")	
	List<CounselingMenuDto> getCounselingMenuMapper();
	
	@Insert("INSERT INTO DSCSN_APLY_INFO (STDNT_NO, C_APLY_DT, DSCSN_RSVT_YMD, DSCSN_YN, C_PRGRS_YN, FILE_NO, C_TYPE_NM, DSCSN_APLY_CN, C_SCLSF_NM)"
			+ "VALUES (#{studentNumber},now(),#{applyDate},#{applyStatus},#{status},#{fileNumber},#{applyMethod},#{applyContent},#{applyCategory})")
	int createApplicationMapper(ApplyEntity applyEntity);

	
}
