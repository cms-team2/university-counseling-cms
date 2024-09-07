package com.counseling.cms.entity;

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
    private String categoryUsable;
    private int postViews;
    private String postingDate;
    private String userName;
    private String postUsable;
    private int boardNumber;


    public PostEntity() {
    	
    }
}
