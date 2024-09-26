package com.counseling.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.PageBannerEntity;
import com.counseling.cms.mapper.UserBannerMapper;

@Service
public class UserBannerService {
	@Autowired
	private UserBannerMapper userBannerMapper;
	
	public Map<String, Object> getUserBanner() {
		Map<String, Object> totalResult = new HashMap<>();
		totalResult.put("list", userBannerMapper.getUserBannerMapper());
		
		return totalResult;
	}
}
