package com.counseling.cms.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.mapper.SelfDiagnosisMapper;
import com.counseling.cms.utility.GetUserInfoUtility;

@Service
public class SelfDiagnosisService {

    @Autowired
    private SelfDiagnosisMapper selfDiagnosisMapper;

    @Autowired
    private GoogleSheetsService googleSheetsService;

    // 최신 응답의 총점 계산 및 저장
    public int calculateAndSaveLatestResult() throws IOException, GeneralSecurityException {
        // 학번 강제 지정 (예: '2018001234')
        String stdntNo = GetUserInfoUtility.getUserId();
        System.out.println(stdntNo);

        // 구글 스프레드시트에서 가장 최신 응답 가져오기
        List<Object> latestAnswers = googleSheetsService.getLatestGoogleSheetData();

        // DB에서 답변과 점수를 동적으로 가져와 매핑
        Map<String, Integer> answerScoreMap = loadAnswerScoreMapFromDB(); // answerScoreMap을 선언 및 초기화
        int totalScore = 0;

        // 첫 번째 값은 날짜이므로, 이를 제외하고 처리
        for (int i = 1; i < latestAnswers.size(); i++) {
            String answer = latestAnswers.get(i).toString();
            if (answerScoreMap.containsKey(answer)) {
                totalScore += answerScoreMap.get(answer);
            }
        }

        // 진단 결과를 테이블에 저장
        saveDiagnosisResult(stdntNo, totalScore, latestAnswers);

        return totalScore;
    }

    // 진단 결과 저장 메서드
    private void saveDiagnosisResult(String stdntNo, int totalScore, List<Object> latestAnswers) {
        int inspNo = 1;  // 진단 종류 (예: 1번 진단)

        // 진단 진행 번호 (AUTO_INCREMENT)
        int inspPrgrsNo = selfDiagnosisMapper.getNextInspPrgrsNo();

        // SELF_PSY_INSP_RSLT 테이블에 저장
        selfDiagnosisMapper.insertSelfPsyInspRslt(inspPrgrsNo, inspNo, stdntNo, totalScore);

        // INSP_ANS_RSLT 테이블에 각 답변 저장
        Map<String, Integer> answerScoreMap = loadAnswerScoreMapFromDB();  // 응답-점수 매핑 가져오기
        
        for (int i = 1; i < latestAnswers.size(); i++) {
            int qitemNo = i; // 문항 번호는 순차적이라고 가정
            String answer = latestAnswers.get(i).toString();  // 응답 내용

            if (answerScoreMap.containsKey(answer)) {
                int ansNo = answerScoreMap.get(answer);  // 응답에 해당하는 점수 번호
                int score = selfDiagnosisMapper.getAnswerScore(qitemNo, ansNo);  // 해당 응답의 점수 가져오기
                selfDiagnosisMapper.insertInspAnsRslt(inspPrgrsNo, stdntNo, inspNo, qitemNo, ansNo, score);
            } else {
                System.out.println("Invalid answer format for: " + answer);
            }
        }
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
