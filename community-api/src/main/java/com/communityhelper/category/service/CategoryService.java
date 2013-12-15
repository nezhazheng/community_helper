package com.communityhelper.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.api.representation.ItemDTO;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.software.Image;

@Service
public class CategoryService {
    public Page createCategoryPage(
            Page<Category> categories, Page<Merchant> merchants) {
        Page categoryPage = new Page(categories.getPageIndex(), categories.getMaxResult());
        List result = new ArrayList();
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
            result.add(dto);
        }
        Collections.sort(result);
        categoryPage.setTotalResult(categories.getTotalResult() + merchants.getTotalResult());
        categoryPage.setList(result);
        return categoryPage;
    }
}
