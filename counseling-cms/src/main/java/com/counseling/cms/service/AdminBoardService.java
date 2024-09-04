package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.PostEntity;
import com.counseling.cms.mapper.AdminBoardMapper;

@Service
public class AdminBoardService {
	@Autowired
	private AdminBoardMapper adminBoardMapper;
	
	public List<PostEntity> getPostService(int boardNumber, int page) {
		int pageSize = 10;
		int totalPosts = adminBoardMapper.countPosts(boardNumber);
		int totalpages = (int)Math.ceil((double)(totalPosts/pageSize));		
		int start = (page - 1) * pageSize;
		
		return adminBoardMapper.getPostMapper(boardNumber, start, pageSize);
	}
}
