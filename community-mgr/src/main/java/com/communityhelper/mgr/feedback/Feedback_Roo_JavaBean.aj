// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.mgr.feedback;

import com.communityhelper.merchant.Merchant;
import com.communityhelper.user.User;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

privileged aspect Feedback_Roo_JavaBean {
    
    public Integer Feedback.getId() {
        return this.id;
    }
    
    public void Feedback.setId(Integer id) {
        this.id = id;
    }
    
    public User Feedback.getUser() {
        return this.user;
    }
    
    public void Feedback.setUser(User user) {
        this.user = user;
    }
    
    public Merchant Feedback.getMerchant() {
        return this.merchant;
    }
    
    public void Feedback.setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
    
    public String Feedback.getMessage() {
        return this.message;
    }
    
    public void Feedback.setMessage(String message) {
        this.message = message;
    }
    
    public Date Feedback.getCreateDate() {
        return this.createDate;
    }
    
    public void Feedback.setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Integer Feedback.getScore() {
        return this.score;
    }
    
    public void Feedback.setScore(Integer score) {
        this.score = score;
    }
    
    public String Feedback.getPhonenum() {
        return this.phonenum;
    }
    
    public void Feedback.setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
    
    public String Feedback.getMerchantName() {
        return this.merchantName;
    }
    
    public void Feedback.setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    
}
