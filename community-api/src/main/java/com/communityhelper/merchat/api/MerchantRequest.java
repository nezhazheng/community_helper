package com.communityhelper.merchat.api;

import java.util.Date;

import com.communityhelper.api.APIRequest;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantStatus;
import com.communityhelper.merchant.MerchantErrorReport.MerchantErrorCategory;

public class MerchantRequest extends APIRequest {
    private String name;
    private String contactPhoneNumber;
    private String contactAddress;
    private String desc;
    /** 商户服务状态 - 是否可用 */
    private Boolean serviceEnable;
    private Integer standardCategoryId;
    private MerchantErrorCategory errorCategory;
    private Integer userId;
    private Integer start = 0;
    private Integer size = 4;
    public Boolean getServiceEnable() {
        return serviceEnable;
    }
    public void setServiceEnable(Boolean serviceEnable) {
        this.serviceEnable = serviceEnable;
    }
    public Integer getStandardCategoryId() {
        return standardCategoryId;
    }
    public void setStandardCategoryId(Integer standardCategoryId) {
        this.standardCategoryId = standardCategoryId;
    }
    public Integer getStart() {
        return start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public MerchantErrorCategory getErrorCategory() {
        return errorCategory;
    }
    public void setErrorCategory(MerchantErrorCategory errorCategory) {
        this.errorCategory = errorCategory;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }
    public String getContactAddress() {
        return contactAddress;
    }
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Merchant createMerchant() {
        Merchant merchant = new Merchant();
        merchant.setName(this.getName());
        merchant.setStandardCategoryId(this.getStandardCategoryId());
        merchant.setContactAddress(this.getContactAddress());
        merchant.setDescription(this.getDesc());
        merchant.setContactPhoneNumber(this.getContactPhoneNumber());
        merchant.setCommunityId(this.getCommunityId());
        merchant.setAuthStatus(MerchantStatus.NOT_VALID);
        merchant.setCreateDate(new Date());
        merchant.setDefault();
        return merchant;
    }
}
