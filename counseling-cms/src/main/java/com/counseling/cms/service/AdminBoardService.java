package com.counseling.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.PostDto;
import com.counseling.cms.entity.PostEntity;
import com.counseling.cms.mapper.AdminBoardMapper;
import com.counseling.cms.utility.FileUtility;

@Service
public class AdminBoardService {
	@Autowired
	private AdminBoardMapper adminBoardMapper;
	
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
		String fileName = postDto.getPostFile().getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uuid = UUID.randomUUID().toString() + "." +extension;
		System.out.println(uuid);
		return null;
	}
}
