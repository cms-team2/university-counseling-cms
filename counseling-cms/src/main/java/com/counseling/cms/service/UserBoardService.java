package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counseling.cms.dto.FaqBbsDto;
import com.counseling.cms.dto.PstDto;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.mapper.UserBoardMapper;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.HttpServletRequest;

@Service("UserBoardService")
public class UserBoardService {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserBoardMapper userBoardMapper;

    public PstDto getBoardView(String boardId, String pstNo) {
        int boardNo = getBoardNo(boardId);
        PstDto dto = userBoardMapper.getBoardView(boardNo, pstNo);
        int inquiryCount = Integer.parseInt(dto.getPstInqCnt()) + 1;
        userBoardMapper.upInqCnt(pstNo, inquiryCount);
        return dto;
    }
    
    public List<PstDto> getBoardList(String boardId, int limit, int offset, HttpServletRequest req, String keyword, String category) {
        int boardNo = getBoardNo(boardId);
        if (boardNo != -1 && boardNo != 5) {
            return userBoardMapper.getBoardList(boardNo, limit, offset, keyword, category);
        } else if (boardNo == 5) {
            String accessToken = CookieUtility.getCookie(req, "accessToken");
            String userId = jwtUtil.extractUserId(accessToken);
            return userBoardMapper.getInquiryList(boardNo, limit, offset, userId);
        }
        return List.of();
    }

    public int getBoardCount(String boardId, HttpServletRequest req, String keyword, String category) {
        int boardNo = getBoardNo(boardId);
        if (boardNo != -1 && boardNo != 5) {
            return userBoardMapper.getBoardCount(boardNo, keyword, category);
        } else if (boardNo == 5) {
            String accessToken = CookieUtility.getCookie(req, "accessToken");
            String userId = jwtUtil.extractUserId(accessToken);
            return userBoardMapper.getInquiryCount(boardNo, userId);
        }
        return 0;
    }

    public List<FaqBbsDto> getFaqList(int limit, int offset) {
        return userBoardMapper.getFaqList(limit, offset);
    }

    public int getFaqCount() {
        return userBoardMapper.getFaqCount();
    }

    private int getBoardNo(String boardId) {
        switch (boardId) {
            case "notice": return 2;
            case "review": return 4;
            case "inquiry": return 5;
            case "dataroom": return 6;
            case "program": return 7;
            default: return -1; 
        }
    }
}