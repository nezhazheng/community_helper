package com.communityhelper.category.api;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.communityhelper.TestEnviroment;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = {"/Category.yml", "/Merchant.yml"})
@WebAppConfiguration
@ContextConfiguration(locations = {"/WEB-INF/spring/webmvc-config.xml"})
public class CategoriesControllerTest extends TestEnviroment {
    @Autowired  
    protected WebApplicationContext wac;  
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void should_got_categories_and_merchants() throws Exception{
        //Given 
        
        //When
        mockMvc.perform(get("/category"))
        //Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("000")))
        .andExpect(jsonPath("$.result", hasSize(4)))
        .andExpect(jsonPath("$.result[0].name", is("维修商")))
        .andExpect(jsonPath("$.result[*].name", containsInAnyOrder("小红物业服务商", "维修商", "物业服务商", "大李维修商")));
    }
}
