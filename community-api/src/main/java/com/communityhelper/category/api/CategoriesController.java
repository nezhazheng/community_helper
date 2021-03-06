package com.communityhelper.category.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.category.StandardCategory;
import com.communityhelper.category.api.representation.ItemDTO;
import com.communityhelper.category.service.CategoryService;
import com.communityhelper.page.Page;

import static com.communityhelper.api.APIResponse.*;
@Controller
@RequestMapping(value = "/category", method = RequestMethod.POST)
public class CategoriesController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 类别列表（含商户）
     */
    @RequestMapping(value = "/{categoryId}")
    public
    @ResponseBody
    APIResponse categoryList(@PathVariable(value = "categoryId") Integer categoryId,
            @RequestBody CategoryListRequest request) {
        Page<ItemDTO> categoryPage = categoryService.createCategoryPage(categoryId, request.getCommunityId(), request.getStart(), request.getSize());
        return success("查询成功").result(categoryPage);
    }
    
    /**
     * 标准类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/standard")
    public
    @ResponseBody
    APIResponse standardCategoryList(@RequestBody APIRequest request) {
        List<StandardCategory> categories = categoryService.findAllStandardCategorys();
        return success("查询成功").result(categories);
    }
}
