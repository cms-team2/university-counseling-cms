package com.counseling.cms.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


        // DB에서 답변과 점수를 동적으로 가져와 매핑
        Map<String, Integer> answerScoreMap = loadAnswerScoreMapFromDB();

        // 총점 계산
        int totalScore = 0;

        // 첫 번째 값은 날짜이므로, 이를 제외하고 처리
        for (int i = 1; i < latestAnswers.size(); i++) {
            String answer = latestAnswers.get(i).toString();
            System.out.println("Answer: " + answer);

            // 응답을 점수로 변환
            if (answerScoreMap.containsKey(answer)) {
                totalScore += answerScoreMap.get(answer);
            } else {
                System.out.println("Invalid answer format for: " + answer);
            }
        }
        System.out.println("Total Score: " + totalScore);
        return totalScore;
    }

    // 총점에 따른 결과 설명 가져오기
    public String getResultExplanation(int totalScore) {
        return selfDiagnosisMapper.getResultExplanation(1, totalScore);  // INSP_NO는 1로 가정
    }

    // DB에서 답변 정보를 가져와 응답-점수 매핑
    private Map<String, Integer> loadAnswerScoreMapFromDB() {
        Map<String, Integer> answerScoreMap = new HashMap<>();
        List<Map<String, Object>> answerInfoList = selfDiagnosisMapper.getAllAnswerInfo();

        for (Map<String, Object> answerInfo : answerInfoList) {
            String answerTitle = answerInfo.get("INSP_ANS_TTL").toString();
            int answerScore = (Integer) answerInfo.get("INSP_ANS_SCR");
            answerScoreMap.put(answerTitle, answerScore);
        }

        return answerScoreMap;
    }
}
