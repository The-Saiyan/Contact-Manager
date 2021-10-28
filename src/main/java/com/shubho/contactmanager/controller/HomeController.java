package com.shubho.contactmanager.controller;

import com.shubho.contactmanager.helper.Message;
import com.shubho.contactmanager.DAO.requests.UserRequest;
import com.shubho.contactmanager.DAO.response.UserResponse;
import com.shubho.contactmanager.services.HomeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shubho.contactmanager.model.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    HomeServices homeServices;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/signin")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home-Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About-Contact Manager");
        return "about";
    }
    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Sign Up");
        model.addAttribute("user", new User());
        return "signup";
    }
    // this is for registering the user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") UserRequest user, BindingResult result,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                               Model model, HttpSession session) {
    	try {
    	    if (!agreement) {
                System.out.println("You have not agreed to terms and condition");
                throw new Exception("You have not agreed to terms and condition");
            }
    	    if (result.hasErrors()) {
    	        model.addAttribute("user", user);
    	        return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserResponse userResponse = homeServices.createUser(user);
            System.out.println(userResponse.getName());
            System.out.println(userResponse.getRole());
            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
            return "signup";
        }
    	catch (Exception e) {
    	    e.printStackTrace();
    	    model.addAttribute("user", user);
    	    session.setAttribute("message", new Message("Something went wrong. "+e.getMessage(), "alert-danger"));
            return "signup";
    	}
    }

    // Handler for resetting password
    @GetMapping("/forgotPassword")
    public String forGotPassword(Model model) {
        model.addAttribute("title", "Forgot Password ?");
        return "forgotPassword";
    }
}