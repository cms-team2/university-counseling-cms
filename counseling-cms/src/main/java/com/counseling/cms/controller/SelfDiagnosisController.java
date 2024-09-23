package com.counseling.cms.controller;

import com.counseling.cms.service.SelfDiagnosisService;
import com.counseling.cms.utility.GetUserInfoUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@Controller
public class SelfDiagnosisController {

    @Autowired
    private SelfDiagnosisService selfDiagnosisService;

    // 구글폼 응답 후 결과 페이지로 리다이렉트
    @GetMapping("/user/self-diagnosis-result")
    public String showDiagnosisResult(Model model) throws IOException, GeneralSecurityException {
        // 최신 결과의 총점 계산 및 저장
        int totalScore = selfDiagnosisService.calculateAndSaveLatestResult();

        // 총점에 따른 결과 설명 가져오기
        String resultExplanation = selfDiagnosisService.getResultExplanation(totalScore);

        // 모델에 총점과 결과 설명을 추가하여 결과 페이지로 전달
        model.addAttribute("totalScore", totalScore);
        model.addAttribute("resultExplanation", resultExplanation);

        return "/user/self_diagnosis/diagnosisResultPage";  // 결과 페이지로 이동
    }
    
    @GetMapping("/user/my-diagnosis-history")
    @ResponseBody
    public List<Map<String, Object>> getMyDiagnosisHistory() {
        String stdntNo = GetUserInfoUtility.getUserId();  // 사용자 학번 가져오기
        List<Map<String, Object>> history = selfDiagnosisService.getDiagnosisHistory(stdntNo);

        return history;  // 기록 반환
    }
 
    // 특정 진단 기록 조회 (진단 번호를 통해 조회)
    @GetMapping("/user/self-diagnosis-result/{inspPrgrsNo}")
    public String showDiagnosisResultByInspPrgrsNo(@PathVariable int inspPrgrsNo, Model model) {
        // 특정 진단 결과 가져오기
        Map<String, Object> diagnosisResult = selfDiagnosisService.getDiagnosisResult(inspPrgrsNo);
        
        // 진단 결과가 있는지 확인
        if (diagnosisResult != null) {
            // 총점과 결과를 모델에 추가
            model.addAttribute("totalScore", diagnosisResult.get("TOT_SCR"));
            model.addAttribute("resultExplanation", selfDiagnosisService.getResultExplanation((int) diagnosisResult.get("TOT_SCR")));
        } else {
            // 진단 결과가 없을 경우 처리
            model.addAttribute("totalScore", "N/A");
            model.addAttribute("resultExplanation", "해당 결과를 찾을 수 없습니다.");
        }
        
        return "/user/self_diagnosis/diagnosisResultPage";  // 결과 페이지로 이동
    }


}
