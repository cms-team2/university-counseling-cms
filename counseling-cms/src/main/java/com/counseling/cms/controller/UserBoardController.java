package com.counseling.cms.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.dto.PstDto;
import com.counseling.cms.service.UserBoardService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserBoardController {

    @Resource(name = "UserBoardService")
    private UserBoardService userBoardService;

    @GetMapping("/board/{boardnm}/view")
    public String showBoardViewPage(@PathVariable String boardnm, @RequestParam String pstNo, Model model) {
        model.addAttribute("boardName", boardnm);
        PstDto dto = userBoardService.getBoardView(boardnm, pstNo);
        model.addAttribute("getBoardView", dto);
        return "counselor/board/basic/view";  
    }
    
    @GetMapping("/board/{boardnm}/list")
    public String showBoardList(@PathVariable String boardnm,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int limit,
                                 HttpServletRequest req, Model model,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String category) {
        
        String boardId = (String) req.getAttribute("boardId");
        model.addAttribute("boardName", boardnm);
        model.addAttribute("boardId", boardId);
        List<?> callback;
        int totalItems;
        if ("faq".equals(boardId)) {
            callback = userBoardService.getFaqList(limit, (page - 1) * limit);
            totalItems = userBoardService.getFaqCount();
        } else {
            callback = userBoardService.getBoardList(boardId, limit, (page - 1) * limit, req, keyword, category);
            totalItems = userBoardService.getBoardCount(boardId, req, keyword, category);
        }
        if(totalItems==-1) {
        model.addAttribute("logins","No");
        }
        model.addAttribute("getList", callback);
        model.addAttribute("total", totalItems);
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", limit);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        return "faq".equals(boardnm) ? "counselor/board/faq/list" : "counselor/board/basic/list";
    }
}
