package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.ServletRequest;

@Controller
public class HomeController {

    // 홈 페이지
    @GetMapping("/welcome")
    public String homePage() {
        return "welcome";
    }

    // 인덱스 페이지
    @GetMapping("/")
    public String index() {
        return "index"; // Thymeleaf에서는 .html 확장자를 명시할 필요가 없음
    }

    // 사용자 로그인 페이지
    @GetMapping("/user/login")
    public String userLoginPage() {
        return "user/userLogin";
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/user/pw/find")
    public String userFindPassword() {
        return "pw/find";
    }

    // 비밀번호 변경 페이지
    @GetMapping("/user/pw/change")
    public String userChangePassword() {
        return "pw/change";
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
    
    // 상담사 관련 페이지
    @GetMapping("/counselor/test")
    public String test() {
        return "counselor/monthlyCalendar";
    }

    @GetMapping("/counselor/test2")
    public String test2() {
        return "counselor/weeklyCalendar";
    }

    @GetMapping("/counselor/calendar")
    public String counselorCalendar() {
        return "counselor/calendar";
    }

    // 게시판 리스트 페이지
    @GetMapping("/board/{boardnm}/list")
    public String boardBasicList(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        m.addAttribute("boardName", boardName);
        m.addAttribute("boardId", boardId);
        
        if ("FAQ".equals(boardName)) { // String 비교에는 .equals() 사용
            return "counselor/board/faq/list";
        } else {
            return "counselor/board/basic/list";
        }
    }

    // 게시판 작성 페이지
    @GetMapping("/board/{boardnm}/write")
    public String boardBasicWrite(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        m.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/write";
    }
    
    // 게시판 수정 페이지
    @GetMapping("/board/{boardnm}/modify")
    public String boardBasicModify(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        m.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/modify";
    }

    // 게시판 보기 페이지
    @GetMapping("/board/{boardnm}/view")
    public String boardBasicView(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        m.addAttribute("boardName", boardName);
        m.addAttribute("boardId", boardId);
        return "counselor/board/basic/view";
    }

    // 관리자 관련 페이지
    @GetMapping("/admin/counselor-list")
    public String counselorListPage() {
        return "admin/counselorList";
    }

    @GetMapping("/admin/admin-list")
    public String adminListPage() {
        return "admin/adminList";
    }

    @GetMapping("/admin/banner-list")
    public String bannerListPage() {
        return "admin/bannerList";
    }

    @GetMapping("/admin/banner-create")
    public String bannerCreatePage() {
        return "admin/bannerCreate";
    }

    @GetMapping("/admin/menu-list1")
    public String menuListPage1() {
        return "admin/menuList-M";
    }

    @GetMapping("/admin/menu-list2")
    public String menuListPage2() {
        return "admin/menuList-C";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/adminLogin";
    }
}
