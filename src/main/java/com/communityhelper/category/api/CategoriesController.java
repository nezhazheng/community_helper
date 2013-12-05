package com.communityhelper.category.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.api.representation.CategoryListRequest;
import com.communityhelper.category.service.CategoryService;
import com.communityhelper.merchat.Merchant;

import static com.communityhelper.api.APIResponse.*;
@Controller
@RequestMapping(value = "/category", method = RequestMethod.POST)
public class CategoriesController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 商户首页
     * @return
     */
    @RequestMapping
    public 
    @ResponseBody
    APIResponse index(@RequestBody APIRequest device){
        Page categories = Category.findChildCategories(Category.DEFAULT_ROOT_ID, 1, 10, device.getCommunityId());
        Page merchants = Merchant.findValidMerchantsByCategoryId(Category.DEFAULT_ROOT_ID, 1, 10, device.getCommunityId());
        Page categoryPage = categoryService.createCategoryPage(categories, merchants);
        return success("查询成功").result(categoryPage);
    }
    
    /**
     * 类别列表（含商户）
     */
    @RequestMapping(value = "/{categoryId}")
    public
    @ResponseBody
    APIResponse categoryList(@PathVariable(value = "categoryId") Integer categoryId,
            @RequestBody CategoryListRequest request){
        Page categories = Category.findChildCategories(categoryId,
                request.getStart(), request.getSize(), request.getCommunityId());
        Page merchants = Merchant.findValidMerchantsByCategoryId(categoryId,
                request.getStart(), request.getSize(), request.getCommunityId());
        Page categoryPage = categoryService.createCategoryPage(categories, merchants);
        return success("查询成功").result(categoryPage);
    }
}
