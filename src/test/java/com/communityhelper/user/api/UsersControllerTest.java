package com.communityhelper.user.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.user.api.representation.UserDTO;
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
}
