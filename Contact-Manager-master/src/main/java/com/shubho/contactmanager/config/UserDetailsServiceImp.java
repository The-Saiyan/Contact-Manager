package com.shubho.contactmanager.config;

import com.shubho.contactmanager.model.User;
import com.shubho.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetailsImp loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find User. ");
        }
        return new UserDetailsImp(user);
    }
}
