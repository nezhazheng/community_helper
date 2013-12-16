package com.communityhelper.user.api.representation;

import java.util.Date;

import com.communityhelper.api.APIRequest;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.RealNameAuthStatus;
import com.communityhelper.user.UserServiceStatus;

public class UserDTO extends APIRequest{
    private Integer id;
    private String phonenum;
    private String password;
    private String oldPassword;
    private String realName;
    private String address;
    private String token;
    private RealNameAuthStatus realNameAuthStatus;
    private UserServiceStatus userServiceStatus;
    
    public RealNameAuthStatus getRealNameAuthStatus() {
        return realNameAuthStatus;
    }
    public void setRealNameAuthStatus(RealNameAuthStatus realNameAuthStatus) {
        this.realNameAuthStatus = realNameAuthStatus;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
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
        realNameAuth.setCreateDate(new Date());
        return realNameAuth;
    }
    public UserServiceStatus getUserServiceStatus() {
        return userServiceStatus;
    }
    public void setUserServiceStatus(UserServiceStatus userServiceStatus) {
        this.userServiceStatus = userServiceStatus;
    }
}
