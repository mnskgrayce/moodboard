package com.example.springenterprise.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// This class only serves to display the HTML templates
// Do NOT use RestController
// It will convert the response to JSON/XML
// ..and you get a string, instead of the Thymeleaf template
@Controller
public class TemplateController {

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
}
