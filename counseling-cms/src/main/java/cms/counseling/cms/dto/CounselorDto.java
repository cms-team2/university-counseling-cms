package cms.counseling.cms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselorDto {
    private String facultyNumber;        // 사번
    private String facultyName;          // 이름
    private String facultyPart;       // 부서명
    private String facultyClassification;     // 교직원 구분
    private String facultyEmail;       // 이메일
    private String facultyTel;     // 전화번호
    private String facultyAppointmentDate;    // 임용일자
    private String counselCategory;  // 상담분류 (C_SCLSF_NM)
    private String filePath;             // 파일 경로 (추가)
    private String fileName;             // 파일 이름 (추가)
}
