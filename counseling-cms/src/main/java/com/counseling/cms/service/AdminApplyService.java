package com.counseling.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.StdntDscsnJoinDto;
import com.counseling.cms.entity.ApplyListEntity;
import com.counseling.cms.entity.CounslerListEntity;
import com.counseling.cms.mapper.DscsnAplyInfoRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("admin_apply_module")
public class AdminApplyService {

    @Autowired
    private DscsnAplyInfoRepo dr;

    public Map<String, Object> applyList(String keyword, String type, String status, int page, int size) {
        int offset = (page - 1) * size;

        // 검색 및 정렬에 따른 데이터 조회
        List<StdntDscsnJoinDto> results;
        int totalCount;

        if (type != null && keyword != null) {
            if ("FLNM".equals(type)) {
                results = dr.selectByFlNM(keyword, status, offset, size);
                totalCount = dr.countByFlNM(keyword, status);
            } else if ("STDNT_NO".equals(type)) {
                results = dr.selectByStdntNo(keyword, status, offset, size);
                totalCount = dr.countByStdntNo(keyword, status);
            } else {
                results = dr.selectByList(status, offset, size);
                totalCount = dr.countByList(status);
            }
        } else {
            results = dr.selectByList(status, offset, size);
            totalCount = dr.countByList(status);
        }

        // 페이지 및 정렬 처리
        int totalPages = (int) Math.ceil((double) totalCount / size);
        int currentPage = page;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("results", results);
        resultMap.put("totalCount", totalCount);
        resultMap.put("totalPages", totalPages);
        resultMap.put("currentPage", currentPage);
        resultMap.put("pageSize", size);

        return resultMap;
    }

    public int getTotalCount(String keyword, String type, String status) {
        if ("FLNM".equals(type)) {
            return dr.countByFlNM(keyword, status);
        } else if ("STDNT_NO".equals(type)) {
            return dr.countByStdntNo(keyword, status);
        }
        return dr.countByList(status);
    }

    public Map<String, Object> getCounslerList(String stdntId) {
        ApplyListEntity details = dr.getApplyListMapper(stdntId);
        List<CounslerListEntity> call = new ArrayList<>();
        List<String> data = dr.getCounslerList(details.getDscsnRsvtYmd());

        if (!data.isEmpty() && !"교수상담".equals(details.getCSclsfNm())) {
            call = dr.getCounsler(data);
        } else if (!"교수상담".equals(details.getCSclsfNm())) {
            call = dr.getCounslerAll();
        }

        Map<String, Object> allData = new HashMap<>();
        allData.put("details", details);
        allData.put("counsler", call);
        return allData;
    }

    public String counslerAllotment(String empNo, String stdntNo, String dscsnRsvtYmd) {
        String result = "";
        try {
            int callback = dr.putDscsnAllotment(empNo, stdntNo, dscsnRsvtYmd);
            result = (callback > 0) ? "ok" : "no";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
