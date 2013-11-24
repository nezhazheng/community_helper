package com.communityhelper;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(locations = {"/WEB-INF/spring/webmvc-config.xml"})
@Ignore
public class MVCTestEnviroment extends TestEnviroment {
    @Autowired  
    protected WebApplicationContext wac;  
    
    protected MockMvc mockMvc;
    
    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(wac).build();
    }
    
    protected ResultActions post(String url, Object content){ 
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(content)));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected ResultActions post(String url, Object content, Object... urlVariables){ 
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(url, urlVariables)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(content)));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
