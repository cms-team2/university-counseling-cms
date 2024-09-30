package com.counseling.cms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.counseling.cms.dto.ApplyDto;
import com.counseling.cms.dto.CounselingMenuDto;
import com.counseling.cms.entity.ApplyStudentInfoEntity;
import com.counseling.cms.service.CounselingApplyService;
import com.counseling.cms.utility.AESUtility;

@Controller
public class CounselingApplyController {
	
	@Autowired
	private CounselingApplyService counselingApplyService;
	@Value("${AESKey}")
	private String KEY;
	
	@GetMapping("/user/apply")
	public String showApplyPage(String counseling, Model model) throws Exception {

		List<CounselingMenuDto> counselingData = counselingApplyService.getCounselingMenu();
		ApplyStudentInfoEntity studentInfo=counselingApplyService.getStudentInfo();
		for (CounselingMenuDto counselingCode : counselingData) {
			if(AESUtility.decrypt(counseling, AESUtility.getSecretKeyFromBase64(KEY)).equals(counselingCode.getCounselingCode())) {
				model.addAttribute("counselingCode", counselingCode.getCounselingCode());
				break;
			}
		}
		String today = LocalDate.now().toString();
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		String tomorrowString = tomorrow.toString();
		System.out.println(tomorrowString);
		
		model.addAttribute("tomorrow", tomorrowString);
		model.addAttribute("today", today);
		model.addAttribute("counselingMenu", counselingData);
		model.addAttribute("studentInfo", studentInfo);
		return "user/application";
	}
	
	@PostMapping("/counseling-apply")
	public ResponseEntity<String> createApplicationController(@ModelAttribute ApplyDto applyDto){
		return counselingApplyService.createApplicationService(applyDto);
	}
	
	
}
