package com.counseling.cms.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.mapper.SelfDiagnosisMapper;

@Service
public class SelfDiagnosisService {

    @Autowired
    private SelfDiagnosisMapper selfDiagnosisMapper;

    @Autowired
    private GoogleSheetsService googleSheetsService;

    // 최신 응답의 총점 계산
    public int calculateLatestTotalScore() throws IOException, GeneralSecurityException {
        // 구글 스프레드시트에서 가장 최신 응답 가져오기
        List<Object> latestAnswers = googleSheetsService.getLatestGoogleSheetData();
        
        // 총점 계산
        int totalScore = 0;
        for (int i = 0; i < latestAnswers.size(); i++) {
            int answerScore = selfDiagnosisMapper.getAnswerScore(i + 1, Integer.parseInt(latestAnswers.get(i).toString()));
            totalScore += answerScore;
        }
        return totalScore;
    }

    // 총점에 따른 결과 설명 가져오기
    public String getResultExplanation(int totalScore) {
        return selfDiagnosisMapper.getResultExplanation(1, totalScore);  // INSP_NO는 1로 가정
    }


    
    
}
