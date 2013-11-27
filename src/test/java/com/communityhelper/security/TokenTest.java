package com.communityhelper.security;

import static org.junit.Assert.assertEquals;

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
        
        Token verifyToken = tokenService.verifyToken(key);
        assertEquals(key, verifyToken.getKey());
    }
}

