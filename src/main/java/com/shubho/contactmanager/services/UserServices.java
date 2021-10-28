package com.shubho.contactmanager.services;

import com.shubho.contactmanager.helper.TransformObject;
import com.shubho.contactmanager.model.Contact;
import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.repository.UserRepository;
import com.shubho.contactmanager.DAO.requests.UserRequest;
import com.shubho.contactmanager.DAO.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User user = TransformObject.toUserObject(userRequest);
        userRepository.save(user);
        return TransformObject.getUserResponse(user);
    }
}
