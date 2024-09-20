package com.counseling.cms.controller;

import com.counseling.cms.service.GoogleSheetsService;
import com.counseling.cms.service.SelfDiagnosisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class SelfDiagnosisController {

    @Autowired
    private SelfDiagnosisService selfDiagnosisService;

    // 구글폼 응답 후 결과 페이지로 리다이렉트
    @GetMapping("/user/self-diagnosis-result")
    public String showDiagnosisResult(Model model) throws IOException, GeneralSecurityException {
        // 가장 최신 결과의 총점 계산
        int totalScore = selfDiagnosisService.calculateLatestTotalScore();

        // 점수에 따른 결과 설명 가져오기
        String resultExplanation = selfDiagnosisService.getResultExplanation(totalScore);

        // 모델에 총점과 결과 설명을 추가하여 결과 페이지로 전달
        model.addAttribute("totalScore", totalScore);
        model.addAttribute("resultExplanation", resultExplanation);

        return "/user/self_diagnosis/diagnosisResultPage";  // 결과 페이지로 이동
    }
}
