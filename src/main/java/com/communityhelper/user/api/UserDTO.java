package com.communityhelper.user.api;

import com.communityhelper.api.APIRequest;
import com.communityhelper.user.RealNameAuth;

public class UserDTO extends APIRequest{
    private Integer id;
    private String phonenum;
    private String password;
    private String realName;
    private String address;
    private String token;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public void setPhonenum(String username) {
        this.phonenum = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public RealNameAuth toRealNameAuth(Integer userId) {
        RealNameAuth realNameAuth = new RealNameAuth();
        realNameAuth.setUserId(userId);
        realNameAuth.setRealName(this.getRealName());
        realNameAuth.setContractAddress(this.getAddress());
        return realNameAuth;
    }
}
