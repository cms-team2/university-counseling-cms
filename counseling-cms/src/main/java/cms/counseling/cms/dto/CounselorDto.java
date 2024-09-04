package cms.counseling.cms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselorDto {
    private String EMP_NO;        // 사번
    private String FLNM;          // 성명
    private String DEPT_NM;       // 부서명
    private String EMP_SE_NM;     // 교직원 구분
    private String EMP_EML;       // 이메일
    private String EMP_TELNO;     // 전화번호
    private String APNTMN_YMD;    // 임용일자
}
