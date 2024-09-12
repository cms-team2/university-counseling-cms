package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

 
    @GetMapping("/")
    public String showHomePage() {
        return "index";  
    }

    // 환영 페이지
    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";  
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin/admin";
    }
 

    // 센터 소개 페이지
    @GetMapping("/user/main/introduction")
    public String userIntroduction() {
        return "user/introduction/introduction";
    }

    // 업무 소개 페이지
    @GetMapping("/user/main/jobintrodution")
    public String jobIntroduction() {
        return "user/jobintroduction/JobIntroduction";
    }
    
    // 조직도 소개 페이지
    @GetMapping("/user/main/organizationchart")
    public String organizationChart() {
        return "user/organization/userOrganizationChart";
    }
    
    // 찾아오는 길 페이지
    @GetMapping("/user/main/userDirections")
    public String directions() {
        return "user/directions/userDirections";
    }
    
    // 자가진단 페이지 
    @GetMapping("/user/main/self_diagnosis")
    public String selfDiagnosis() {
        return "user/self_diagnosis/Self-diagnosis";
    }
  
    // 마이페이지 
    @GetMapping("/user/mypage")
    public String myPage() {
        return "user/mypage/mypage";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String showUserLoginPage() {
        return "user/userLogin";
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/user/find")
    public String showFindPasswordPage() {
        return "pw/find";  
    }

    // 비밀번호 변경 페이지
    @GetMapping("/user/change")
    public String showChangePasswordPage() {
        return "pw/change";  
    }

    // 심리상담 페이지
    @GetMapping("/user/counseling/counseling")
    public String showCounselingPage() {
        return "user/counseling/counseling";
    }

    // 익명상담 페이지
    @GetMapping("/user/counseling/anonymity")
    public String showAnonymityCounselingPage() {
        return "user/counseling/anonymity";
    }

    // 위기상담 페이지
    @GetMapping("/user/counseling/emergency")
    public String showEmergencyCounselingPage() {
        return "user/counseling/emergency";
    }

    // 진로상담 페이지
    @GetMapping("/user/academic/career")
    public String showCareerCounselingPage() {
        return "user/counseling/career";
    }

    // 취업상담 페이지
    @GetMapping("/user/academic/job")
    public String showJobCounselingPage() {
        return "user/counseling/job";
    }

    // 교수상담 페이지
    @GetMapping("/user/academic/professor")
    public String showProfessorCounselingPage() {
        return "user/counseling/professor";
    }

    // 학습컨설팅 페이지
    @GetMapping("/user/academic/consulting")
    public String showAcademicConsultingPage() {
        return "user/counseling/consulting";
    }

    // 또래상담 페이지
    @GetMapping("/user/etc/peer")
    public String showPeerCounselingPage() {
        return "user/counseling/peer";
    }

    // 상담 신청 페이지
    @GetMapping("/user/application")
    public String showCounselingApplicationPage() {
        return "user/application";
    }

    @GetMapping("/counselor/notice/list")
    public String showCounselorNoticeList(Model model) {
        model.addAttribute("boardName", "상담사 공지사항");
        model.addAttribute("boardId", "counselorBoard");
        return "counselor/board/basic/list";
    }

    // 상담 신청 내역 목록 페이지
    @GetMapping("/counselor/counselee-list")
    public String counseleeList() {
        return "counselor/counseleeList";
    }

    // 상담사 목록 페이지
    @GetMapping("/counselor/counselee-view")
    public String counseleeView() {
        return "counselor/counseleeView";
    }

    // 상담 기록 페이지
    @GetMapping("/counselor/counselling-record")
    public String showCounsellingRecordPage() {
        return "counselor/counsellingRecord";
    }

    // 월간 캘린더 페이지
    @GetMapping("/counselor/monthly-calendar")
    public String showMonthlyCalendar() {
        return "counselor/monthlyCalendar";
    }

    // 주간 캘린더 페이지
    @GetMapping("/counselor/weekly-calendar")
    public String showWeeklyCalendar() {
        return "counselor/weeklyCalendar";
    }

    // 상담사 캘린더 페이지
    @GetMapping("/counselor/calendar")
    public String showCounselorCalendar() {
        return "counselor/calendar";
    }

    // 게시판 목록 페이지
    @GetMapping("/board/{boardnm}/list")
    public String showBoardList(@PathVariable String boardnm, Model model) {
        String boardName = "기본 게시판";  // Default value

        if ("FAQ".equals(boardnm)) {
            boardName = "FAQ 게시판 목록";
        }
        
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", "boardIdPlaceholder");
        
        return "counselor/board/basic/list";  // Adjust as necessary
    }

    // 게시판 작성 페이지
    @GetMapping("/board/{boardnm}/write")
    public String showBoardWritePage(@PathVariable String boardnm, Model model) {
        model.addAttribute("boardName", boardnm);
        return "counselor/board/inquiry/write";  
    }

    // 게시판 수정 페이지
    @GetMapping("/board/{boardnm}/modify")
    public String showBoardModifyPage(@PathVariable String boardnm, Model model) {
        model.addAttribute("boardName", boardnm);
        return "counselor/board/inquiry/modify";  
    }

    // 게시판 조회 페이지
    @GetMapping("/board/{boardnm}/view")
    public String showBoardViewPage(@PathVariable String boardnm, Model model) {
        model.addAttribute("boardName", boardnm);
        model.addAttribute("boardId", "boardIdPlaceholder");
        return "counselor/board/basic/view";  
    }

    // 관리자 상담사 목록 페이지
    @GetMapping("/admin/counselor-list")
    public String showCounselorListPage() {
        return "admin/counselorList";
    }

    // 관리자 목록 페이지
    @GetMapping("/admin/admin-list")
    public String showAdminListPage() {
        return "admin/adminList";
    }

    // 배너 목록 페이지
    @GetMapping("/admin/banner-list")
    public String showBannerListPage() {
        return "admin/bannerList";
    }

    // 배너 생성 페이지
    @GetMapping("/admin/banner-create")
    public String showBannerCreatePage() {
        return "admin/bannerCreate";
    }

    // 대메뉴 리스트 페이지
    @GetMapping("/admin/menu-list1")
    public String showMenuListPage1() {

        return "admin/menuList-M";
    }

    // 소메뉴 리스트 (C) 페이지
    @GetMapping("/admin/menu-list2")
    public String showMenuListPage2() {
        return "admin/menuList-C";
    }

    // 관리자 로그인 페이지
    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "admin/adminLogin";
    }

    // 상담 통계 페이지
    @GetMapping("/admin/statistics")
    public String counselingChartPage() {
        return "admin/statistics";  
    }

    // 게시판 관리 페이지
    @GetMapping("/admin/board-management")
    public String boardManagement() {
        return "admin/boardManagement";
    }

    // 상담 일정 관리 - 상담 일정 관리 페이지
    @GetMapping("/admin/schedule-list")
    public String scheduleList() {
        return "redirect:/admin/schedulelisting";
    }
    
    // 상담 일정 관리 - 상담사 일정 관리 페이지
    @GetMapping("/admin/counselor-schedule")
    public String counselorSchedule() {
        return "redirect:/admin/counselorscheduling";
    }

    // 게시판 관리 페이지 - 게시글 관리 페이지
    @GetMapping("/admin/manage-post")
    public String managePost() {
        return "redirect:/admin/getPost";
    }
}
