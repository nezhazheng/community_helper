package com.communityhelper.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.merchat.Merchant;

@Service
public class CategoryService {
    public List createCategoryList(List<Category> categories,
            List<Merchant> merchants) {
        List result = new ArrayList();
        result.addAll(categories);
        result.addAll(merchants);
        return result;
    }

    public Page createCategoryPage(Page categories, Page merchants) {
        Page categoryPage = new Page(categories.getPageIndex(), categories.getMaxResult());
        List result = new ArrayList();
        result.addAll(categories.getList());
        if(result.size() < categories.getMaxResult()){
            int offset = categories.getMaxResult() - result.size();
            if(offset > merchants.getList().size()) {
                offset = merchants.getList().size();
            }
            result.addAll(merchants.getList().subList(0, offset));
        }
        Collections.sort(result);
        categoryPage.setTotalResult(categories.getTotalResult() + merchants.getTotalResult());
        categoryPage.setList(result);
        return categoryPage;
    }
}
