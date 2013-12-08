package com.communityhelper.user.api;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.api.representation.MerchantRequest;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.User;
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
    
    @Transactional
    @Test
    public void should_add_merchant_correct() throws Exception {
        //Given
        MerchantRequest dto = new MerchantRequest();
        dto.setName("测试商户");
        dto.setDesc("测试描述");
        
        //When
        post("/user/{userId}/merchant/auth", dto, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        //Then
        List<Merchant> merchants = Merchant.findAllMerchants();
        assertEquals(3, merchants.size());
    }
    
    @Test
    public void should_update_user() throws Exception{
        // Given
        UserDTO user = new UserDTO();
        user.setPhonenum("admin");
        user.setAddress("test");
        
      //When
        post("/user/{userId}/update", user, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        User valid = User.findUser(1);
        assertNotEquals("admin", valid.getPhonenum());
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
        
        RealNameAuth valid = RealNameAuth.findRealNameAuth(1);
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
        post("/user/{userId}/realnameauth", user, 2)
        
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("004")));
    }
    
    @Test
    public void should_return_all_my_merchants() throws Exception{
        // Given
        MerchantRequest dto = new MerchantRequest();
        dto.setVersion("1.0.0");
        
        // When
        post("/user/{userId}/merchant", dto, 1)
        
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result", hasSize(2)));
    }
}
