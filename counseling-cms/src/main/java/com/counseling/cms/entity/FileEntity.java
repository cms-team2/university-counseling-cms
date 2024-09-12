package com.counseling.cms.entity;

import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.utility.FileUtility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FileEntity {

	private FileUtility fileUtility;

	private int fileNumber;
	private String uuid;
	private String fileName;
	private String filePath;
	private long fileSize;
	
	public void setFileEntity(FileUtility fileUtility, MultipartFile file, Integer fileNumber) {
		this.setFileNumber(fileNumber);
		this.setUuid(fileUtility.ftpImageUpload(file));
		this.setFileName(file.getOriginalFilename());
		this.setFilePath(fileUtility.createFilePath(file, this.getUuid()));
		this.setFileSize(file.getSize());
	}

}
