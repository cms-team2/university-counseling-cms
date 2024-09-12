package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.dto.StdntDscsnJoinDto;
import com.counseling.cms.entity.ApplyListEntity;
import com.counseling.cms.entity.CounslerListEntity;
@Mapper
public interface DscsnAplyInfoRepo {

    @SelectProvider(type = DscsnAplyInfoProvider.class, method = "selectByFlNM")
    List<StdntDscsnJoinDto> selectByFlNM(@Param("keyword") String keyword, @Param("status") String status);

    @SelectProvider(type = DscsnAplyInfoProvider.class, method = "selectByStdntNo")
    List<StdntDscsnJoinDto> selectByStdntNo(@Param("keyword") String keyword, @Param("status") String status);

    @SelectProvider(type = DscsnAplyInfoProvider.class, method = "selectByList")
    List<StdntDscsnJoinDto> selectByList(@Param("status") String status);
	    
	    
	@Select("SELECT a.DSCSN_RSVT_YMD, a.DSCSN_APLY_CN, b.C_SCLSF_NM FROM DSCSN_APLY_INFO AS a JOIN DSCSN_CATEGORY AS b ON a.C_SCLSF_CD = b.C_SCLSF_CD WHERE a.STDNT_NO = #{Stdnt_id};")
	 @Results({
		 @Result(property = "dscsnRsvtYmd", column = "DSCSN_RSVT_YMD"),
		 @Result(property = "dscsnAplyCn", column = "DSCSN_APLY_CN"),
		 @Result(property = "cSclsfNm", column = "C_SCLSF_NM")
	    })
	ApplyListEntity getApplyListMapper(String Stdnt_id);
	
	
	@Select("SELECT EMP_NO FROM DSCSN_APLY_INFO WHERE DSCSN_YN = 'Y' AND DSCSN_RSVT_YMD = #{DscsnRsvtYmd};")
	List<String> getCounslerList(String DscsnRsvtYmd);



	

	@Select("<script>SELECT EMP_NO, FLNM FROM EMP_INFO WHERE EMP_SE_NM = '상담사' AND EMP_NO NOT IN <foreach item='item' collection='data' open='(' close=')' separator=','>#{item}</foreach></script>")
	@Results({ @Result(property = "empNo", column = "EMP_NO"), @Result(property = "flnm", column = "FLNM") })
	List<CounslerListEntity> getCounsler(@Param("data") List<String> data);

	 @Select("SELECT EMP_NO, FLNM FROM EMP_INFO WHERE EMP_SE_NM='상담사'")
	 List<CounslerListEntity> getCounslerAll();
	 


	 @Update("UPDATE DSCSN_APLY_INFO SET DSCSN_YN='Y', EMP_NO=#{empNo} WHERE STDNT_NO=#{stdntNo} AND DSCSN_RSVT_YMD=#{dscsnRsvtYmd}")
	    int putDscsnAllotment(@Param("empNo") String empNo, @Param("stdntNo") String stdntNo, @Param("dscsnRsvtYmd") String dscsnRsvtYmd);

	
}
