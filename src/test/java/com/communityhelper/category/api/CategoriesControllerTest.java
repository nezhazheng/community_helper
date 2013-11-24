package com.communityhelper.category.api;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.communityhelper.MVCTestEnviroment;
import com.communityhelper.api.APIRequest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = {"/Category.yml"})
public class CategoriesControllerTest extends MVCTestEnviroment {
    @Test
    public void should_got_index_page() throws Exception{
      //Given
        APIRequest device = new APIRequest();
        device.setChannel("5");
        device.setPlatform("ios");
        device.setVersion("3.12.0.1"); 
        
        //When
        post("/category", device)
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result", hasSize(4)))
        .andExpect(jsonPath("$.result[0].name", is("维修商")))
        .andExpect(jsonPath("$.result[*].name", containsInAnyOrder("小红物业服务商", "维修商", "物业服务商", "大李维修商")));
    }
    
    @Test
    public void should_got_child_category_page() throws Exception{
      //Given
        APIRequest device = new APIRequest();
        device.setChannel("5");
        device.setPlatform("ios");
        device.setVersion("3.12.0.1"); 
        
        //When
        ResultActions result = post("/category/1", device, 1, 2)
        //Then
        .andExpect(status().isOk());
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }
}
