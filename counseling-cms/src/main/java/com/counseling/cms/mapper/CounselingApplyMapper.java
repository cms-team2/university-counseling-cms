package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.CounselingMenuDto;

@Mapper
public interface CounselingApplyMapper {
	
	@Select("SELECT C_SCLSF_CD as counselingCode ,C_SCLSF_NM as counselingName FROM DSCSN_CATEGORY")	
	List<CounselingMenuDto> getCounselingMenuMapper();
}
