package com.example.springenterprise.controllers;

import com.example.springenterprise.services.MoodboardService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

// Main class to resolve template requests
// Do NOT use RestController
// It will convert the response to JSON/XML
// ..and you get a string, instead of the Thymeleaf template
@AllArgsConstructor
@Controller
public class MainController {

    private final MoodboardService moodboardService;

    // Get the home page with all the user's moodboards
    @RequestMapping("/")
    public String getHome(Model model, Principal principal) {
        String userEmail = principal.getName();
        model.addAttribute("moodboards", moodboardService.listByUser(userEmail));
        return "index";
    }

    // Get login form
    @RequestMapping("/login")
    public String getLoginView() {
        return "login";
    }

    // Get registration form
    @RequestMapping("/register")
    public String getRegisterView() {
        return "register";
    }

    // Get the page with searched images
    @RequestMapping("/search_result")
    public String getSearchResultView() {
        return "search_result";
    }

    // Get the page with random images
    @RequestMapping("/random")
    public String getRandomView() {
        return "random";
    }

    // Show picture detail page (+ list of moodboards to save to)
    @RequestMapping("/pic")
    public String getPicView(Model model, Principal principal) {
        String userEmail = principal.getName();
        model.addAttribute("moodboards", moodboardService.listByUser(userEmail));
        return "pic";
    }

    // Log current user out and redirect to login page
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String getLogoutView (HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return "redirect:/login";
    }
}
