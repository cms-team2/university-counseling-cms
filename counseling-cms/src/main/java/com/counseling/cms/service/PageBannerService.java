package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.counseling.cms.dto.PageBannerDto;
import com.counseling.cms.entity.PageBannerEntity;
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
	

	public ResponseEntity<String> createBannerService(PageBannerDto adminBannerDto) {
		Integer fileNumber = this.fileUtility.createFileCode();
		FileEntity fileEntity = new FileEntity();
		fileEntity.setFileEntity(fileUtility, adminBannerDto.getFileInput(), fileNumber);
		int fileDbResult = fileMapper.createFile(fileEntity);

		if(fileDbResult > 0) {
			PageBannerEntity adminBannerEntity = new PageBannerEntity();
			adminBannerEntity.adminBannerFileSave(adminBannerDto, fileNumber);
			
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
	
	public Map<String, Object> getBannerModifyService(int bnr_no){
		PageBannerEntity bannerData = pageBannerMapper.selectAllbyBnrNo(bnr_no);

		FileEntity fileData = fileMapper.selectAllbyFileNoMapper(bannerData.getFile_no());
		String file_name = fileData.getFileName();
		int file_no = fileData.getFileNumber();
		String file_path = fileData.getFilePath();
		
		Map<String, Object> totalResult = new HashMap<>();
		totalResult.put("file_name", file_name);
		totalResult.put("file_no", file_no);
		totalResult.put("file_path", file_path);
		totalResult.put("bannerData", bannerData);
		
		return totalResult;
	}
	
	public ResponseEntity<String> submitBannerModifyService(PageBannerDto pageBannerDto,String old_filename,Integer old_filenumber,String old_filepath){
		if(old_filename.equals("Y")) { //삭제하고 새로 첨부
			int fileDbDeleteResult = fileMapper.deleteFile(old_filenumber);
			if(fileDbDeleteResult > 0) {
				FileEntity fileEntity = new FileEntity();
				fileEntity.setFileEntity(fileUtility, pageBannerDto.getFileInput(), old_filenumber);
				int fileDbResult = fileMapper.createFile(fileEntity);
				if(fileDbResult > 0) {
					int updateResult = UpdateBannerService(pageBannerDto, old_filenumber);
					if(updateResult > 0) {
						return ResponseEntity.ok().build();							
					}else {
						return ResponseEntity.status(601).body("배너 수정 실패");	
					}
				}else {
					return ResponseEntity.status(601).body("배너 수정 실패");
				}
			}else {
				return ResponseEntity.status(601).body("배너 수정 실패");
			}
		}else { //기존 첨부한 이미지 그대루
			int updateResult = UpdateBannerService(pageBannerDto, old_filenumber);
			if(updateResult > 0) {
				return ResponseEntity.ok().build();							
			}else {
				return ResponseEntity.status(601).body("배너 수정 실패");	
			}
		}
	}

	public int UpdateBannerService(PageBannerDto pageBannerDto, Integer old_filenumber) {
		PageBannerEntity pageBannerEntity = new PageBannerEntity(); 
		pageBannerEntity.adminBannerFileSave(pageBannerDto, old_filenumber);
		int updateResult = pageBannerMapper.updateBnr(pageBannerEntity);
		
		return updateResult;
	}
	
    public Map<String, Object> getBannerListService(int page,String searchText) {
		int pageSize = 10;
		int totalPosts = pageBannerMapper.countBannerList();
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize);		
		int start = (page - 1) * pageSize;
		
    	ArrayList<PageBannerEntity> listResult = pageBannerMapper.getBannerListMapper(start, pageSize,searchText);
    	
    	Map<String, Object> totalResult = new HashMap<>();
    	
    	totalResult.put("totalPages", totalPages);
    	totalResult.put("listResult", listResult);
    	totalResult.put("totalPosts", totalPosts);
    	totalResult.put("pageSize", pageSize);

		return totalResult;
    }
   
    public Map<String,String> selectBannerInfoService(int bnr_no){
    	PageBannerEntity selectFile = pageBannerMapper.selectAllbyBnrNo(bnr_no);
    	Integer file_no = selectFile.getFile_no();
    	String file_contents = selectFile.getBnr_cn();
		String file_path = "";
    	if(file_no != null) {
    		FileEntity fileData = fileMapper.selectAllbyFileNoMapper(file_no);
    		if(fileData !=null) {
    			file_path = fileData.getFilePath().split("CDN")[1];
    		}
    	}
    	
    	Map<String, String> selectResult = new HashMap<>();
    	selectResult.put("file_contents", file_contents);
    	selectResult.put("file_path", file_path);

    	return selectResult;
    }
    
    
    public ResponseEntity<String> deleteBannerListService(Integer bnr_no) {
    	try {
    		Integer file_no = pageBannerMapper.selectFileNoMapper(bnr_no);
    		List<FileEntity> file_list = fileMapper.selectFilePathMapper(file_no);
    		
    		int filelength = 0;
    		int fileDeleteResultCount = 0;
    		while(filelength < file_list.size()) {
    			String file_path = file_list.get(filelength).getFilePath();
    			
    			int fileDbdeleteResult = fileMapper.deleteFile(file_list.get(filelength).getFileNumber());
				if(fileDbdeleteResult > 0) {
					fileDeleteResultCount++;    					
				}
    			
    			filelength++;
    		}
    		
    		if(fileDeleteResultCount > 0) {
    			Integer bannerDbdeleteResult = pageBannerMapper.deleteBannerByBnrNoMapper(bnr_no);
				if(bannerDbdeleteResult > 0) {
	    			return ResponseEntity.ok().build();
				}else {
					return ResponseEntity.status(604).body("파일 삭제 실패");	
				}
    		}else {
    			return ResponseEntity.status(604).body("파일 삭제 실패");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(604).body("파일 삭제 실패");
    	}
    	
    }
	

}
