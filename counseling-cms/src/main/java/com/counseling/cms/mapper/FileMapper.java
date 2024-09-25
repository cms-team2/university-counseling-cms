package com.counseling.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.FileEntity;

@Mapper
public interface FileMapper {
	@Insert("INSERT INTO COM_FILE (`FILE_NO`, `FILE_SEQ`, `FILE_NM`, `FILE_PATH`, `FILE_SZ`) VALUES (#{fileNumber}, #{uuid}, #{fileName}, #{filePath}, #{fileSize})")
	int createFile(FileEntity fileEntity);
	
	@Select("SELECT * FROM COM_FILE WHERE FILE_NO=#{file_no}")
    @Results({
		@Result(property="fileNumber",column="FILE_NO"),
		@Result(property="uuid",column="FILE_SEQ"),
		@Result(property="fileName",column="FILE_NM"),
		@Result(property="filePath",column="FILE_PATH"),
		@Result(property="fileSize",column="FILE_SZ"),
	})
	List<FileEntity> selectFilePathMapper(Integer file_no);
	
	@Select("SELECT * FROM COM_FILE WHERE FILE_SEQ=#{file_no}")
    @Results({
		@Result(property="fileNumber",column="FILE_NO"),
		@Result(property="uuid",column="FILE_SEQ"),
		@Result(property="fileName",column="FILE_NM"),
		@Result(property="filePath",column="FILE_PATH"),
		@Result(property="fileSize",column="FILE_SZ"),
	})
	FileEntity selectAllbyFileNoMapper(Integer file_no);
	
	@Select("SELECT * FROM COM_FILE WHERE FILE_SEQ=#{file_seq}")
    @Results({
		@Result(property="fileNumber",column="FILE_NO"),
		@Result(property="uuid",column="FILE_SEQ"),
		@Result(property="fileName",column="FILE_NM"),
		@Result(property="filePath",column="FILE_PATH"),
		@Result(property="fileSize",column="FILE_SZ"),
	})
	FileEntity selectOneFile(String file_seq);
	
	@Select("SELECT FILE_NM FROM COM_FILE WHERE FILE_NO=#{fileNumber}")
	List<String> getfileName(int fileNumber);
	
	@Delete("DELETE FROM COM_FILE WHERE FILE_NO=#{fileNumber}")
	int deleteFile(int fileNumber);
}
