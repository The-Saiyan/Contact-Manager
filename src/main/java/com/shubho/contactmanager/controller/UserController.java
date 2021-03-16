package com.shubho.contactmanager.controller;

import com.shubho.contactmanager.model.Contact;
import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @ModelAttribute
    public void addUserToAll(Model model, Principal principal) {
        String userEmail = principal.getName();
        System.out.println(userEmail);
        User user = userRepository.getUserByUserEmail(userEmail);
        model.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        return "normal/user_dashboard";
    }
    // Opening add contact form
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "add-contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    // Processing Contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact) {

        return "normal/add_contact_form";
    }
}
