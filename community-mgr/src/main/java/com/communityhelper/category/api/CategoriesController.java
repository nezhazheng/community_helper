package com.communityhelper.category.api;

import static com.communityhelper.api.APIResponse.success;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.service.CategoryService;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantStatus;
@Controller
@RequestMapping(value = "/category")
public class CategoriesController {
    
    @Autowired
    private CategoryService categoryService;
    
    
    /**
     * 类别列表（含商户）
     */
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Page categoryMerchantList(@RequestParam("categoryId") Integer categoryId,
            @RequestParam("communityId") Integer communityId,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "size", defaultValue = "10") Integer size){
        Page categories = Category.findChildCategories(categoryId, start, size, communityId);
        Page merchants = Merchant.findValidMerchantsByCategoryId(categoryId, start, size, communityId);
        Page categoryPage = categoryService.createCategoryPage(categories, merchants);
        return categoryPage;
    }
    
    /**
     * 增加类别
     * @param name
     * @param order
     * @param categoryId
     * @param communityId
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addCategory(@RequestParam("name") String name,
            @RequestParam("order") Integer order,
            @RequestParam(value = "categoryId") Integer categoryId,
            @RequestParam(value = "communityId") Integer communityId) {
        categoryService.inrcOrder(categoryId, order);
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCommunityId(communityId);
        category.setName(name);
        category.setOrder(order);
        category.persist();
        return "success";
    }
    
    /**
     * 删除类别
     * @param id
     * @param isCategory
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public
    @ResponseBody
    String deleteCategory(@RequestParam(value = "id") Integer id, @RequestParam(value = "isCategory") Boolean isCategory) {
        Integer order = 0;
        Integer categoryId = 0;
        if(isCategory) {
            Category category = Category.findCategory(id);
            categoryId = category.getCategoryId();
            order = category.getOrder();
            category.remove();
        } else {
            Merchant merchant = Merchant.findMerchant(id);
            categoryId = merchant.getCategoryId();
            order = merchant.getOrder();
            merchant.remove();
        }
        categoryService.reduceOrder(categoryId, order);
        return "success";
    }
    
    /**
     * 审核商户
     * @param status #com.communityhelper.category.MerchantStatus
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse audit(@RequestParam("status") String status,
            @RequestParam("merchantId") Integer merchantId,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("order") Integer order){
        Merchant merchant = Merchant.findMerchant(merchantId);
        if(!order.equals(merchant.getOrder())){
            categoryService.inrcOrder(categoryId, order);
        }
        merchant.setStatus(MerchantStatus.valueOf(status));
        merchant.setOrder(order);
        merchant.setCategoryId(categoryId);
        merchant.merge();
        return success("审核成功");
    }
    
    /**
     * 类别列表（不含商户）
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Category> categoryList(@RequestParam("communityId") Integer communityId){
        List<Category> categories = Category.findAllCategories(communityId);
        return categories;
    }
}
