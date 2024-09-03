package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.ServletRequest;


@Controller
public class HomeController {

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }

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
    
    @GetMapping("/counselor/apply-description")
    public String showApplyDescriptionPage() {
        return "counselor/applyDescription";
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

    @GetMapping("/board/{boardnm}/list")
    public String showBoardList(@PathVariable String boardnm, ServletRequest req, Model model) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
        
        if ("FAQ".equals(boardName)) {
            return "counselor/board/faq/list";
        } else {
            return "counselor/board/basic/list";
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

    // Admin-related endpoints
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
}
