package com.communityhelper.user.api.representation;

import java.util.List;

import com.communityhelper.merchat.Merchant;
import com.communityhelper.user.User;

/**
 * 我的信息 dto
 * @author nezhazheng
 *
 */
public class MyDTO {
    private User user;
    private List<Merchant> merchants;
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
