package com.communityhelper.user.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.api.representation.MerchantDTO;
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
        MerchantDTO dto = new MerchantDTO();
        dto.setName("测试商户");
        dto.setDesc("测试描述");
        
        //When
        post("/user/{userId}/merchant/auth", dto, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        //Then
        List<Merchant> merchants = Merchant.findAllMerchants();
        assertEquals(1, merchants.size());
    }
    
    @Test
    public void should_update_user() throws Exception{
        // Given
        UserDTO user = new UserDTO();
        user.setPhonenum("admin");
        user.setPassword("111111");
        user.setAddress("test");
        
      //When
        post("/user/{userId}/update", user, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        User valid = User.findUser(1);
        assertEquals("111111", valid.getPassword());
        assertNotEquals("admin", valid.getPhonenum());
    }
}
