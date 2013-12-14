package com.communityhelper.user.api.representation;

import java.util.List;

import com.communityhelper.merchant.Merchant;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.User;

/**
 * 我的信息 dto
 * @author nezhazheng
 *
 */
public class MyDTO {
    private User user;
    private List<Merchant> merchants;
    private RealNameAuth realNameAuth;
    public RealNameAuth getRealNameAuth() {
        return realNameAuth;
    }
    public void setRealNameAuth(RealNameAuth realNameAuth) {
        this.realNameAuth = realNameAuth;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Merchant> getMerchants() {
        return merchants;
    }
    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }
}
