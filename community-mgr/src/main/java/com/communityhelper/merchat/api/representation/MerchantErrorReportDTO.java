package com.communityhelper.merchat.api.representation;

import com.communityhelper.merchant.MerchantErrorReport;
import com.communityhelper.merchant.MerchantErrorReport.MerchantErrorCategory;

public class MerchantErrorReportDTO {
    private Integer id;
    private Integer userId;
    private String merchantName;
    
    private MerchantErrorCategory errorCategory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public MerchantErrorCategory getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(MerchantErrorCategory errorCategory) {
        this.errorCategory = errorCategory;
    }

    public void setMerchantReport(MerchantErrorReport report) {
        this.setId(report.getId());
        this.setErrorCategory(report.getErrorCategory());
        this.setUserId(report.getUserId());
    }
}
