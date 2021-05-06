package com.example.springenterprise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
