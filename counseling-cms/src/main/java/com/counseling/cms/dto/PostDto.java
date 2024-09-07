package com.counseling.cms.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
	private String postTitle;
	private String fiexUsable;
	private String postUsable;
	private String boardNumber;
	private String postContent;
	private MultipartFile postFile;
}
