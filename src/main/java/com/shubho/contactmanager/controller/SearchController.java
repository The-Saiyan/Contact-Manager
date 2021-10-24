package com.shubho.contactmanager.controller;

import com.shubho.contactmanager.model.Contact;
import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.repository.ContactRepository;
import com.shubho.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
        User user = this.userRepository.getUserByUserEmail(principal.getName());
        List<Contact> contactList = this.contactRepository.findByNameContainingAndUser(query, user);
        return ResponseEntity.ok(contactList);
    }
}
