package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SelfDiagnosisMapper {

    // 각 문항별로 답변에 대한 점수 가져오기
    @Select("SELECT INSP_ANS_SCR FROM INSP_ANS_INFO WHERE INSP_QITEM_NO = #{qitemNo} AND INSP_ANS_NO = #{ansNo}")
    int getAnswerScore(int qitemNo, int ansNo);

    // 총점에 따른 결과 설명 가져오기
    @Select("SELECT INSP_RSLT_CN FROM RSLT_EXPLN_INFO WHERE INSP_NO = #{inspNo} AND #{totalScore} BETWEEN CRTR_BGNG_SCR AND CRTR_END_SCR")
    String getResultExplanation(int inspNo, int totalScore);

    // 모든 답변 정보 가져오기
    @Select("SELECT INSP_ANS_TTL, INSP_ANS_SCR FROM INSP_ANS_INFO")
    List<Map<String, Object>> getAllAnswerInfo();
    
    // 다음 진단 진행 번호 가져오기
    @Select("SELECT IFNULL(MAX(INSP_PRGRS_NO), 0) + 1 FROM SELF_PSY_INSP_RSLT")
    int getNextInspPrgrsNo();

    // SELF_PSY_INSP_RSLT 테이블에 결과 저장
    @Insert("INSERT INTO SELF_PSY_INSP_RSLT (INSP_PRGRS_NO, INSP_NO, STDNT_NO, TOT_SCR) VALUES (#{inspPrgrsNo}, #{inspNo}, #{stdntNo}, #{totalScore})")
    void insertSelfPsyInspRslt(int inspPrgrsNo, int inspNo, String stdntNo, int totalScore);

    // INSP_ANS_RSLT 테이블에 답변 결과 저장
    @Insert("INSERT INTO INSP_ANS_RSLT (INSP_PRGRS_NO, STDNT_NO, INSP_NO, INSP_QITEM_NO, INSP_ANS_NO, INSP_ANS_SCR) VALUES (#{inspPrgrsNo}, #{stdntNo}, #{inspNo}, #{qitemNo}, #{ansNo}, #{score})")
    void insertInspAnsRslt(int inspPrgrsNo, String stdntNo, int inspNo, int qitemNo, int ansNo, int score);    
    
    
}
