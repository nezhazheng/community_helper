package com.communityhelper.user.api.representation;

import com.communityhelper.api.APIRequest;

public class UserDTO extends APIRequest{
    private String phonenum;
    private String password;
    private String realName;
    private String address;
    private String token;
    
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
}
