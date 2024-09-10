package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.PostDto;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.entity.PostEntity;
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
	
	public Map<String, Object> getPostService(int boardNumber, int page, String searchPart, String searchValue) {
		int pageSize = 10;
		int totalPosts = 0;
		List<PostEntity> postList=new ArrayList<PostEntity>();
		
		if(!searchPart.equals("")) {
			
			if(searchPart.equals("제목")) {
				searchPart="PST_TTL";
				totalPosts = adminBoardMapper.countSearchTitle(boardNumber, searchPart, searchValue);	
				int start = (page - 1) * pageSize;
				postList=adminBoardMapper.getSearchTitleMapper(boardNumber, start, pageSize, searchPart, searchValue);
			} else if(searchPart.equals("숨김 여부")) {
				searchPart="PSTG_YN";
				totalPosts = adminBoardMapper.countSearchPostUsable(boardNumber, searchPart, searchValue);	
				int start = (page - 1) * pageSize;
				postList=adminBoardMapper.getSearchPostUsableMapper(boardNumber, start, pageSize, searchPart, searchValue);
			} else if(searchPart.equals("고정 여부")) {
				searchPart="PST_FIX";
				totalPosts = adminBoardMapper.countSearchFixedUsable(boardNumber, searchPart, searchValue);	
				int start = (page - 1) * pageSize;
				postList=adminBoardMapper.getSearchFixedUsableMapper(boardNumber, start, pageSize, searchPart, searchValue);
			}
			
		} else {
			totalPosts = adminBoardMapper.countPosts(boardNumber);	
			int start = (page - 1) * pageSize;
			postList=adminBoardMapper.getPostMapper(boardNumber, start, pageSize);
		}

		int totalPages = (int)Math.ceil((double)(totalPosts/pageSize));	
		
		Map<String, Object> result = new HashMap<>();
        result.put("posts",postList);
        result.put("totalPages", totalPages);
        
        return result;
		
	}
	
	public ResponseEntity<String> createPostService(PostDto postDto){
		MultipartFile file[] = postDto.getPostFile();
		Integer fileNumber = fileUtility.createFileCode();
		for(int i = 0 ; i < file.length ; i++) {
			try {
				FileEntity fileEntity = new FileEntity(fileUtility, file[i], fileNumber);
				fileMapper.createFile(fileEntity);	
			}catch(Exception e) {
				return ResponseEntity.status(701).body("파일 저장 오류");
			}
			
		}		
		
		PostEntity postEntity = new PostEntity(postDto, fileNumber);
		try {
			adminBoardMapper.createPost(postEntity);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(702).body("게시글 저장 실패");
		}
			
	}
	
	public ResponseEntity<PostEntity> getOnePostService(String postNumber) {
		
		try {
			return ResponseEntity.ok(adminBoardMapper.getOnePostMapper(Integer.valueOf(postNumber)));
		}catch(Exception e) {
			return ResponseEntity.status(703).build();
		}
	}
}
