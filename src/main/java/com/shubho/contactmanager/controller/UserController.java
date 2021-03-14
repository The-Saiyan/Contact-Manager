package com.shubho.contactmanager.controller;

import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        String userEmail = principal.getName();
        System.out.println(userEmail);
        User user = userRepository.getUserByUserEmail(userEmail);
        model.addAttribute("user", user);
        return "normal/user_dashboard";
    }
}
