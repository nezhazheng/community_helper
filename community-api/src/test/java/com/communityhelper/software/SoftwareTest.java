package com.communityhelper.software;

import static org.junit.Assert.*;

import org.junit.Test;

import com.communityhelper.TestEnviroment;

public class SoftwareTest extends TestEnviroment{
    @Test
    public void should_upgrade(){
        String originVersion = "1.0.0.1";
        Software software = new Software();
        
        software.setVersion("10.0");
        assertTrue(software.upgrade(originVersion));
        
        // 位数大于
        software.setVersion("1.0.0.1.1");
        assertTrue(software.upgrade(originVersion));
        
        // 位数相等
        software.setVersion("1.0.0.1");
        assertFalse(software.upgrade(originVersion));
        
        // 位数相等
        software.setVersion("1.0.0.2");
        assertTrue(software.upgrade(originVersion));
        
        // 位数小于
        software.setVersion("1.0.0");
        assertFalse(software.upgrade(originVersion));
    }
}
