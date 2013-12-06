package com.communityhelper.software.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Test;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.api.APIRequest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value ={"/Software.yml"})
public class SoftwareControllerTest extends MVCTestEnviroment{
    
    @Test
    public void should_not_upgrade() throws IOException, Exception{
        //Given
        APIRequest device = new APIRequest();
        device.setChannel("5");
        device.setPlatform("ios");
        device.setVersion("3.12.0.1");
        
        //When
        post("/software/launch", device)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.launchImage.url", is("http://localhost/image/i.png")))
        .andExpect(jsonPath("$.result.upgrade", is(false)));
    }
    
    @Test
    public void should_upgrade() throws IOException, Exception{
        //Given
        APIRequest device = new APIRequest();
        device.setChannel("5");
        device.setPlatform("android");
        device.setVersion("3.12.0.1");
        
        //When
        post("/software/launch", device)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.upgrade", is(true)))
        .andExpect(jsonPath("$.result.updateSoftware.upgradeDesc", is("shengjihappy")));;
    }
}
