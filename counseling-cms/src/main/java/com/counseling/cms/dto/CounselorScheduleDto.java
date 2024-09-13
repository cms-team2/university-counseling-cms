package com.counseling.cms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselorScheduleDto {
    private String day;             // 일
    private String counselorName;   // 상담사 이름
    private String reservationDate; // 상담 예약일시 (DSCSN_RSVT_YMD)
}
