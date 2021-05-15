package com.shubho.contactmanager.services;

import com.shubho.contactmanager.DAO.requests.UserRequest;
import com.shubho.contactmanager.DAO.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServices {
    @Autowired
    UserServices userServices;

    public UserResponse createUser(UserRequest userRequest) {
        return userServices.createUser(userRequest);
    }

}
