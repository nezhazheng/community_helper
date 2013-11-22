package com.communityhelper.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.communityhelper.user.User;

@Service("ourTokenService")
public class TokenService {
    @Autowired
    private KeyBasedPersistenceTokenService tokenService;
    
    public String getToken(UserDetails userDetails){
        String preToToken = userDetails.getUsername() + "=" + userDetails.getPassword();
        Token token = tokenService.allocateToken(preToToken);
        return token.getKey();
    }
    
    public boolean validate(String token) {
        Token verifyToken = null;
        try {
            verifyToken = tokenService.verifyToken(token);
        }catch (IllegalArgumentException e){
            return false;
        }
        String[] information = verifyToken.getExtendedInformation().split("=");
        User user = User.findUserByMobile(information[0]);
        if(user == null){
            return false;
        }
        if(user.getPassword().equals(information[1])){
            return true;
        }
        return false;
    }
    
    public UserDetails getUserFromToken(String token) {
        Token verifyToken = tokenService.verifyToken(token);
        String[] information = verifyToken.getExtendedInformation().split("=");
        User user = User.findUserByMobile(information[0]);
        RegisterUserDetails userDetails = new RegisterUserDetails(user);
        return userDetails;
    }
}
