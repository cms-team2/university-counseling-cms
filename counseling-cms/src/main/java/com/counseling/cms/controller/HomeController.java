package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.ServletRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";  
    }

    @GetMapping("/user/login")
    public String showUserLoginPage() {
        return "user/userLogin";
    }

    @GetMapping("/user/find")
    public String showFindPasswordPage() {
        return "pw/find";  
    }

    @GetMapping("/user/change")
    public String showChangePasswordPage() {
        return "pw/change";  
    }
   
    //심리상담 페이지
    @GetMapping("/user/counseling/counseling")
    public String showCounselingPage() {
        return "/user/counseling/counseling";
    }
   
    //익명상담 페이지
    @GetMapping("/user/counseling/anonymity")
    public String showAnonymityCounselingPage() {
        return "/user/counseling/anonymity";
    }
   
    //위기상담 페이지
    @GetMapping("/user/counseling/emergency")
    public String showEmergencyCounselingPage() {
    return "/user/counseling/emergency";
    }
   
    //진로상담 페이지
    @GetMapping("/user/academic/career")
    public String showCareerCounselingPage() {
        return "/user/counseling/career";
    }
   
    //취업상담 페이지
    @GetMapping("/user/academic/job")
    public String showJobCounselingPage() {
        return "/user/counseling/job";
    }
   
    //교수상담 페이지
    @GetMapping("/user/academic/professor")
    public String showProfessorCounselingPage() {
        return "/user/counseling/professor";
    }
   
    //학습컨설팅 페이지
    @GetMapping("/user/academic/consulting")
    public String showAcademicConsultingPage() {
        return "/user/counseling/consulting";
    }
   
    //또래상담 페이지
    @GetMapping("/user/etc/peer")
    public String showPeerCounselingPage() {
        return "/user/counseling/peer";
    }
   
    @GetMapping("/counselor/apply-description")
    public String showApplyDescriptionPage() {
        return "counselor/applyDescription";
    }

    // 상담일지 목록 페이지
    @GetMapping("/counselor/counselling-record-list")
    public String counsellingRecordList() {
        return "counselor/counsellingRecordList";
    }

   
    // 상담 신청 내역 목록 페이지
    @GetMapping("/counselor/counselee-list")
    public String counseleeList() {
        return "counselor/counseleeList";
    }
   

    // 상담 신청 내역 뷰 페이지
    @GetMapping("/counselor/counselee-view")
    public String counseleeView(){
        return "counselor/counseleeView";
    }
   
    @GetMapping("/counselor/counselling-record")
    public String showCounsellingRecordPage() {
        return "counselor/counsellingRecord";
    }
   
    @GetMapping("/counselor/monthly-calendar")
    public String showMonthlyCalendar() {
        return "counselor/monthlyCalendar";  
    }

    @GetMapping("/counselor/weekly-calendar")
    public String showWeeklyCalendar() {
        return "counselor/weeklyCalendar";  
    }

    @GetMapping("/counselor/calendar")
    public String showCounselorCalendar() {
        return "counselor/calendar";
    }
   
    // 상담사 공지사항 목록 페이지
    @GetMapping("/counselor/notice/list")
    public String showCounselorNoticeList(Model model) {
        model.addAttribute("boardName", "상담사 공지사항");
        model.addAttribute("boardId", "counselorBoard");
       
        return "counselor/board/basic/list";
    }

    @GetMapping("/board/{boardnm}/list")
    public String showBoardList(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
       
        if ("FAQ".equals(boardName)) {
            return "counselor/board/faq/list";  // FAQ 게시판 목록
        } else {
            return "counselor/board/basic/list";  // 기본 게시판 목록
        }
    }

    @GetMapping("/board/{boardnm}/write")
    public String showBoardWritePage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        model.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/write";  
    }
   
    @GetMapping("/board/{boardnm}/modify")
    public String showBoardModifyPage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        model.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/modify";  
    }

    @GetMapping("/board/{boardnm}/view")
    public String showBoardViewPage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
        return "counselor/board/basic/view";  
    }

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
        return "admin/menuList-M";  // 대메뉴 리스트 템플릿을 반환
    }

    @GetMapping("/admin/menu-list2")
    public String showMenuListPage2() {
        return "admin/menuList-C";  // 소메뉴 리스트 (C) 템플릿을 반환
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "admin/adminLogin";  // 관리자 로그인 템플릿을 반환
    }
   
    @GetMapping("/admin/statistics") // 상담 통계 페이지
    public String counselingChartPage() {
    	return "admin/statistics";  
    }
   
   
    @GetMapping("/admin/apply-list") // 상담 신청 리스트 페이지
    public String applyListPage() {
        return "/admin/applyList";
    }
   
    @GetMapping("/admin/schedule-list") // 상담 일정 관리 - 배정 상담 목록
    public String scheduleList() {
        return "/admin/scheduleList";
    }
   
    @GetMapping("/admin/board-management") // 게시판 관리 페이지
    public String boardManagement() {
        return "/admin/boardManagement";
    }
   
    @GetMapping("/admin/counselor-schedule") // 상담 일정 관리 - 상담사 일정 관리 페이지
    public String counselorSchedule() {
        return "/admin/counselorSchedule";
    }
   
    @GetMapping("/admin/manage-post") // 게시판 관리 페이지 - 게시글 관리 페이지
    public String managePost() {
        return "redirect:/admin/getPost";
    }
}
