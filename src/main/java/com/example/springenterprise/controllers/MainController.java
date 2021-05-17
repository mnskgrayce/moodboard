package com.example.springenterprise.controllers;

import com.example.springenterprise.services.MoodboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

// Do NOT use RestController
// It will convert the response to JSON/XML
// ..and you get a string, instead of the Thymeleaf template
@AllArgsConstructor
@Controller
public class MainController {

    private final MoodboardService moodboardService;

    @RequestMapping("/")
    public String getHomeView(Model model, Principal principal) {
        String userEmail = principal.getName();
        model.addAttribute("moodboards", moodboardService.listByUser(userEmail));
        return "index";
    }

    @RequestMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @RequestMapping("/register")
    public String getRegisterView() {
        return "register";
    }

    @RequestMapping("/search_result")
    public String getSearchResult() {
        return "search_result";
    }

    @RequestMapping("/pic")
    public String getPicView() {
        return "pic";
    }
}
