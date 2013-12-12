package com.communityhelper.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.communityhelper.security.RegisterUserDetails;
import com.communityhelper.user.User;

@Component("userDetailService")
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = User.findUserByPhonenum(userName);
        if(user == null){
            throw new UsernameNotFoundException("UserName :" + userName);
        }
        RegisterUserDetails userDetails = new RegisterUserDetails(user);
        return userDetails;
    }
}
