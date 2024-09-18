package com.counseling.cms.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselorScheduleDto {
    private String counselorName;  // 상담사 이름
    private String reservationDate;  // 상담 예약 날짜 (DSCSN_RSVT_YMD)
    private String counselingType;   // 상담 방식 (C_TYPE_NM)
}
