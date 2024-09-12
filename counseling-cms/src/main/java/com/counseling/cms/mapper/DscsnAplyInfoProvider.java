package com.counseling.cms.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.annotations.Param;

public class DscsnAplyInfoProvider {

    public String selectByFlNM(@Param("keyword") String keyword, @Param("status") String status) {
        return new SQL() {{
            SELECT("*");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("FLNM LIKE CONCAT('%', #{keyword}, '%')");

            if (status != null) {
                switch (status) {
                    case "gender":
                        ORDER_BY("GNDR DESC");
                        break;
                    case "name":
                        ORDER_BY("FLNM ASC");
                        break;
                    case "apply":
                    default:
                        ORDER_BY("C_APLY_DT ASC"); // Assuming APPLY_DATE is the field for 신청일순
                        break;
                }
            } else {
                ORDER_BY("C_APLY_DT ASC"); // Default ordering
            }
        }}.toString();
    }

    public String selectByStdntNo(@Param("keyword") String keyword, @Param("status") String status) {
        return new SQL() {{
            SELECT("*");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("STDNT_NO LIKE CONCAT('%', #{keyword}, '%')");

            if (status != null) {
                switch (status) {
                    case "gender":
                        ORDER_BY("GNDR DESC");
                        break;
                    case "name":
                        ORDER_BY("FLNM ASC");
                        break;
                    case "apply":
                    default:
                        ORDER_BY("C_APLY_DT ASC"); // Assuming APPLY_DATE is the field for 신청일순
                        break;
                }
            } else {
                ORDER_BY("C_APLY_DT ASC"); // Default ordering
            }
        }}.toString();
    }

    public String selectByList(@Param("status") String status) {
        return new SQL() {{
            SELECT("*");
            FROM("VIEW_DSCSN_APLY_INFO");

            if (status != null) {
                switch (status) {
                    case "gender":
                        ORDER_BY("GNDR DESC");
                        break;
                    case "name":
                        ORDER_BY("FLNM ASC");
                        break;
                    case "apply":
                    default:
                        ORDER_BY("C_APLY_DT ASC"); // Assuming APPLY_DATE is the field for 신청일순
                        break;
                }
            } else {
                ORDER_BY("C_APLY_DT ASC"); // Default ordering
            }
        }}.toString();
    }
}
