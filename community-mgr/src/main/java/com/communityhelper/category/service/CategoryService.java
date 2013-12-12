package com.communityhelper.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.api.CategoryDTO;
import com.communityhelper.merchat.Merchant;

@Service
public class CategoryService {
    @PersistenceContext
    transient EntityManager entityManager;
    
    public List createCategoryList(List<Category> categories,
            List<Merchant> merchants) {
        List result = new ArrayList();
        result.addAll(categories);
        result.addAll(merchants);
        return result;
    }

    public Page createCategoryPage(Page<Category> categories, Page<Merchant> merchants) {
        Page categoryPage = new Page(categories.getPageIndex(), categories.getMaxResult());
        List result = new ArrayList();
        for(Category category : categories.getList()){
            CategoryDTO dto = new CategoryDTO();
            dto.setLeaf(false);
            dto.setStatus("");
            dto.setName(category.getName());
            dto.setCategoryId(category.getCategoryId());
            dto.setOrder(category.getOrder());
            dto.setId(category.getId());
            result.add(dto);
        }
        for(Merchant merchant : merchants.getList()){
            CategoryDTO dto = new CategoryDTO();
            dto.setLeaf(true);
            dto.setStatus(merchant.getStatus().name());
            dto.setName(merchant.getName());
            dto.setCategoryId(merchant.getCategoryId());
            dto.setOrder(merchant.getOrder());
            dto.setMerchantId(merchant.getId());
            result.add(dto);
        }
        Collections.sort(result);
        categoryPage.setTotalResult(categories.getTotalResult() + merchants.getTotalResult());
        categoryPage.setList(result);
        return categoryPage;
    }
    
    @Transactional
    public Integer inrcOrder(Integer categoryId, Integer order) {
        int merchantCount = entityManager.createNativeQuery("update merchant set morder = morder + 1 where category_id = "+categoryId+" and morder >= " + order).executeUpdate();
        int categoryCount = entityManager.createNativeQuery("update category set corder = corder + 1 where category_id = "+categoryId+" and corder >= " + order).executeUpdate();
        return categoryCount + merchantCount;
    }
    
    @Transactional
    public Integer reduceOrder(Integer categoryId, Integer order) {
        int merchantCount = entityManager.createNativeQuery("update merchant set morder = morder - 1 where category_id = "+categoryId+" and morder > " + order).executeUpdate();
        int categoryCount = entityManager.createNativeQuery("update category set corder = corder - 1 where category_id = "+categoryId+" and corder > " + order).executeUpdate();
        return categoryCount + merchantCount;
    }
}
