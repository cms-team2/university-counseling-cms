package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.counseling.cms.service.AdminApplyService;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/admin/apply-list/api_data")
    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Map<String, String>> adminApplyApi(@RequestParam(value = "data", required = false) String studentId) {
        String cndAddress = "http://example.com/cnd_address"; // 예시 주소
        
        Map<String, String> response = new HashMap<>();
        response.put("cndAddress", cndAddress);

        return ResponseEntity.ok(response); // JSON 형식으로 응답 반환
    }

    // 각종 사용자 페이지
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

    @GetMapping("/user/mypage")
    public String myPage() {
        return "user/mypage/mypage";
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
        if (session.getAttribute("userEmail") != null) {
            return "pw/change";      		
        } 
        return "redirect:/pw/find";
    }

    // 상담 페이지들
    @GetMapping("/user/counseling/counseling")
    public String showCounselingPage() {
        return "user/counseling/counseling";
    }

    @GetMapping("/user/counseling/anonymity")
    public String showAnonymityCounselingPage() {
        return "user/counseling/anonymity";
    }

    @GetMapping("/user/counseling/emergency")
    public String showEmergencyCounselingPage() {
        return "user/counseling/emergency";
    }

    @GetMapping("/user/academic/career")
    public String showCareerCounselingPage() {
        return "user/counseling/career";
    }

    @GetMapping("/user/academic/job")
    public String showJobCounselingPage() {
        return "user/counseling/job";
    }

    @GetMapping("/user/academic/professor")
    public String showProfessorCounselingPage() {
        return "user/counseling/professor";
    }

    @GetMapping("/user/academic/consulting")
    public String showAcademicConsultingPage() {
        return "user/counseling/consulting";
    }

    @GetMapping("/user/etc/peer")
    public String showPeerCounselingPage() {
        return "user/counseling/peer";
    }

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

    @GetMapping("/counselor/counselee-list")
    public String counseleeList() {
        return "counselor/counseleeList";
    }

    @GetMapping("/counselor/counseling-record")
    public String showCounsellingRecordPage() {
        return "counselor/counselingRecord";
    }
    
    @GetMapping("/counselor/counseling-record-list")
    public String showCounsellingRecordListPage() {
        return "counselor/counselingRecordList";
    }

    @GetMapping("/counselor/weekly-calendar")
    public String weeklyCalendar() {
        return "counselor/weeklyCalendar";
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
    public String showBoardWritePage(@PathVariable String boardnm, Model model) {
        model.addAttribute("boardName", boardnm);
        return "counselor/board/inquiry/write";  
    }

    @GetMapping("/board/{boardnm}/modify")
    public String showBoardModifyPage(@PathVariable String boardnm, Model model) {
        model.addAttribute("boardName", boardnm);
        return "counselor/board/inquiry/modify";  
    }

    @GetMapping("/board/{boardnm}/view")
    public String showBoardViewPage(@PathVariable String boardnm, Model model) {
        model.addAttribute("boardName", boardnm);
        model.addAttribute("boardId", "boardIdPlaceholder");
        return "counselor/board/basic/view";  
    }

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



    @GetMapping("/admin/board-management")
    public String boardManagement() {
        return "admin/boardManagement";
    }

    @GetMapping("/admin/schedule-list")
    public String redirectToScheduleList() {
        return "redirect:/admin/schedulelisting";
    }
    
    @GetMapping("/admin/counselor-schedule")
    public String redirectToCounselorSchedule() {
        return "redirect:/admin/counselorscheduling";
    }

    @GetMapping("/admin/manage-post")
    public String managePost(@RequestParam String boardNumber) {
        return "redirect:/admin/getPost?boardNumber=" + boardNumber;
    }
}
