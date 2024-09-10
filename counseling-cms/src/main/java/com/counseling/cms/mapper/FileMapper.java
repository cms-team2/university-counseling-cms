package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.counseling.cms.entity.FileEntity;

@Mapper
public interface FileMapper {

	@Insert("INSERT INTO COM_FILE (`FILE_NO`, `FILE_SEQ`, `FILE_NM`, `FILE_PATH`, `FILE_SZ`) VALUES (#{fileNumber}, #{uuid}, #{fileName}, #{filePath}, #{fileSize})")
	int createFile(FileEntity fileEntity);
	
	@Select("SELECT FILE_NM FROM COM_FILE WHERE FILE_NO=#{fileNumber}")
	String getfileName(int fileNumber);
}
