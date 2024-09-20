package com.counseling.cms.entity;

import com.counseling.cms.dto.PostDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEntity {
	private int postNumber;
    private String postTitle;
    private String postContent;
    private int fileNumber;
    private String fixedUsable;
    private String category;
    private int postViews = 0;
    private String postingDate;
    private String userName;
    private String postUsable;
    private int boardNumber;
    private String replyExists;
    // 기본 생성자
    public PostEntity() {
    }

    // DTO를 사용한 생성자
    public PostEntity(PostDto postDto, int fileNumber) {
        this.postTitle = postDto.getPostTitle();
        this.postContent = postDto.getPostContent();
        this.fileNumber = fileNumber;
        this.fixedUsable = postDto.getFixedUsable();
        this.category = postDto.getCategory();
        this.postViews = 0; // 초기화
        this.userName = postDto.getUserName();
        this.postUsable = postDto.getPostUsable();
        this.boardNumber = postDto.getBoardNumber();
    }
}

