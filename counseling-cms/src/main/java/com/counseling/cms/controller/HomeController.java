package com.counseling.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.ServletRequest;

@Controller
public class HomeController {

    @GetMapping("/welcome")
    public String homePage() {
        return "welcome";
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Updated to match Thymeleaf convention, no need for .html
    }

    @GetMapping("/user/login")
    public String userLoginPage() {
        return "user/userLogin";
    }

    @GetMapping("/user/find")
    public String userFindPassword() {
        return "pw/find";
    }

    @GetMapping("/user/change")
    public String userChangePassword() {
        return "pw/change";
    }

    @GetMapping("/counselor")
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

    @GetMapping("/board/{boardnm}/list")
    public String boardBasicList(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        m.addAttribute("boardName", boardName);
        m.addAttribute("boardId", boardId);
        
        if(boardName == "FAQ") {
        	return "counselor/board/faq/list";
        }else {
        	return "counselor/board/basic/list";
        }
    }

    @GetMapping("/board/{boardnm}/write")
    public String boardBasicWrite(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        m.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/write";
    }
    
    @GetMapping("/board/{boardnm}/modify")
    public String boardBasicModify(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        m.addAttribute("boardName", boardName);
        return "counselor/board/inquiry/modify";
    }

    @GetMapping("/board/{boardnm}/view")
    public String boardBasicView(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String) req.getAttribute("boardName");
        String boardId = (String) req.getAttribute("boardId");
        m.addAttribute("boardName", boardName);
        m.addAttribute("boardId", boardId);
        return "counselor/board/basic/view";
    }

    // Admin-related endpoints
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
