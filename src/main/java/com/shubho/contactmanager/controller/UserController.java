package com.shubho.contactmanager.controller;

import com.shubho.contactmanager.helper.Message;
import com.shubho.contactmanager.model.Contact;
import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.repository.ContactRepository;
import com.shubho.contactmanager.repository.UserRepository;
import com.shubho.contactmanager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    UserServices userServices;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
    @PostMapping(value = "/process-contact", consumes = {"multipart/form-data"})
    public String processContact(@RequestPart("photo") MultipartFile file,
                                 HttpSession httpSession,
                                 @ModelAttribute Contact contact, Principal principal) {
        try {
            String username = principal.getName();
            User user = userRepository.getUserByUserEmail(username);
            //Uploading image file
            if (file.isEmpty()) {
                // error message goes here
                System.out.println("File is empty");
            } else {
                contact.setImageUrl(file.getOriginalFilename());
                File file1 = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            this.userRepository.save(user);
            System.out.println("Data" + contact);
            httpSession.setAttribute("message", new Message("Your contact is added successfully!", "success"));
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            httpSession.setAttribute("message", new Message("Something went wrong!", "danger"));
        }
        return "normal/add_contact_form";
    }

    // pagination logic -> one page will contain 5 contacts
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") int page, Model m, Principal principal) {
        m.addAttribute("title", "View Contacts");
        String userName = principal.getName();
        User user = userRepository.getUserByUserEmail(userName);
        // setting pageable of page and the number of rows per page
        // in this case "page" is our variable and number of rows per page is 5
        Pageable pageable = PageRequest.of(page, 10);
        Page<Contact> contactList = this.contactRepository.findContactByUser(user.getId(), pageable);
        m.addAttribute("contactList", contactList);
        // finding out at what page no i am at right now
        m.addAttribute("currentPage", page);
        //finding how many pages will be there
        m.addAttribute("totalPages", contactList.getTotalPages());
        return "normal/show_contacts";
    }

    @GetMapping("/{cId}/contact")
    public String viewContact(@PathVariable("cId") long cId, Model model, Principal principal) {
        model.addAttribute("title", "View Contact");
        Optional<Contact> contactOptional = this.contactRepository.findById(cId);
        Contact contact = contactOptional.get();
        String username = principal.getName();
        User user = this.userRepository.getUserByUserEmail(username);
        if (user.getId() == contact.getUser().getId())
            model.addAttribute("contact", contact);
        return "normal/view_contact";
    }

    @Transactional
    @RequestMapping(value = "/delete-contact/{cId}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable("cId") long cId,
                                Model model,
                                Principal principal,
                                HttpSession session) {
        Optional<Contact> contactOptional = this.contactRepository.findById(cId);
        String username = principal.getName();
        User user = this.userRepository.getUserByUserEmail(username);
        Contact contact = contactOptional.get();
        System.out.println(contact);
        if (user.getId() == contact.getUser().getId()) {
            contact.setUser(null);
            this.contactRepository.delete(contact);
            session.setAttribute("message", new Message("Contact deleted successfully.", "success"));
        } else return "normal/Error_Page";
        return "redirect:/user/show-contacts/0";
    }

    @GetMapping("/userProfile")
    public String yourProfile(Principal principal, Model model) {
        String username = principal.getName();
        User user = this.userRepository.getUserByUserEmail(username);
        model.addAttribute("user", user);
        return "normal/profile";
    }


    // Controller for opening settings tab
    @GetMapping("/settings")
    public String openSettings() {
        return "normal/settings";
    }

    //Change password
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 HttpSession session) {
        User currentUser = this.userRepository.getUserByUserEmail(principal.getName());
        if (newPassword != "" && newPassword != null
                && bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())
                && newPassword.equals(confirmPassword)) {
            currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            this.userRepository.save(currentUser);
            session.setAttribute("message", new Message("Your password is changes ", "success"));
        } else {
            session.setAttribute("message", new Message("Wrong Password", "danger"));
            return "redirect:/user/settings";
        }
        return "redirect:/user/index";
    }
}
