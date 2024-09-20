package com.counseling.cms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SelfDiagnosisMapper {

    // 각 문항별로 답변에 대한 점수 가져오기
    @Select("SELECT INSP_ANS_SCR FROM INSP_ANS_INFO WHERE INSP_QITEM_NO = #{qitemNo} AND INSP_ANS_NO = #{ansNo}")
    int getAnswerScore(@Param("qitemNo") int questionItemNumber, @Param("ansNo") int answerNumber);

    // 총점에 따른 결과 설명 가져오기
    @Select("SELECT INSP_RSLT_CN FROM RSLT_EXPLN_INFO WHERE INSP_NO = #{inspNo} AND #{totalScore} BETWEEN CRTR_BGNG_SCR AND CRTR_END_SCR")
    String getResultExplanation(@Param("inspNo") int inspNo, @Param("totalScore") int totalScore);	

}
