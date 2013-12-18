package com.communityhelper.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.StandardCategory;
import com.communityhelper.category.api.representation.ItemDTO;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.software.Image;
import com.communityhelper.user.User;
import com.communityhelper.user.UserServiceStatus;

@Service
public class CategoryService {
    public Page<ItemDTO> createCategoryPage(Page<Category> categories, Page<Merchant> merchants) {
        Page<ItemDTO> categoryPage = new Page<ItemDTO>(categories.getPageIndex(), categories.getMaxResult());
        List<ItemDTO> result = new ArrayList<ItemDTO>();
        for(Category category: categories.getList()){
            ItemDTO dto = ItemDTO.categoryToItem(category);
            Image icon = Image.findImage(category.getIconId());
            if(null != icon){
                dto.setIconURL(icon.getUrl());
            }
            result.add(dto);
        }
        for(Merchant merchant : merchants.getList()){
            ItemDTO dto = ItemDTO.merchantToItem(merchant);
            if(merchant.getUserId() != null && 0 != merchant.getUserId()) {
                dto.setUserServiceStatus(User.findUser(merchant.getUserId()).getUserServiceStatus());
            } else {
                dto.setUserServiceStatus(UserServiceStatus.DO_BUSINESS);
            }
            result.add(dto);
        }
        Collections.sort(result);
        categoryPage.setTotalResult(categories.getTotalResult() + merchants.getTotalResult());
        categoryPage.setList(result);
        return categoryPage;
    }
    
    @Cacheable(value = "categories", key = "'categories'")
    public List<StandardCategory> findAllStandardCategorys() {
        List<StandardCategory> categories = StandardCategory.findAllStandardCategorys();
        return categories;
    }
}
