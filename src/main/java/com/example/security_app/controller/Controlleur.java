package com.example.security_app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controlleur {

    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.addAttribute("name", name);
        return "about";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/feature")
    public String featuresPage() {
        return "features";
    }
}
