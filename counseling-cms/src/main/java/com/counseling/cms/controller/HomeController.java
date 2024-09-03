package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.ServletRequest;

@Controller
public class HomeController {

    // 환영 페이지
    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";  // "welcome.html" 템플릿을 반환
    }

    // 홈 페이지
    @GetMapping("/")
    public String showHomePage() {
        return "index";  // "index.html" 템플릿을 반환
    }

    // 사용자 로그인 페이지
    @GetMapping("/user/login")
    public String showUserLoginPage() {
        return "user/userLogin";  // "user/userLogin.html" 템플릿을 반환
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/user/find")
    public String showFindPasswordPage() {
        return "pw/find";  // "pw/find.html" 템플릿을 반환
    }

    // 비밀번호 변경 페이지
    @GetMapping("/user/change")
    public String showChangePasswordPage() {
        return "pw/change";  // "pw/change.html" 템플릿을 반환
    }
    
    // 상담사 목록 페이지
    @GetMapping("/counselor/counselling-record-list")
    public String counsellingRecordList() {
        return "counselor/counsellingRecordList"; 
    }
    
    // 상담사 목록 페이지
    @GetMapping("/counselor/consultant-list")
    public String consultantList() {
        return "counselor/consultantList"; 
    }
    
    // 상담사 신청 설명 페이지
    @GetMapping("/counselor/apply-description")
    public String showApplyDescriptionPage() {
        return "counselor/applyDescription";  // "counselor/applyDescription.html" 템플릿을 반환
    }
    
    // 상담 기록 페이지
    @GetMapping("/counselor/counselling-record")
    public String showCounsellingRecordPage() {
        return "counselor/counsellingRecord";  // "counselor/counsellingRecord.html" 템플릿을 반환
    }
    
    // 월별 캘린더 페이지
    @GetMapping("/counselor/monthly-calendar")
    public String showMonthlyCalendar() {
        return "counselor/monthlyCalendar";  // "counselor/monthlyCalendar.html" 템플릿을 반환
    }

    // 주별 캘린더 페이지
    @GetMapping("/counselor/weekly-calendar")
    public String showWeeklyCalendar() {
        return "counselor/weeklyCalendar";  // "counselor/weeklyCalendar.html" 템플릿을 반환
    }

    // 상담사 캘린더 페이지
    @GetMapping("/counselor/calendar")
    public String showCounselorCalendar() {
        return "counselor/calendar";  // "counselor/calendar.html" 템플릿을 반환
    }
    
    // 상담사 목록 페이지
    @GetMapping("/counselor/notice/list")
    public String showCounselorNoticeList(Model model) {
        model.addAttribute("boardName", "상담사 공지사항");
        model.addAttribute("boardId", "counselorBoard");
        
        return "counselor/board/basic/list";
    }

    // 게시판 목록 페이지
    @GetMapping("/board/{boardnm}/list")
    public String showBoardList(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
        
        if ("FAQ".equals(boardName)) {
            return "counselor/board/faq/list";  // FAQ 게시판 목록 템플릿을 반환
        } else {
            return "counselor/board/basic/list";  // 기본 게시판 목록 템플릿을 반환
        }
    }

    // 게시판 글 작성 페이지
    @GetMapping("/board/{boardnm}/write")
    public String showBoardWritePage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        model.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/write";  // 게시판 글 작성 템플릿을 반환
    }
    
    // 게시판 글 수정 페이지
    @GetMapping("/board/{boardnm}/modify")
    public String showBoardModifyPage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        model.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/modify";  // 게시판 글 수정 템플릿을 반환
    }

    // 게시판 글 보기 페이지
    @GetMapping("/board/{boardnm}/view")
    public String showBoardViewPage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
        return "counselor/board/basic/view";  // 게시판 글 보기 템플릿을 반환
    }

    // 관리자 관련 페이지

    @GetMapping("/admin/counselor-list")
    public String showCounselorListPage() {
        return "admin/counselorList";  // 관리자 상담사 목록 템플릿을 반환
    }

    @GetMapping("/admin/admin-list")
    public String showAdminListPage() {
        return "admin/adminList";  // 관리자 목록 템플릿을 반환
    }

    @GetMapping("/admin/banner-list")
    public String showBannerListPage() {
        return "admin/bannerList";  // 배너 목록 템플릿을 반환
    }

    @GetMapping("/admin/banner-create")
    public String showBannerCreatePage() {
        return "admin/bannerCreate";  // 배너 생성 템플릿을 반환
    }

    @GetMapping("/admin/menu-list1")
    public String showMenuListPage1() {
        return "admin/menuList-M";  // 메뉴 목록 (M) 템플릿을 반환
    }

    @GetMapping("/admin/menu-list2")
    public String showMenuListPage2() {
        return "admin/menuList-C";  // 메뉴 목록 (C) 템플릿을 반환
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "admin/adminLogin";  // 관리자 로그인 템플릿을 반환
    }
    
    @GetMapping("/admin/statistics")
    public String counselingChartPage() {
    	return "admin/statistics";
    }
}
