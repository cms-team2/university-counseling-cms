package com.counseling.cms.entity;

import com.counseling.cms.dto.AdminBannerDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AdminBannerEntity {
	private Integer FILE_NO,BNR_SEQ;
	private String BNR_TTL,BNR_CN,BNR_PSTG_YN;
	
	public AdminBannerEntity() {
		
	}
	
    public AdminBannerEntity(AdminBannerDto adminBannerDto,Integer fileNoResult) {
    	//랜덤 숫자 생성하는 로직 작성 utility 패키지안에 static으로 만들어서 재활용 하자
    	
    	adminBannerDto.setFILE_NO(fileNoResult);
    	
    	this.setFILE_NO(adminBannerDto.getFILE_NO());
    	this.setBNR_SEQ(Integer.valueOf(adminBannerDto.getBNR_SEQ()));
    	this.setBNR_TTL(adminBannerDto.getBNR_TTL());
    	this.setBNR_CN(adminBannerDto.getBNR_CN());
    	this.setBNR_PSTG_YN(adminBannerDto.getBNR_PSTG_YN());
    	
//    	this.setFile_no(fileNoResult);
//    	this.setBnr_seq(Integer.valueOf(adminBannerDto.getBnr_seq()));
//    	this.setBnr_ttl(adminBannerDto.getBnr_ttl());
//    	this.setBnr_cn(adminBannerDto.getBnr_cn());
//    	this.setBnr_pstg_yn(adminBannerDto.getBnr_pstg_yn());
    };
    

}
