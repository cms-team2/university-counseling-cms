package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.dto.StdntDscsnJoinDto;
import com.counseling.cms.entity.ApplyListEntity;
@Mapper
public interface DscsnAplyInfoRepo {
	List<StdntDscsnJoinDto> dscsn_Aply_List(String keyword,String type);
	
	@Select("SELECT a.DSCSN_RSVT_YMD, a.DSCSN_APLY_CN, b.C_SCLSF_NM FROM DSCSN_APLY_INFO AS a JOIN DSCSN_CATEGORY AS b ON a.C_SCLSF_CD = b.C_SCLSF_CD WHERE a.STDNT_NO = #{Stdnt_id};")
	 @Results({
		 @Result(property = "dscsnRsvtYmd", column = "DSCSN_RSVT_YMD"),
		 @Result(property = "dscsnAplyCn", column = "DSCSN_APLY_CN"),
		 @Result(property = "cSclsfNm", column = "C_SCLSF_NM")
	    })
	ApplyListEntity getApplyListMapper(String Stdnt_id);
	
	@Select("SELECT EMP_NO,C_SCLSF_CD FROM DSCSN_APLY_INFO WHERE DSCSN_YN = 'Y' AND DSCSN_RSVT_YMD = #{DscsnRsvtYmd};")
	List<String> getCounslerList(String DscsnRsvtYmd);

	@Select("SELECT EMP_NO,FLNM FROM EMP_INFO WHERE EMP_SE_NM <> '교수'  AND EMP_NO <> #{data};")
	String getCounsler(String data);
	
}
