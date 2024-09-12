package com.counseling.cms.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageBannerDto {
	private Integer bnr_seq;
	private String bnr_ttl,bnr_cn,bnr_pstg_yn;
	private MultipartFile fileInput;
}
