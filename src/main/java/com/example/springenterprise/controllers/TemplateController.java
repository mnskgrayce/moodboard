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

    // TODO: Configure login error page

    @RequestMapping("/register")
    public String getRegisterView() {
        return "register";
    }
}
