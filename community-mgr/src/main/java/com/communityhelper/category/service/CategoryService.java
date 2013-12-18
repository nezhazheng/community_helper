package com.communityhelper.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.api.CategoryDTO;
import com.communityhelper.merchant.Merchant;

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
            dto.setStatus(merchant.getAuthStatus().name());
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
    
    /**
     * 插入用
     * @param categoryId
     * @param order
     * @return
     */
    @Transactional
    public Integer inrcOrder(Integer categoryId, Integer order) {
        if (order == 0) {
            return -1;
        }
        int merchantCount = entityManager.createNativeQuery("update merchant set morder = morder + 1 where category_id = "+categoryId+" and auth_status = 'VALID' and morder >= " + order).executeUpdate();
        int categoryCount = entityManager.createNativeQuery("update category set corder = corder + 1 where category_id = "+categoryId+" and corder >= " + order).executeUpdate();
        return categoryCount + merchantCount;
    }
    
    /**
     * 删除或修改为未审核状态时用，总之在商户列表不再显示时，请调用此方法
     * @param categoryId 被删除的CategoryId
     * @param order     被删除的Order
     * @return
     */
    @Transactional
    public Integer reduceOrder(Integer categoryId, Integer order) {
        if (order == 0) {
            return -1;
        }
        int merchantCount = entityManager.createNativeQuery("update merchant set morder = morder - 1 where category_id = "+categoryId+" and auth_status = 'VALID' and morder > " + order).executeUpdate();
        int categoryCount = entityManager.createNativeQuery("update category set corder = corder - 1 where category_id = "+categoryId+" and corder > " + order).executeUpdate();
        return categoryCount + merchantCount;
    }
    
    /**
     * 更新此商户相关的顺序
     * @param updateMerchant
     * @param updateOrder
     * @return
     */
    @Transactional
    public void updateRelatedOrder(Merchant updateMerchant, Integer updateOrder, Integer categoryId) {
        if(updateOrder == 0) {
            return;
        }
        Integer oldOrder = updateMerchant.getOrder();
        // 审核情况，order顺序递增
        if(0 ==  oldOrder && updateOrder > oldOrder){
            this.inrcOrder(categoryId, updateOrder);
        }
        // 更新情况, order调换
        if(0 != oldOrder && updateOrder != oldOrder){
            Merchant replacedMerchant = Merchant.findMerchantByOrderAndCategoryId(updateOrder, categoryId);
            // TODO 如果找不着 找类别最大的那个做递增
            if(replacedMerchant != null){
                replacedMerchant.setOrder(oldOrder);
                replacedMerchant.merge();
            } else {
                Category replacedCategory = Category.findCategoryByOrderAndCategoryId(updateOrder, categoryId);
                if(replacedCategory != null) {
                    replacedCategory.setOrder(oldOrder);
                    replacedCategory.merge();
                }
            }
        }
    }
}
