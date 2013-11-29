package com.communityhelper.sms.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.communityhelper.TestEnviroment;

public class SMSServiceTest extends TestEnviroment {
    @Autowired
    private SMSSerice service;
    
    @Test
    public void should_send_sms(){
        boolean result = service.sendValidCode("111222", "13311008875");
        System.out.println(result);
    }
}
