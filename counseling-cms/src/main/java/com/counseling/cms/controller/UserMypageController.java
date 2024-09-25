package com.counseling.cms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.counseling.cms.entity.UserMyActivityEntity;
import com.counseling.cms.entity.UserMypageEntity;
import com.counseling.cms.service.UserMypageService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserMypageController {
	
	@Resource(name = "UserMypageService")
	private UserMypageService UMS;
	
	  @GetMapping("/user/mypage")
	    public String myPage(HttpServletRequest req,Model m) {
		  UserMypageEntity dto = UMS.dto(req);
		  Integer getDscsnCount= UMS.getDscsnCount(req);
		  m.addAttribute("count",getDscsnCount);
		  m.addAttribute("userInfo",dto);
	        return "user/mypage/mypage";
	    }
	  
	  @PostMapping("/user/getMyActivity")
	  public ResponseEntity<List<UserMyActivityEntity>> getMyActivity(HttpServletRequest req) {
	      List<UserMyActivityEntity> activities = UMS.getMyActivity(req);
	      return ResponseEntity.ok(activities);
	  }
	  
	  @PostMapping("/user/cancelActivity/{aplyNo}") 
	  public ResponseEntity<String> cancelDscsnActivity(@PathVariable String aplyNo) { // 파라미터 이름 변경
	       int callback= UMS.canselDscsn(aplyNo);
	       String result="";
	       if(callback>0) {
	    	   result="ok";
	       }else {
	    	   result="no";
	       }
	      return ResponseEntity.ok(result);
	  }


	  
}
