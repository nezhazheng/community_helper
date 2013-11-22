package com.communityhelper.category.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
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
    @RequestMapping(method = RequestMethod.GET)
    public 
    @ResponseBody
    APIResponse index(){
        List<Category> categories = Category.findRootCategories();
        List<Merchant> merchants = Merchant.findRootMerchants();
        List result = new ArrayList();
        result.addAll(categories);
        result.addAll(merchants);
        return response().success("查询成功").result(result);
    }
}
