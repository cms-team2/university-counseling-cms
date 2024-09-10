package com.counseling.cms.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
	private String postTitle;
	private String fixedUsable;
	private String postUsable;
	private int boardNumber;
	private String postContent;
	private String userName;
	private String category;
	private MultipartFile postFile[];
}
