package com.shubho.contactmanager.DAO.response;

import com.shubho.contactmanager.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private String role;
    private Boolean enabled;
    private String imageUrl;
    private String about;
    private List<Contact> contacts;
}
