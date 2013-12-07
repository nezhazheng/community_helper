package com.communityhelper.merchat.api.representation;

import com.communityhelper.api.APIRequest;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.MerchantErrorReport.MerchantErrorCategory;

public class MerchantRequest extends APIRequest {
    private String name;
    private String contactPhoneNumber;
    private String contactAddress;
    private String desc;
    private MerchantErrorCategory errorCategory;
    private Integer userId;
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
    public Merchant toMerchant() {
        Merchant merchant = new Merchant();
        merchant.setName(this.getName());
        merchant.setContactAddress(this.getContactAddress());
        merchant.setDescription(this.getDesc());
        merchant.setContactPhoneNumber(this.getContactPhoneNumber());
        merchant.setDefault();
        return merchant;
    }
}
