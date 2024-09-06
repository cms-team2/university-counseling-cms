package com.counseling.cms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.PostDto;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.mapper.AdminBoardMapper;
import com.counseling.cms.mapper.FileMapper;
import com.counseling.cms.utility.FileUtility;

@Service
public class AdminBoardService {
	@Autowired
	private AdminBoardMapper adminBoardMapper;
	@Autowired
	private FileMapper fileMapper;
	@Autowired
	private FileUtility fileUtility;
	
	public Map<String, Object> getPostService(int boardNumber, int page) {
		int pageSize = 10;
		int totalPosts = adminBoardMapper.countPosts(boardNumber);
		int totalPages = (int)Math.ceil((double)(totalPosts/pageSize));		
		int start = (page - 1) * pageSize;
	

		Map<String, Object> result = new HashMap<>();
        result.put("posts", adminBoardMapper.getPostMapper(boardNumber, start, pageSize));
        result.put("totalPages", totalPages);
        
        return result;
		
	}
	
	public ResponseEntity<String> createPostService(PostDto postDto){
		MultipartFile file[] = postDto.getPostFile();
		Integer fileNumber = fileUtility.createFileCode();
		System.out.println(fileUtility.ftpImageUpload(file[0]));
		
		 for(int i = 0 ; i < file.length ; i++) { 
			 FileEntity fileEntity = new
			 FileEntity(fileUtility, file[i], fileNumber);
			 fileMapper.createFile(fileEntity);
		 }
		 
		
			
		return null;
	}
}
