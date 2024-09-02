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
        return "/index.html";
    }

    @GetMapping("/user/login")
    public String userLoginPage() {
        return "/user/userLogin.html";
    }

    @GetMapping("/user/find")
    public String userFindPassword() {
        return "/pw/find.html";
    }

    @GetMapping("/user/change")
    public String userChangePassword() {
        return "/pw/change.html";
    }

    @GetMapping("/counselor/test")
    public String test() {
        return "/counselor/monthlyCalendar.html";
    }

    @GetMapping("/counselor/test2")
    public String test2() {
        return "/counselor/weeklyCalendar.html";
    }

    @GetMapping("/counselor/calendar")
    public String counselorCalendar() {
        return "/counselor/calendar.html";
    }

    @GetMapping("/board/{boardnm}/list")
    public String boardBasicList(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String)req.getAttribute("boardName");
        m.addAttribute("boardName", boardName);
        return "/counselor/board/basic/list.html";
    }

    @GetMapping("/board/{boardnm}/write")
    public String boardBasicWrite(@PathVariable String boardnm, ServletRequest req, Model m) {
        String boardName = (String)req.getAttribute("boardName");
        m.addAttribute("boardName", boardName);
        return "/counselor/board/basic/write.html";
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
