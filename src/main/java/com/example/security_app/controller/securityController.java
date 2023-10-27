package com.example.security_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class securityController {

    @GetMapping("/notAuthorized")
    public String notAuthorized() {

        return "notAuthorized";
    }

    @GetMapping("/login")
    public String pageLogin() {

        return "login";
    }
}
