package com.shubho.contactmanager.helper;

import com.shubho.contactmanager.model.Contact;
import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.DAO.requests.UserRequest;
import com.shubho.contactmanager.DAO.response.UserResponse;
import java.util.List;

public class TransformObject {
    public static User toUserObject(UserRequest userRequest) {
        String name = userRequest.getName();
        String email = userRequest.getEmail();
        String role = userRequest.getRole();
        Boolean enabled = userRequest.getEnabled();
        String imageUrl = userRequest.getImageUrl();
        String about = userRequest.getAbout();
        List<Contact> contacts = userRequest.getContacts();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setEnabled(enabled);
        user.setImageUrl(imageUrl);
        user.setAbout(about);
        user.setContacts(contacts);
        user.setPassword(userRequest.getPassword());
        return user;
    }
    public static UserResponse getUserResponse(User user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getRole(),
                                user.getEnabled(), user.getImageUrl(), user.getAbout(),
                                user.getContacts());
    }
}
