package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.service.AdminApplyService;
import com.counseling.cms.utility.AESUtility;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Resource(name = "admin_apply_module")
    private AdminApplyService aas;


    @GetMapping("/")
    public String showHomePage() {
        return "index";  
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";  
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin/admin";
    }

    @GetMapping("/user/main/introduction")
    public String userIntroduction() {
        return "user/introduction/introduction";
    }

    @GetMapping("/user/main/jobintrodution")
    public String jobIntroduction() {
        return "user/jobintroduction/JobIntroduction";
    }
    
    @GetMapping("/user/main/organizationchart")
    public String organizationChart() {
        return "user/organization/userOrganizationChart";
    }
    
    @GetMapping("/user/main/userDirections")
    public String directions() {
        return "user/directions/userDirections";
    }

    @GetMapping("/user/main/self_diagnosis")
    public String selfDiagnosis() {
        return "user/self_diagnosis/Self-diagnosis";
    }

    @GetMapping("/user/login")
    public String showUserLoginPage() {
        return "user/userLogin";
    }

    @GetMapping("/pw/find")
    public String showFindPasswordPage() {
        return "pw/find";  
    }

    @GetMapping("/pw/change")
    public String showChangePasswordPage(HttpSession session) {
    	if(session.getAttribute("userId")!=null) {
    		return "pw/change";      		
    	} 
    	return "redirect:/pw/find";
    }

    // 상담 페이지들
    @GetMapping("/user/counseling/counseling")
    public String showCounselingPage(Model model) {
    	model.addAttribute("code","C3010");
        return "user/counseling/counseling";
    }

    @GetMapping("/user/counseling/anonymity")
    public String showAnonymityCounselingPage(Model model) {
    	model.addAttribute("code","C3011");
        return "user/counseling/anonymity";
    }

    @GetMapping("/user/counseling/emergency")
    public String showEmergencyCounselingPage(Model model) {
    	model.addAttribute("code","C3012");
        return "user/counseling/emergency";
    }

    @GetMapping("/user/academic/career")
    public String showCareerCounselingPage(Model model) {
    	model.addAttribute("code","C3020");
        return "user/counseling/career";
    }

    @GetMapping("/user/academic/job")
    public String showJobCounselingPage(Model model) {
    	model.addAttribute("code","C3021");
        return "user/counseling/job";
    }

    @GetMapping("/user/academic/professor")
    public String showProfessorCounselingPage(Model model) {
    	model.addAttribute("code","C3022");
        return "user/counseling/professor";
    }

    @GetMapping("/user/academic/consulting")
    public String showAcademicConsultingPage(Model model) {
    	model.addAttribute("code","C3023");
        return "user/counseling/consulting";
    }

    @GetMapping("/user/etc/peer")
    public String showPeerCounselingPage(Model model) {
    	model.addAttribute("code","C3030");
        return "user/counseling/peer";
    }


    @Value("${AESKey}")
	private String KEY;
    // 상담 신청 페이지

    @GetMapping("/user/application")
    public String showCounselingApplicationPage(String counseling) throws Exception {
        return "redirect:/user/apply?counseling="+AESUtility.encrypt(counseling, AESUtility.getSecretKeyFromBase64(KEY));
    }

    @GetMapping("/counselor/notice/list")
    public String showCounselorNoticeList(Model model) {
        model.addAttribute("boardName", "상담사 공지사항");
        model.addAttribute("boardId", "counselorBoard");
        return "counselor/board/basic/list";
    }
    
    // 월간 캘린더 페이지
	@GetMapping("/counselor/monthly-calendar")
	public String getMonthlyList() {
		return "/counselor/monthlyCalendar";
	}

    // 주간 캘린더 페이지
    @GetMapping("/counselor/weekly-calendar")
    public String showWeeklyCalendar() {
        return "counselor/weeklyCalendar";
    }


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

   


    // 관리자 상담사 목록 페이지
    @GetMapping("/admin/counselor-list")
    public String showCounselorListPage() {
        return "redirect:/admin/list-of-counselors";
    }

    @GetMapping("/admin/admin-list")
    public String showAdminListPage() {
        return "admin/adminList";
    }

    @GetMapping("/admin/banner-list")
    public String showBannerListPage() {
        return "redirect:/admin/bannerList";
    }

    @GetMapping("/admin/banner-create")
    public String showBannerCreatePage() {
        return "admin/bannerCreate";
    }

    @GetMapping("/admin/bannerModify")
    public String showBannerModifyPage(@RequestParam(value="idx", defaultValue = "") int idx) {
        return "redirect:/admin/banner-modify?idx=" + idx; 
    }

    @GetMapping("/admin/menu-list1")
    public String showMenuListPage1() {
        return "redirect:/admin/menu/major/list"; 
    }

    @GetMapping("/admin/menu-list2")
    public String showMenuListPage2(@RequestParam(value="code", defaultValue = "") String code) {
        return code.isEmpty() ? "redirect:/admin/menu/sub/list" : "redirect:/admin/menu/sub/list?code=" + code;
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "admin/adminLogin";
    }

    @GetMapping("/admin/statistics")
    public String counselingChartPage() {
        return "admin/statistics";  
    }




    // 게시판 관리 페이지
    @GetMapping("/admin/board-management")
    public String boardManagement() {
        return "admin/boardManagement";
    }


    @GetMapping("/admin/schedule-list")
    public String redirectToScheduleList() {
        return "redirect:/admin/schedulelisting";
    }
    
    // 상담 일정 관리 - 상담사 일정 관리 페이지
    @GetMapping("/admin/counselor-schedule")
    public String redirectToCounselorSchedule() {
        return "redirect:/admin/counselorscheduling";
    }

    @GetMapping("/admin/manage-post")
    public String managePost(@RequestParam String boardNumber) {
        return "redirect:/admin/getPost?boardNumber=" + boardNumber;
    }
    
    //챗봇 페이지
    @GetMapping("/user/	bot")
    public String chatbot() {
        return "/user/chatbot";
    }
    
    //카카오 로그인
    @GetMapping("/user/kakaook")
    public String kakaook() {
    	return "/user/kakao";
    }

  
    //카카오 메세지
    @PostMapping("/user/message")
    public String kakaoMessage() {
    	return "/user/kakaomessage";
    }
    
    @GetMapping("/apfhd")
    public String accessDeniedPage() {
    	return "/layouts/accessDenied";

    }
}
