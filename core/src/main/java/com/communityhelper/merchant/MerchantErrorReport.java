package com.communityhelper.merchant;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "merchant_error_report")
public class MerchantErrorReport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** foreign keys */
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "merchant_id")
    private Integer merchantId;
    
    @Column(name = "error_category")
    @Enumerated(EnumType.STRING)
    private MerchantErrorCategory errorCategory;
    
    public enum MerchantErrorCategory{
        ADDRESS_ERROR,PHONE_ERROR
    }
}
