package com.counseling.cms.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AdminBannerDto {
	private Integer BNR_SEQ,FILE_NO;
	private String BNR_TTL,BNR_CN,BNR_PSTG_YN;
	private MultipartFile fileInput;
}
