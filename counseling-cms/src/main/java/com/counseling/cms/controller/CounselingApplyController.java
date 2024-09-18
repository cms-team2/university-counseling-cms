package com.counseling.cms.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.counseling.cms.dto.CounselingMenuDto;
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
		System.out.println(counseling);
		List<CounselingMenuDto> counselingData = counselingApplyService.getCounselingMenu();
		for (CounselingMenuDto counselingCode : counselingData) {
			if(AESUtility.decrypt(counseling, AESUtility.getSecretKeyFromBase64(KEY)).equals(counselingCode.getCounselingCode())) {
				System.out.println(counselingCode.getCounselingCode());
			}
		}
		model.addAttribute("counselingMenu", counselingData);
		return "user/application";
	}
}
