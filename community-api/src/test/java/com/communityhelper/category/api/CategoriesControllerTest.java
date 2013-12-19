package com.communityhelper.category.api;

import static org.hamcrest.Matchers.*;
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
        CategoryListRequest request = new CategoryListRequest();
        request.setChannel("5");
        request.setPlatform("ios");
        request.setVersion("3.12.0.1"); 
        request.setCommunityId(1);
        
        //When
        post("/category/0", request)
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.totalResult", is(5)))
        // test sort
        // 类别
        .andExpect(jsonPath("$.result.list[0].name", is("物业服务商")))
        .andExpect(jsonPath("$.result.list[0].serviceEnable", is(true)))
        .andExpect(jsonPath("$.result.list[0].isCategory", is(true)))
        
         // 商户
        .andExpect(jsonPath("$.result.list[1].isCategory", is(false)))
        .andExpect(jsonPath("$.result.list[1].id", is(6)))
        .andExpect(jsonPath("$.result.list[3].name", is("小红物业服务商")))
        .andExpect(jsonPath("$.result.list[3].serviceEnable", is(true)))
        .andExpect(jsonPath("$.result.list[3].isCategory", is(false)));
    }
    
    @Test
    public void should_got_index_page_sizeable() throws Exception{
        //Given
        CategoryListRequest request = new CategoryListRequest();
        request.setChannel("5");
        request.setPlatform("ios");
        request.setVersion("3.12.0.1"); 
        request.setCommunityId(1);
        request.setStart(2);
        request.setSize(2);
        
        //When
        post("/category/0", request)
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result.totalResult", is(5)))
        // test sort
        // 类别
        .andExpect(jsonPath("$.result.list[0].name", is("维修商")))
        .andExpect(jsonPath("$.result.list[0].serviceEnable", is(true)))
        .andExpect(jsonPath("$.result.list[0].isCategory", is(true)))
        
         // 商户
        .andExpect(jsonPath("$.result.list[1].isCategory", is(false)))
        .andExpect(jsonPath("$.result.list[1].id", is(2)));
    }
    
    @Test
    public void should_got_child_category_page() throws Exception{
        //Given
        APIRequest device = new APIRequest();
        device.setChannel("5");
        device.setPlatform("ios");
        device.setVersion("3.12.0.1"); 
        device.setCommunityId(1);
        
        //When
        ResultActions result = post("/category/1?size=2", device)
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.totalResult", is(4)));
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }
}
