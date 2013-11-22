package com.communityhelper.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;

import com.communityhelper.TestEnviroment;

public class TokenTest extends TestEnviroment{
    @Autowired
    KeyBasedPersistenceTokenService tokenService;
    @Test
    public void testTokenService(){
        Token token =  tokenService.allocateToken("admin=111111");
        String key = token.getKey();
        System.out.println(key);
        System.out.println(token.getExtendedInformation());
        System.out.println(token.getKeyCreationTime());
        
        Token verifyToken = tokenService.verifyToken(key);
        System.out.println(verifyToken.getKey());
        System.out.println(verifyToken.getExtendedInformation());
        System.out.println(verifyToken.getKeyCreationTime());
    }
}

