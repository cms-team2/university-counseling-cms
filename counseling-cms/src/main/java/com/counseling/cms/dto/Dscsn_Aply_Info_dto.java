package com.counseling.cms.dto;

import lombok.Data;

@Data
public class Dscsn_Aply_Info_dto {
    private int aplyNo;
    private int fileSn;
    private String stdntNo;
    private String cAplyDt;          // datetime -> String
    private String dscsnRsvtYmd;     // datetime -> String
    private String dscsnYn;          // char(1) -> String
    private String cEmpNo;           // char(10) -> String
    private String cPrgrsYn;         // char(1) -> String
    private String cTypeNm;          // varchar(300) -> String
    private String cSclsfCd;         // varchar(50) -> String
}
