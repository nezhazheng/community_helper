package com.communityhelper.category.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.category.Category;
import com.communityhelper.category.service.CategoryService;
import com.communityhelper.merchant.Merchant;
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
    List<CategoryDTO> categoryMerchantList(@RequestParam("categoryId") Integer categoryId,
            @RequestParam("communityId") Integer communityId){
        List<Category> categories = Category.findAllChildCategories(categoryId, communityId);
        List<Merchant> merchants = Merchant.findAllValidMerchantsByCategoryId(categoryId, communityId);
        List<CategoryDTO> categoryPage = categoryService.createCategoryPage(categories, merchants);
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
            @RequestParam(value = "iconId") Integer iconId,
            @RequestParam(value = "communityId") Integer communityId) {
        categoryService.inrcOrder(categoryId, order);
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCommunityId(communityId);
        category.setName(name);
        category.setIconId(iconId);
        category.setOrder(order);
        category.persist();
        return "success";
    }
    
    /**
     * 修改类别
     * @param name
     * @param updateOrder
     * @param updateCategoryId
     * @param communityId
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public
    @ResponseBody
    String modifyCategory(@RequestParam("name") String name,
            @RequestParam("order") Integer updateOrder,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "categoryId") Integer updateCategoryId,
            @RequestParam(value = "iconId") Integer iconId) {
        Category category = Category.findCategory(id);
        
        categoryService.updateRelatedOrder(category.getOrder(), category.getCategoryId(), updateOrder, updateCategoryId);
        
        category.setCategoryId(updateCategoryId);
        category.setName(name);
        category.setIconId(iconId);
        category.setOrder(updateOrder);
        category.merge();
        return "success";
    }
    
    /**
     * 删除类别或商户
     * @param id
     * @param isCategory
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public
    @ResponseBody
    @Transactional
    String deleteCategory(@RequestParam(value = "id") Integer id, @RequestParam(value = "isCategory") Boolean isCategory) {
        Integer order = 0;
        Integer categoryId = 0;
        if(isCategory) {
            Category category = Category.findCategory(id);
            categoryId = category.getCategoryId();
            order = category.getOrder();
            category.remove();
            categoryService.reduceOrder(categoryId, order);
        }
        return "success";
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
