package com.communityhelper.category.api;

import com.communityhelper.category.Orderable;

public class CategoryDTO implements Orderable, Comparable<Orderable>{
    private Integer id;
    private Integer categoryId;
    private Integer merchantId;
    private String name;
    private Boolean leaf;
    private String status;
    private Integer order;
    private Boolean serviceEnable;
    private Integer iconId;
    public Integer getIconId() {
        return iconId;
    }
    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }
    public Boolean getServiceEnable() {
        return serviceEnable;
    }
    public void setServiceEnable(Boolean serviceEnable) {
        this.serviceEnable = serviceEnable;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
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
    public Boolean getLeaf() {
        return leaf;
    }
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public int compareTo(Orderable o) {
        return this.getOrder().compareTo(o.getOrder());
    }
}
