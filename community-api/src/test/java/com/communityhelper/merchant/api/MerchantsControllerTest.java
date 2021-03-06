package com.communityhelper.merchant.api;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantStatus;
import com.communityhelper.merchant.MyMerchantCollection;
import com.communityhelper.merchat.api.MerchantRequest;
import com.communityhelper.user.User;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/Category.yml")
public class MerchantsControllerTest extends MVCTestEnviroment {
    @Transactional
    @Test
    public void should_add_merchant_success() throws Exception{
        //Given
        MerchantRequest dto = new MerchantRequest();
        dto.setName("测试商户");
        dto.setDesc("测试描述");
        
        //When
        post("/merchant", dto)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        //Then
        List<Merchant> merchants = Merchant.findAllMerchants();
        assertEquals(8, merchants.size());
    }
    
    @Transactional
    @Test
    public void should_user_auth_merchant_correct() throws Exception {
        // Given
        MerchantRequest dto = new MerchantRequest();
        dto.setName("测试商户");
        dto.setDesc("测试描述");
        dto.setUserId(1);
        
        // When
        post("/merchant/auth", dto)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        // Then
        List<Merchant> merchants = Merchant.findMerchantsByUserId(1);
        assertEquals(1, merchants.size());
        // the default serviceEnable is false
        assertEquals(false, merchants.get(0).getServiceEnable());
        // the default authStatus is NOT_VALID
        assertEquals(MerchantStatus.NOT_VALID, merchants.get(0).getAuthStatus());
    }
    
    @Transactional
    @Test
    public void should_add_merchant_collection_success() throws Exception {
        //Given
        MerchantRequest request = new MerchantRequest();
        request.setUserId(1);
        
        //When
        post("/merchant/{merchantId}/collection", request, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        MyMerchantCollection collection = MyMerchantCollection.find(1, 2);
        assertNotNull(collection);
        
        //Then
        User tryUser = User.findUser(1);
        assertEquals("zhengzheng", tryUser.getRealName());
        
        // When
        post("/merchant/{merchantId}", request, 1)
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.merchant.collected", is(true)));
    }
    
    @Test
    public void should_got_merchant_detail() throws Exception{
        // Given
        MerchantRequest request = new MerchantRequest();
        request.setChannel("1");
        request.setUserId(1);
        
        // When
        post("/merchant/{merchantId}", request, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.merchant.authStatus", is("VALID")))
        .andExpect(jsonPath("$.result.feedbackList.list", hasSize(1)))
        .andExpect(jsonPath("$.result.merchant.collected", is(false)))
        .andExpect(jsonPath("$.result.feedbackList.list[0].phonenum", is("13311008877")));
    }
}


