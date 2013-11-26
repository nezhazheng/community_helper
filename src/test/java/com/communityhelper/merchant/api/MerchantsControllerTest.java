package com.communityhelper.merchant.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.api.representation.MerchantDTO;

public class MerchantsControllerTest extends MVCTestEnviroment {
    @Transactional
    @Test
    public void should_add_merchant_success() throws Exception{
        //Given
        MerchantDTO dto = new MerchantDTO();
        dto.setName("测试商户");
        dto.setDesc("测试描述");
        
        //When
        post("/merchant", dto)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")));
        
        //Then
        List<Merchant> merchants = Merchant.findAllMerchants();
        assertEquals(1, merchants.size());
    }
}


