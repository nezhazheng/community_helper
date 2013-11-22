package com.communityhelper.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.communityhelper.TestEnviroment;
import com.communityhelper.TestUtil;
import com.communityhelper.user.api.UserDTO;

@WebAppConfiguration
@ContextConfiguration(locations = {"/WEB-INF/spring/webmvc-config.xml"})
public class UsersControllerTest extends TestEnviroment {
    @Autowired  
    protected WebApplicationContext wac;  
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void should_auth_correct_with_username_password() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername("admin");
        user.setPassword("111111");
        user.setVersion("10.0");
        
        mockMvc.perform(post("/user/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(user)))
        .andExpect(status().isOk());
        
        
    }
}
