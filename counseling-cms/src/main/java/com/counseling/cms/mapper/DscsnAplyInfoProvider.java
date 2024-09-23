package com.counseling.cms.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.annotations.Param;

public class DscsnAplyInfoProvider {

    public String selectByFlNM(@Param("keyword") String keyword, @Param("status") String status, @Param("offset") int offset, @Param("size") int size) {
        return new SQL() {{
            SELECT("*");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("FLNM LIKE CONCAT('%', #{keyword}, '%')");
            WHERE("C_SCLSF_NM <> '교수상담'");

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
                        ORDER_BY("C_APLY_DT ASC");
                        break;
                }
            } else {
                ORDER_BY("C_APLY_DT ASC");
            }
            LIMIT("#{size}");
            OFFSET("#{offset}");
        }}.toString();
    }

    public String selectByStdntNo(@Param("keyword") String keyword, @Param("status") String status, @Param("offset") int offset, @Param("size") int size) {
        return new SQL() {{
            SELECT("*");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("STDNT_NO LIKE CONCAT('%', #{keyword}, '%')");
            WHERE("C_SCLSF_NM <> '교수상담'");

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
                        ORDER_BY("C_APLY_DT ASC");
                        break;
                }
            } else {
                ORDER_BY("C_APLY_DT ASC");
            }
            LIMIT("#{size}");
            OFFSET("#{offset}");
        }}.toString();
    }

    public String selectByList(@Param("status") String status, @Param("offset") int offset, @Param("size") int size) {
        return new SQL() {{
            SELECT("*");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("C_SCLSF_NM <> '교수상담'");

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
                        ORDER_BY("C_APLY_DT ASC");
                        break;
                }
            } else {
                ORDER_BY("C_APLY_DT ASC");
            }
            LIMIT("#{size}");
            OFFSET("#{offset}");
        }}.toString();
    }

    public String countByFlNM(@Param("keyword") String keyword, @Param("status") String status) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("FLNM LIKE CONCAT('%', #{keyword}, '%')");
            WHERE("C_SCLSF_NM <> '교수상담'");
        }}.toString();
    }

    public String countByStdntNo(@Param("keyword") String keyword, @Param("status") String status) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("STDNT_NO LIKE CONCAT('%', #{keyword}, '%')");
            WHERE("C_SCLSF_NM <> '교수상담'");
        }}.toString();
    }

    public String countByList(@Param("status") String status) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("VIEW_DSCSN_APLY_INFO");
            WHERE("C_SCLSF_NM <> '교수상담'");
        }}.toString();
    }
}
