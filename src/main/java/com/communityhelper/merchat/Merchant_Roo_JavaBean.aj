// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.merchat;

import com.communityhelper.category.MerchantStatus;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

privileged aspect Merchant_Roo_JavaBean {
    
    public Integer Merchant.getId() {
        return this.id;
    }
    
    public void Merchant.setId(Integer id) {
        this.id = id;
    }
    
    public Integer Merchant.getCategoryId() {
        return this.categoryId;
    }
    
    public void Merchant.setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public Integer Merchant.getUserId() {
        return this.userId;
    }
    
    public void Merchant.setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer Merchant.getCommunityId() {
        return this.communityId;
    }
    
    public void Merchant.setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }
    
    public String Merchant.getName() {
        return this.name;
    }
    
    public void Merchant.setName(String name) {
        this.name = name;
    }
    
    public String Merchant.getContactPhoneNumber() {
        return this.contactPhoneNumber;
    }
    
    public void Merchant.setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }
    
    public String Merchant.getContactAddress() {
        return this.contactAddress;
    }
    
    public void Merchant.setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
    
    public String Merchant.getDescription() {
        return this.description;
    }
    
    public void Merchant.setDescription(String description) {
        this.description = description;
    }
    
    public Double Merchant.getScore() {
        return this.score;
    }
    
    public void Merchant.setScore(Double score) {
        this.score = score;
    }
    
    public Integer Merchant.getScoreUserCount() {
        return this.scoreUserCount;
    }
    
    public void Merchant.setScoreUserCount(Integer scoreUserCount) {
        this.scoreUserCount = scoreUserCount;
    }
    
    public MerchantStatus Merchant.getStatus() {
        return this.status;
    }
    
    public void Merchant.setStatus(MerchantStatus status) {
        this.status = status;
    }
    
    public Integer Merchant.getOrder() {
        return this.order;
    }
    
    public void Merchant.setOrder(Integer order) {
        this.order = order;
    }
    
}
