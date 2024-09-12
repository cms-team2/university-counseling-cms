package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.counseling.cms.entity.PageMajorCatgoryEntity;
import com.counseling.cms.entity.PageSubCategoryEntity;

@Mapper
public interface PageSubCategoryMapper {
	@Select("SELECT COUNT(*) FROM SUBMENU_CATEGORY")
    int countSubCatgoryList();
	
	@Select("SELECT * FROM SUBMENU_CATEGORY")
	@Results({
		@Result(property="menuCode",column="SUB_LCLSF_CD"),
		@Result(property="majorMenuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="SUB_LCLSF_NM"),
		@Result(property="menuSequence",column="SUB_EXPSR_SEQ"),
		@Result(property="menuYn",column="SUB_EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
		@Result(property="menuDescription",column="SUB_EXPLN"),
	})
	List<PageSubCategoryEntity> selectAllSubCategoryMapper();
	
	@Select("SELECT * FROM SUBMENU_CATEGORY WHERE SUB_LCLSF_CD=#{menuCode} ORDER BY SUB_EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="SUB_LCLSF_CD"),
		@Result(property="majorMenuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="SUB_LCLSF_NM"),
		@Result(property="menuSequence",column="SUB_EXPSR_SEQ"),
		@Result(property="menuYn",column="SUB_EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
		@Result(property="menuDescription",column="SUB_EXPLN"),
	})
	PageSubCategoryEntity selectOneByCodeSubCategoryMapper(String menuCode);
	
	@Select("SELECT * FROM SUBMENU_CATEGORY WHERE LCLSF_CD=#{majorMenuCode} ORDER BY SUB_EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="SUB_LCLSF_CD"),
		@Result(property="majorMenuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="SUB_LCLSF_NM"),
		@Result(property="menuSequence",column="SUB_EXPSR_SEQ"),
		@Result(property="menuYn",column="SUB_EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
		@Result(property="menuDescription",column="SUB_EXPLN"),
	})
	List<PageSubCategoryEntity> selectByCodeSubCategoryMapper(String majorMenuCode);
	
	@Select("SELECT * FROM SUBMENU_CATEGORY" +
	        " WHERE SUB_LCLSF_NM LIKE CONCAT('%', #{searchText}, '%') ORDER BY SUB_EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="SUB_LCLSF_CD"),
		@Result(property="majorMenuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="SUB_LCLSF_NM"),
		@Result(property="menuSequence",column="SUB_EXPSR_SEQ"),
		@Result(property="menuYn",column="SUB_EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
		@Result(property="menuDescription",column="SUB_EXPLN"),
	})
    List<PageSubCategoryEntity> nameSubCatgoryList(String searchText);	
	
	@Select("SELECT * FROM SUBMENU_CATEGORY" +
	        " WHERE SUB_LCLSF_CD LIKE CONCAT('%', #{searchText}, '%') ORDER BY SUB_EXPSR_SEQ ASC")
	@Results({
		@Result(property="menuCode",column="SUB_LCLSF_CD"),
		@Result(property="majorMenuCode",column="LCLSF_CD"),
		@Result(property="menuName",column="SUB_LCLSF_NM"),
		@Result(property="menuSequence",column="SUB_EXPSR_SEQ"),
		@Result(property="menuYn",column="SUB_EXPSR_YN"),
		@Result(property="menuUrl",column="URL_ADDR"),
		@Result(property="menuDescription",column="SUB_EXPLN"),
	})
    List<PageSubCategoryEntity> codeSubCatgoryList(String searchText);	
	
	@Insert("INSERT INTO SUBMENU_CATEGORY (`SUB_LCLSF_CD`, `LCLSF_CD`, `SUB_LCLSF_NM`, `SUB_EXPSR_SEQ`, `SUB_EXPSR_YN` , `URL_ADDR` , `SUB_EXPLN`) "
			+ "VALUES (#{menuCode}, #{majorMenuCode}, #{menuName}, #{menuSequence},"
			+ "#{menuYn}, #{menuUrl} , #{menuDescription})")
	int insertSubCatgory(PageSubCategoryEntity pageSubCategoryEntity);
	
    @Update("UPDATE SUBMENU_CATEGORY SET "
            + "SUB_LCLSF_NM = #{menuName}, "
            + "SUB_EXPSR_SEQ = #{menuSequence}, "
            + "SUB_EXPSR_YN = #{menuYn}, "
            + "URL_ADDR = #{menuUrl}, "
            + "SUB_EXPLN = #{menuDescription} "
            + "WHERE SUB_LCLSF_CD = #{menuCode}")
    int updateSubCatgory(PageSubCategoryEntity pageSubCategoryEntity);
    
	@Delete("DELETE FROM SUBMENU_CATEGORY WHERE SUB_LCLSF_CD=#{menuCode}")
    int deleteSubCategoryMapper(String menuCode);
	
}
