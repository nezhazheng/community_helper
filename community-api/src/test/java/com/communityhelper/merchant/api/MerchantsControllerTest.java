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
import com.communityhelper.api.APIRequest;
import com.communityhelper.merchant.Merchant;
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
        assertEquals(6, merchants.size());
    }
    
    @Transactional
    @Test
    public void should_add_merchant_collection_success() throws Exception{
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
    }
    
    @Test
    public void should_got_merchant_detail() throws Exception{
        //Given
        APIRequest request = new APIRequest();
        request.setChannel("1");
        
        //When
        post("/merchant/{merchantId}", request, 1)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.feedbackList.list", hasSize(1)))
        .andExpect(jsonPath("$.result.feedbackList.list[0].phonenum", is("13311008877")));
    }
}


