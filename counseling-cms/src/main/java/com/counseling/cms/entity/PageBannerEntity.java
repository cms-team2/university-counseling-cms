package com.counseling.cms.entity;

import com.counseling.cms.dto.PageBannerDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageBannerEntity {
	private Integer bnr_no,file_no,bnr_seq;
	private String bnr_ttl,bnr_cn,bnr_pstg_yn,bnr_ymd;
	private String file_path;
	
	public PageBannerEntity() {
		
	}
	
    public void adminBannerFileSave(PageBannerDto adminBannerDto,Integer fileNoResult) {
    	//랜덤 숫자 생성하는 로직 작성 utility 패키지안에 static으로 만들어서 재활용 하자
    	this.setFile_no(fileNoResult);
    	this.setBnr_seq(adminBannerDto.getBnr_seq());
    	this.setBnr_ttl(adminBannerDto.getBnr_ttl());
    	this.setBnr_cn(adminBannerDto.getBnr_cn());
    	this.setBnr_pstg_yn(adminBannerDto.getBnr_pstg_yn());
    };
    

}
