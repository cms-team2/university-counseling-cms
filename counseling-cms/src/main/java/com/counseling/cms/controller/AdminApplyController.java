package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counseling.cms.service.AdminApplyService;

import jakarta.annotation.Resource;

@Controller
public class AdminApplyController {
	
	   @Resource(name = "admin_apply_module")
	    private AdminApplyService aas;
	
	   @PostMapping("/admin/apply_allotment")
	    public ResponseEntity<String> AppliAllotment(
	            @RequestParam("EMP_NO") String empNo,
	            @RequestParam("STDNT_NO") String stdntNo,
	            @RequestParam("DSCSNRSVTYMD") String dscsnRsvtYmd) {
	    	String result = aas.counslerAllotment(empNo,stdntNo,dscsnRsvtYmd);
	    		return ResponseEntity.ok(result); 
	    }
	   
	    @GetMapping("/admin/apply-list")
	    public String applyList(@RequestParam(required = false) String keyword,@RequestParam(required = false) String type,@RequestParam(required = false) String status,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size,Model model
	    ) {
	        // 검색과 정렬을 모두 포함한 메서드 호출
	        Map<String, Object> result = aas.applyList(keyword, type, status, page, size);
	        model.addAttribute("apply_list", result.get("results"));
	        model.addAttribute("totalCount", result.get("totalCount"));
	        model.addAttribute("totalPages", result.get("totalPages"));
	        model.addAttribute("currentPage", result.get("currentPage"));
	        model.addAttribute("size", result.get("pageSize"));
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("type", type);
	        model.addAttribute("status", status);
	        return "admin/applyList"; 
	    }
	   
	    

	    @GetMapping("/admin/apply_list_api_data/{data}")
	    @ResponseBody
	    public ResponseEntity<Map<String, Object>> adminApplyApi(@PathVariable(name = "data") String studentId) {
	    	Map<String, Object> counsler = aas.getCounslerList(studentId);
	    	return ResponseEntity.ok(counsler);
	    }
}
