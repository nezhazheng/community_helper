package com.communityhelper.category.api.representation;

import com.communityhelper.category.Category;
import com.communityhelper.category.Orderable;
import com.communityhelper.merchat.Merchant;

public class ItemDTO implements Orderable, Comparable<Orderable> {
    private Integer id;
    private Integer categoryId;
    private String name;
    private Double score;
    private String iconURL;
    private Boolean isCategory;
    private Integer order;
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    public String getIconURL() {
        return iconURL;
    }
    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
    public Boolean getIsCategory() {
        return isCategory;
    }
    public void setIsCategory(Boolean isCategory) {
        this.isCategory = isCategory;
    }
    
    public static ItemDTO categoryToItem(Category category){
        ItemDTO item = new ItemDTO();
        item.setId(category.getId());
        item.setIsCategory(true);
        item.setName(category.getName());
        item.setScore(0.0);
        item.setOrder(category.getOrder());
        item.setCategoryId(category.getCategoryId());
        return item;
    }
    
    public static ItemDTO merchantToItem(Merchant merchant){
        ItemDTO item = new ItemDTO();
        item.setId(merchant.getId());
        item.setIsCategory(false);
        item.setName(merchant.getName());
        item.setScore(merchant.getScore());
        item.setOrder(merchant.getOrder());
        item.setCategoryId(merchant.getCategoryId());
        return item;
    }
    
    @Override
    public int compareTo(Orderable o) {
        return this.getOrder().compareTo(o.getOrder());
    }
}
