package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.entity.PageMajorCatgoryEntity;

@Mapper
public interface PageMajorCategoryMapper {
	@Select("SELECT COUNT(*) FROM MAJOR_CATEGORY")
    int countMajorCatgoryList();
	
	@Select("SELECT * FROM MAJOR_CATEGORY WHERE LCLSF_CD=#{menuCode} ORDER BY EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="LCLSF_NM"),
		@Result(property="menuSection",column="PG_SE_NM"),
		@Result(property="menuSequence",column="EXPSR_SEQ"),
		@Result(property="menuYn",column="EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
	})
	PageMajorCatgoryEntity selectByCodeMajorCategoryMapper(String menuCode);
	
	@Select("SELECT * FROM MAJOR_CATEGORY ORDER BY EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="LCLSF_NM"),
		@Result(property="menuSection",column="PG_SE_NM"),
		@Result(property="menuSequence",column="EXPSR_SEQ"),
		@Result(property="menuYn",column="EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
	})
	List<PageMajorCatgoryEntity> allMajorCatgoryList();
	
	@Select("SELECT * FROM MAJOR_CATEGORY WHERE PG_SE_NM=#{menuSection} ORDER BY EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="LCLSF_NM"),
		@Result(property="menuSection",column="PG_SE_NM"),
		@Result(property="menuSequence",column="EXPSR_SEQ"),
		@Result(property="menuYn",column="EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
	})
	List<PageMajorCatgoryEntity> secMajorCatgoryList(String menuSection);
	
	@Select("SELECT * FROM MAJOR_CATEGORY " +
	        "WHERE LCLSF_NM LIKE CONCAT('%', #{searchText}, '%') ORDER BY EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="LCLSF_NM"),
		@Result(property="menuSection",column="PG_SE_NM"),
		@Result(property="menuSequence",column="EXPSR_SEQ"),
		@Result(property="menuYn",column="EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
	})
    List<PageMajorCatgoryEntity> nameMajorCatgoryList(String searchText);	
	
	@Select("SELECT * FROM MAJOR_CATEGORY " +
	        "WHERE LCLSF_CD LIKE CONCAT('%', #{searchText}, '%') ORDER BY EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="LCLSF_NM"),
		@Result(property="menuSection",column="PG_SE_NM"),
		@Result(property="menuSequence",column="EXPSR_SEQ"),
		@Result(property="menuYn",column="EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
	})
    List<PageMajorCatgoryEntity> codeMajorCatgoryList(String searchText);	
	
	@Insert("INSERT INTO MAJOR_CATEGORY (`LCLSF_CD`, `LCLSF_NM`, `PG_SE_NM`, `EXPSR_SEQ`, `EXPSR_YN` , `URL_ADDR`) "
			+ "VALUES (#{menuCode}, #{menuName}, #{menuSection}, #{menuSequence}, #{menuYn} , #{menuUrl})")
	int insertMajorCatgory(PageMajorCatgoryEntity pageMajorCatgoryEntity);
	
    @Update("UPDATE MAJOR_CATEGORY SET "
            + "LCLSF_NM = #{menuName}, "
            + "EXPSR_SEQ = #{menuSequence}, "
            + "EXPSR_YN = #{menuYn}, "
            + "URL_ADDR = #{menuUrl} "
            + "WHERE LCLSF_CD = #{menuCode}")
    int updateMajorCatgory(PageMajorCatgoryEntity pagerCatgoryEntity);
	
	@Delete("DELETE FROM MAJOR_CATEGORY WHERE LCLSF_CD=#{menuCode}")
    int deleteMajorCategoryMapper(String menuCode);
	
}
