package com.counseling.cms.controller;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.counseling.cms.entity.UserBoardEntity;
import com.counseling.cms.service.UserBoardService;
import com.counseling.cms.utility.FileUtility;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserBoardController {

	@Autowired
	private FileUtility FileUtility; 
	
    @Resource(name = "UserBoardService")
    private UserBoardService userBoardService;

    @GetMapping("/board/{boardnm}/view")
    public String showBoardViewPage(@PathVariable String boardnm, @RequestParam String pstNo, Model model) {
    	
    	UserBoardEntity dto = userBoardService.getBoardView(boardnm, pstNo);
    	if("inquiry".equals(boardnm)) {	
    		model.addAttribute("comment",userBoardService.getComment(dto.getCmntNo()));
    	}
    	
    	if("counselorBoard".equals(boardnm)) {
    		model.addAttribute("boardName", "상담사 공지사항");
    	}else {
    		model.addAttribute("boardName", boardnm);
    	}
    	
        model.addAttribute("getBoardView", dto);
        return "counselor/board/basic/view";  
    }

        @GetMapping("/board/{boardnm}/write")
        public String showBoardWritePage(@PathVariable String boardnm, @RequestParam(required = false) String aplyNo, Model model, HttpServletRequest req) {
            model.addAttribute("boardName", boardnm);
            model.addAttribute("userName", userBoardService.getUserName(req));
            return "counselor/board/inquiry/write"; 
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
    
    @GetMapping("/user/downloadFile")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<UrlResource> downloadFile(@RequestParam String fileSeq, HttpServletResponse res) throws MalformedURLException {
		return FileUtility.downloadFile(fileSeq, res);	
	}
    
    
    @PostMapping("/user/{boardId}/write")
    public String boardWrite(
            @PathVariable("boardId") String boardId, 
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("author") String author,
            @RequestParam("content") String content,
            @RequestParam("file") MultipartFile[] file,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {  

        String callback = userBoardService.filesave(file, req, title, content, category, author, boardId);
        
        if ("ok".equals(callback)) { 
            redirectAttributes.addFlashAttribute("message", "저장 완료되었습니다.");
            return "redirect:/board/" + boardId + "/list";  
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "오류로 인해 실패하였습니다.");
            return "redirect:/board/" + boardId + "/list";
            }
    }

    
    @DeleteMapping("/board/inquiry/delete")
    public ResponseEntity<String> deleteborad(@RequestParam String pstNo) {
        int call = userBoardService.deleteboard(pstNo);
        String result="시스템 오류로 인해 잠시 후 다시 시도해주세요.";
        if(call>0) {
        	result="ok";
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/user/{boardId}/modifyok")
    public ResponseEntity<String> modifyBoardPost(
            @PathVariable("boardId") String boardId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam("category") String category,
            @RequestParam("author") String author,
            @RequestParam("pstNo") String pstNo) {
        
        String callback = userBoardService.modifyBoard(pstNo, file, title, content, category, author);
        if ("정상적으로 수정 되었습니다.".equals(callback)) {
            return ResponseEntity.ok("게시글이 수정되었습니다."); 
        } else {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                                 .body("게시글 수정에 실패했습니다."); 
        }
    }
    
    
    @GetMapping("/board/{boardnm}/modify")
    public String showBoardModifyPage(@PathVariable String boardnm, Model model,@RequestParam String pstNo,HttpServletRequest req) {
    	model.addAttribute("getModifyView",userBoardService.getBoardView(boardnm, pstNo));
        model.addAttribute("boardName", boardnm);
        model.addAttribute("getAuthrt",userBoardService.getAuthrt(req));
        return "counselor/board/inquiry/modify";  
    }
    
    
    @GetMapping("/counselor/notice/list")
    public String showCounselorNoticeList(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int limit,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String category,
                                           HttpServletRequest req,
                                           Model model) {
        
        List<UserBoardEntity> dto = userBoardService.getBoardList("1", limit, (page - 1) * limit, req, keyword, category);
        

        model.addAttribute("boardName", "상담사 공지사항");
        model.addAttribute("boardId", "counselorBoard");
        model.addAttribute("getCounslerList", dto);

        

        int totalItems = userBoardService.getCounslerCount(keyword, category);
        

        int totalPages = (int) Math.ceil((double) totalItems / limit);
  
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", limit);
        model.addAttribute("keyword", keyword);

        return "counselor/board/basic/counsler";
    }


    
}
