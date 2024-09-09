package com.counseling.cms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.dto.Dscsn_Aply_Info_dto;
import com.counseling.cms.dto.Stdnt_Dscsn_join_dto;
import com.counseling.cms.service.adminApplyService2;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;

@Controller
public class HomeController {
	
	@Resource(name = "admin_apply_module")
	private adminApplyService2 aas2;
	
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
    
    @GetMapping("/admin/apply-list")
    public String applyListPage(@RequestParam(value = "",required = false)String search_type,
    		@RequestParam(value = "",required = false)String search_keyword,Model m) {
    	List<Stdnt_Dscsn_join_dto> list = aas2.apply_list(search_keyword,search_type);
    	System.out.println(list);
    	m.addAttribute("apply_list",list);
        return "/admin/applyList";
    }
    
    @GetMapping("/admin/schedule-list")
    public String scheduleList() {
        return "/admin/scheduleList";
    }
    
    @GetMapping("/admin/board-management")
    public String boardManagement() {
        return "/admin/boardManagement";
    }
    
    @GetMapping("/admin/counselor-schedule")
    public String counselorSchedule() {
        return "/admin/counselorSchedule";
    }
    
    @GetMapping("/admin/manage-post")
    public String managePost() {
        return "/admin/managePost";
    }

    // 센터 소개 페이지
    @GetMapping("/user/main/introduction")
    public String userIntroduction() {
        return "user/introduction/introduction";
    }
    // 업무 소개 페이지
    @GetMapping("/user/main/jobintrodution")
    public String jobintrodution() {
    	return "user/jobintroduction/JobIntroduction";
    }
    
    //조직도 소개 페이지
    @GetMapping("/user/main/organizationchart")
    public String organizationchart() {
    	return "user/organization/userOrganizationChart";
    }
    
    //찾아오는길 페이지
    @GetMapping("/user/main/userDirections")
    public String Directions() {
    	return "user/directions/userDirections";
    }
    
    //자가진단 페이지 
    @GetMapping("/user/main/self_diagnosis")
    public String self_diagnosis() {
    	
    	return "user/self_diagnosis/Self-diagnosis";
    }
  
    //마이페이지 
    @GetMapping("/user/mypage")
    public String mypage() {
    	return "user/mypage/mypage";
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
    
    @GetMapping("/user/counseling/counseling")
    public String showCounselingPage() {
        return "/user/counseling/counseling";
    }
    
    @GetMapping("/user/counseling/anonymity")
    public String showAnonymityCounselingPage() {
        return "/user/counseling/anonymity";
    }
    
    @GetMapping("/user/counseling/emergency")
    public String showEmergencyCounselingPage() {
        return "/user/counseling/emergency";
    }
    
    @GetMapping("/user/academic/career")
    public String showCareerCounselingPage() {
        return "/user/counseling/career";
    }
    
    @GetMapping("/user/academic/job")
    public String showJobCounselingPage() {
        return "/user/counseling/job";
    }
    
    @GetMapping("/user/academic/professor")
    public String showProfessorCounselingPage() {
        return "/user/counseling/professor";
    }
    
    @GetMapping("/user/academic/consulting")
    public String showAcademicConsultingPage() {
        return "/user/counseling/consulting";
    }
    
    @GetMapping("/user/etc/peer")
    public String showPeerCounselingPage() {
        return "/user/counseling/peer";
    }
    
    @GetMapping("/user/application")
    public String showCounselingApplicationPage() {
        return "/user/application";
    }
    
    @GetMapping("/counselor/apply-description")
    public String showApplyDescriptionPage() {
        return "counselor/applyDescription";
    }
    @GetMapping("/counselor/counselee-view")
    public String counseleeView() {
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
        return "admin/counselorList";  
    }

    @GetMapping("/admin/admin-list")
    public String showAdminListPage() {
        return "admin/adminList";  
    }

    @GetMapping("/admin/banner-list")
    public String showBannerListPage() {
        return "admin/bannerList";  
    }

    @GetMapping("/admin/banner-create")
    public String showBannerCreatePage() {
        return "admin/bannerCreate"; 
    }

    @GetMapping("/admin/menu-list1")
    public String showMenuListPage1() {
        return "admin/menuList-M"; 
    }

    @GetMapping("/admin/menu-list2")
    public String showMenuListPage2() {
        return "admin/menuList-C";  
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "admin/adminLogin";  
    }
    
    @GetMapping("/admin/statistics")
    public String counselingChartPage() {
        return "admin/statistics";
    }
}
