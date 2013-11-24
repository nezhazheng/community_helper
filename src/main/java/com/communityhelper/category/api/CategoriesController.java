package com.communityhelper.category.api;

import java.util.List;

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
import com.communityhelper.category.service.CategoryService;
import com.communityhelper.merchat.Merchant;

import static com.communityhelper.api.APIResponse.*;
@Controller
@RequestMapping("/category")
public class CategoriesController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 商户首页
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse index(@RequestBody APIRequest device){
        List<Category> categories = Category.findRootCategories();
        List<Merchant> merchants = Merchant.findRootMerchants();
        List categoryList = categoryService.createCategoryList(categories, merchants);
        return response().success("查询成功").result(categoryList);
    }
    
    /**
     * 类别列表（含商户）
     */
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.POST)
    public
    @ResponseBody
    APIResponse categoryList(@PathVariable("categoryId") Integer categoryId,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody APIRequest device){
        Page categories = Category.findChildCategories(categoryId, start, size);
        Page merchants = Merchant.findMerchantsByCategoryId(categoryId, start, size);
        Page categoryPage = categoryService.createCategoryPage(categories, merchants);
        return response().success("查询成功").result(categoryPage);
    }
}
