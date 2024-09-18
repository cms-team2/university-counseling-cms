package com.counseling.cms.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counseling.cms.dto.Stdnt_Dscsn_join_dto;
import com.counseling.cms.service.adminApplyService2;
import com.counseling.cms.utility.AESUtility;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Resource(name = "admin_apply_module")
    private adminApplyService2 aas2;

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

    @GetMapping("/admin/apply-list")
    public String applyListPage(@RequestParam(value = "search_type", required = false) String search_type,
                                @RequestParam(value = "search_keyword", required = false) String search_keyword,
                                Model model) {
        List<Stdnt_Dscsn_join_dto> list = aas2.apply_list(search_keyword, search_type);
        model.addAttribute("apply_list", list);
        return "/admin/applyList";
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
    @GetMapping("/pw/find")
    public String showFindPasswordPage() {
        return "pw/find";  
    }

    // 비밀번호 변경 페이지
    @GetMapping("/pw/change")
    public String showChangePasswordPage(HttpSession session) {
    	if(session.getAttribute("userEmail")!=null) {
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

    @Value("${AESKey}")
	private String KEY;
    // 상담 신청 페이지
    @GetMapping("/user/application")
    public String showCounselingApplicationPage(String counseling) throws Exception {
        return "redirect:/user/apply?counseling="+AESUtility.encrypt(counseling , AESUtility.getSecretKeyFromBase64(KEY));
    }

    // 상담사 공지사항 목록 페이지
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

    // 상담 기록 페이지
    @GetMapping("/counselor/counseling-record")
    public String showCounsellingRecordPage() {
        return "counselor/counselingRecord";
    }
    
    // 상담 기록 페이지
    @GetMapping("/counselor/counseling-record-list")
    public String showCounsellingRecordListPage() {
        return "counselor/counselingRecordList";
    }

    // 상담사 주간 캘린더 페이지
    @GetMapping("/counselor/weekly-calendar")
    public String weeklyCalendar() {
        return "counselor/weeklyCalendar";
    }

    // 게시판 목록 페이지
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

    // 게시판 작성 페이지
    @GetMapping("/board/{boardnm}/write")
    public String showBoardWritePage(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        model.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/write";  
    }

    // 관리자 상담사 목록 페이지
    @GetMapping("/admin/counselor-list")
    public String showCounselorListPage() {
        return "redirect:/admin/list-of-counselors";
    }

    // 관리자 목록 페이지
    @GetMapping("/admin/admin-list")
    public String showAdminListPage() {
        return "admin/adminList";
    }

    // 배너 목록 페이지
    @GetMapping("/admin/banner-list")
    public String showBannerListPage() {
        return "redirect:/admin/bannerList";
    }

    // 배너 생성 페이지
    @GetMapping("/admin/banner-create")
    public String showBannerCreatePage() {
        return "admin/bannerCreate";
    }
    
    @GetMapping("/admin/bannerModify")
    public String showBannerModifyPage(@RequestParam(value="idx", defaultValue = "") int idx) {
        return "redirect:/admin/banner-modify?idx="+idx; 
    }

    // 대메뉴 리스트 페이지
    @GetMapping("/admin/menu-list1")
    public String showMenuListPage1() {
        return "redirect:/admin/menu/major/list"; 
    }

    // 소메뉴 리스트 (C) 페이지
    @GetMapping("/admin/menu-list2")
    public String showMenuListPage2(@RequestParam(value="code", defaultValue = "") String code) {
    	String result ="";
    	if(code.equals("")) {
    		result = "redirect:/admin/menu/sub/list";
    	}else {
    		result = "redirect:/admin/menu/sub/list?code="+code;
    	}
        return result;
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


    // 상담 일정 관리 - 배정 상담 목록 페이지
    @GetMapping("/admin/schedule-list")
    public String scheduleList() {
        return "admin/scheduleList";
    }

    // 게시판 관리 페이지
    @GetMapping("/admin/board-management")
    public String boardManagement() {
        return "admin/boardManagement";
    }

    // 상담 일정 관리 - 상담사 일정 관리 페이지
    @GetMapping("/admin/counselor-schedule")
    public String counselorSchedule() {
        return "admin/counselorSchedule";
    }

    // 게시판 관리 페이지 - 게시글 관리 페이지
    @GetMapping("/admin/manage-post")
    public String managePost(String boardNumber) {
        return "redirect:/admin/getPost?boardNumber="+boardNumber;
    }
}
