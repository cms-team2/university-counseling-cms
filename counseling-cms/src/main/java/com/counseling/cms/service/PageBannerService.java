package com.counseling.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.AdminBannerDto;
import com.counseling.cms.entity.AdminBannerEntity;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.mapper.FileMapper;
import com.counseling.cms.mapper.PageBannerMapper;
import com.counseling.cms.utility.FileUtility;

@Service
public class PageBannerService {
	@Autowired
	private PageBannerMapper pageBannerMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private FileUtility fileUtility;
	
	public ResponseEntity<String> createBannerService(AdminBannerDto adminBannerDto) {
		Integer fileNumber = this.fileUtility.createFileCode();
		FileEntity fileEntity = new FileEntity(fileUtility, adminBannerDto.getFileInput(), fileNumber); 
		int fileDbResult = fileMapper.createFile(fileEntity);

		if(fileDbResult > 0) {
			AdminBannerEntity adminBannerEntity = new AdminBannerEntity(adminBannerDto,fileNumber);
			int bannerInsertResult = pageBannerMapper.insertBanner(adminBannerEntity);			
			
			if(bannerInsertResult>0) {
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.status(601).body("배너 저장 실패");	
			}
			
		}else {
			return ResponseEntity.status(602).body("파일 저장 실패");
		}
	}

	
    public Map<String,Object> getBannerList() {
    	
//		Map<String, Object> result = new HashMap<>();
//      result.put("posts", pageBannerMapper.getBannerList());
        
    	try {
    		System.out.println(pageBannerMapper.countBannerList());
    		List<AdminBannerEntity> abc = pageBannerMapper.getBannerList();
    		System.out.println(abc);
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
        return null;
    }
	

}
