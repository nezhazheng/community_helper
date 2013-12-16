package com.communityhelper.merchat.api.representation;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantStatus;
import com.communityhelper.user.UserServiceStatus;

public class UserMerchantDTO {
    private Integer id;
    
    /** foreign keys */
    private Integer categoryId;
    private Integer userId;
    private Integer communityId;
    private Integer standardCategoryId;
    
    private String name;
    private String contactPhoneNumber;
    private String contactAddress;
    private String description;
    
    /** 用户评分 */
    private Double score;
    /** 评分人数 */
    private Integer scoreUserCount;
    /** 商户当前审核状态 */
    private MerchantStatus authStatus;
    private Integer order;
    private Date createDate;
    /** 商户服务状态 - 是否可用 */
    private Boolean serviceEnable;
    
    /** 当前用户是否收藏了 */
    private Boolean collected;
    private UserServiceStatus userServiceStatus;
    
    public UserMerchantDTO() {}
    public UserMerchantDTO(Merchant m) {
        try {
            PropertyUtils.copyProperties(this, m);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getCommunityId() {
        return communityId;
    }
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }
    public Integer getStandardCategoryId() {
        return standardCategoryId;
    }
    public void setStandardCategoryId(Integer standardCategoryId) {
        this.standardCategoryId = standardCategoryId;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    public Integer getScoreUserCount() {
        return scoreUserCount;
    }
    public void setScoreUserCount(Integer scoreUserCount) {
        this.scoreUserCount = scoreUserCount;
    }
    public MerchantStatus getAuthStatus() {
        return authStatus;
    }
    public void setAuthStatus(MerchantStatus authStatus) {
        this.authStatus = authStatus;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Boolean getServiceEnable() {
        return serviceEnable;
    }
    public void setServiceEnable(Boolean serviceEnable) {
        this.serviceEnable = serviceEnable;
    }
    public Boolean getCollected() {
        return collected;
    }
    public void setCollected(Boolean collected) {
        this.collected = collected;
    }
    public UserServiceStatus getUserServiceStatus() {
        return userServiceStatus;
    }
    public void setUserServiceStatus(UserServiceStatus userServiceStatus) {
        this.userServiceStatus = userServiceStatus;
    }
}
