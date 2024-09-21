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
import com.counseling.cms.dto.ReplyDto;
import com.counseling.cms.entity.FaqEntity;
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
		int start = (page - 1) * pageSize;

		
		List<PostEntity> postList = new ArrayList<PostEntity>();
		List<FaqEntity> faqList = new ArrayList<FaqEntity>();
		
		if(!searchPart.equals("") && !searchValue.equals("")) {
			if(searchPart.equals("제목")) {
				totalPosts = adminBoardMapper.countSearchTitle(boardNumber, searchPart, searchValue);	
				postList=adminBoardMapper.getSearchTitleMapper(boardNumber, start, pageSize, searchPart, searchValue);
			} else if(searchPart.equals("숨김 여부")) {
				totalPosts = adminBoardMapper.countSearchPostUsable(boardNumber, searchPart, searchValue);	
				postList=adminBoardMapper.getSearchPostUsableMapper(boardNumber, start, pageSize, searchPart, searchValue);
			} else if(searchPart.equals("고정 여부")) {
				totalPosts = adminBoardMapper.countSearchFixedUsable(boardNumber, searchPart, searchValue);	
				postList=adminBoardMapper.getSearchFixedUsableMapper(boardNumber, start, pageSize, searchPart, searchValue);
			}
			
		} else {
			if(boardNumber == 3) {
				totalPosts = adminBoardMapper.countFaqs();
				faqList=adminBoardMapper.getFaqMapper(start, pageSize);
			}else {
				totalPosts = adminBoardMapper.countPosts(boardNumber);	
				postList=adminBoardMapper.getPostMapper(boardNumber, start, pageSize);
			}
			
		}

		int totalPages = (int)Math.ceil((double)totalPosts/pageSize);	
		if(totalPages==0) {totalPages=1;}
		
		Map<String, Object> result = new HashMap<>();
        if(boardNumber == 5) {
        	for (PostEntity post : postList) {
                // 답변 여부 확인
                String hasReply = adminBoardMapper.checkReplyExists(post.getPostNumber());
                post.setReplyExists(hasReply);
            }
        }
        
        if(boardNumber == 3) {
        	result.put("posts", faqList);
        }else {
        	result.put("posts",postList);        	
        }
        result.put("totalPages", totalPages);
        
        return result;
		
	}	

	public ResponseEntity<String> createPostService(PostDto postDto){
		MultipartFile file[] = postDto.getPostFile();
		Integer fileNumber = fileUtility.createFileCode();
		
		if(file[0].getSize()>0) {
			for(int i = 0 ; i < file.length ; i++) {
				try {
					FileEntity fileEntity = new FileEntity();
					fileEntity.setFileEntity(fileUtility, file[i], fileNumber);
					fileMapper.createFile(fileEntity);	
				}catch(Exception e) {
					return ResponseEntity.status(701).body("파일 저장 오류");
				}
				
			}
		}	
		
		PostEntity postEntity = new PostEntity(postDto, fileNumber);
		try {
			adminBoardMapper.createPost(postEntity);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.status(702).body("게시글 저장 실패");
		}

			
	}
	
	public ResponseEntity<Map<String, Object>> getOnePostService(String postNumber, String boardNumber) {
		Map<String, Object> postData = new HashMap<>();
		try {
			if(Integer.valueOf(boardNumber) == 3) {
				FaqEntity post = adminBoardMapper.getOneFaqMapper(Integer.valueOf(postNumber));
				postData.put("post", post);
			}else {
				PostEntity post = adminBoardMapper.getOnePostMapper(Integer.valueOf(postNumber));
				int fileNumber = post.getFileNumber();
				List<String> fileName = fileMapper.getfileName(fileNumber);
				String replyContent = adminBoardMapper.getReplyContentMapper(Integer.valueOf(postNumber));
				if(replyContent == null) replyContent = "";
				postData.put("post", post);
				postData.put("fileName", fileName);
				postData.put("replyContent", replyContent);
			}

			return ResponseEntity.ok(postData);
		}catch(Exception e) {
			return ResponseEntity.status(703).build();
		}
	}
	
	public ResponseEntity<String> modifyPostService(PostDto postDto){
		if(postDto.getFileDelete().equals("Y")) { //만약 기존 파일이 삭제 됐으면
			try{
				fileMapper.deleteFile(postDto.getFileNumber());
			}catch(Exception e) {
				return ResponseEntity.status(704).body("파일 삭제 오류");
			}
		}
		try {
			MultipartFile file[] = postDto.getPostFile();
			if(file[0].getSize()>0) {
				for(int i = 0 ; i < file.length ; i++) {
					try {
						FileEntity fileEntity = new FileEntity();
						fileEntity.setFileEntity(fileUtility, file[i], postDto.getFileNumber());
						fileMapper.createFile(fileEntity);	
					}catch(Exception e) {
						return ResponseEntity.status(701).body("파일 저장 오류");
					}
					
				}
			}

			adminBoardMapper.modifyPostMapper(postDto);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(705).body("게시글 수정 오류");
		}
		
	}
	
	public ResponseEntity<String> deleteCheckedPostService(List<Integer> postNumber){
		try {
			adminBoardMapper.deleteCheckedPostMapper(postNumber);
		}catch(Exception e) {
			return ResponseEntity.status(706).body("체크 게시글 삭제 실패");
		}
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<String> deletePostService(int postNumber){
		try {
			adminBoardMapper.deletePostMapper(postNumber);
		}catch(Exception e) {
			return ResponseEntity.status(707).body("게시글 삭제 실패");
		}
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<String> createReplyService(ReplyDto replyDto){
		try {
			adminBoardMapper.createReplyMapper(replyDto);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return ResponseEntity.status(708).body("문의 게시판 답변 실패");
		}
		
	}
	
	public ResponseEntity<String> createFaqService(PostDto postDto){
		try{
			adminBoardMapper.createFaq(postDto);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(709).body("FAQ 등록 실패");
		}
	}
	
	public ResponseEntity<String> modifyFaqService(PostDto postDto){
		try {
			adminBoardMapper.modifyFaqMapper(postDto);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(710).body("FAQ 수정 실패");
		}
	}
	
	
}
