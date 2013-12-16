package com.communityhelper.user.api;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Test;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.api.APIRequest;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.User;
import com.communityhelper.user.UserServiceStatus;
import com.communityhelper.user.api.representation.UserDTO;
import com.github.springtestdbunit.annotation.DatabaseSetup;
@DatabaseSetup("/User.yml")
public class UsersControllerTest extends MVCTestEnviroment {
    @Test
    public void should_auth_success_with_correct_username_password() throws Exception{
        // Given
        UserDTO user = new UserDTO();
        user.setPhonenum("admin");
        user.setPassword("111111");
        user.setVersion("10.0");
        
        // When
        post("/user/authenticate",user)
        // Then
        .andExpect(status().isOk());
    }
    
    @Test
    public void should_update_user() throws Exception{
        // Given
        UserDTO user = new UserDTO();
        user.setPhonenum("admin");
        user.setAddress("test");
        
        // When
        post("/user/{userId}/update", user, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        User valid = User.findUser(1);
        assertNotEquals("admin", valid.getPhonenum());
    }
    
    @Test
    public void should_change_user_status() throws Exception{
        User valid = User.findUser(1);
        assertNotEquals("DO_BUSINESS", valid.getPhonenum());
        
        UserDTO user = new UserDTO();
        user.setUserServiceStatus(UserServiceStatus.BUSY);
        
        // When
        post("/user/{userId}/update", user, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        valid = User.findUser(1);
        assertNotEquals("BUSY", valid.getUserServiceStatus());
    }
    
    @Test
    public void should_real_name_auth_success() throws Exception{
        // Given
        UserDTO user = new UserDTO();
        user.setRealName("abc");
        user.setAddress("bcd");
        
        // When
        post("/user/{userId}/realnameauth", user, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        RealNameAuth valid = RealNameAuth.findRealNameAuthByUserId(1);
        assertEquals("abc", valid.getRealName());
        assertThat(valid.getUserId(), is(1));
    }
    
    @Test
    public void should_real_name_auth_return_wait() throws Exception{
        // Given
        UserDTO user = new UserDTO();
        user.setRealName("abc");
        user.setAddress("bcd");
        
        // When
        post("/user/{userId}/realnameauth", user, 1)
        
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        // When
        post("/user/{userId}/realnameauth", user, 1)
        
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("004")));
    }
    
    @Test
    public void should_return_all_my_info() throws Exception{
        // Given
        APIRequest request = new APIRequest();
        request.setVersion("1.0.0");
        
        // When
        post("/user/{userId}/my", request, 1)
        
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.merchants", hasSize(2)))
        .andExpect(jsonPath("$.result.user.realNameAuthStatus", is("HAS_NOT_AUTH")))
        .andExpect(jsonPath("$.result.realNameAuth", nullValue()));
        
        // When WAIT_TO_AUTH will got realNameAuth info
        post("/user/{userId}/my", request, 2)
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.user.realNameAuthStatus", is("WAIT_TO_AUTH")))
        .andExpect(jsonPath("$.result.realNameAuth.realName", is("zhengzhengzheng")));
    }
    
    @Test
    public void should_got_my_collection() throws Exception{
        // Given
        APIRequest request = new APIRequest();
        request.setVersion("1.0.0");
        
        // When
        post("/user/{userId}/merchantcollection", request, 1)
        
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result", hasSize(1)));
    }
}
