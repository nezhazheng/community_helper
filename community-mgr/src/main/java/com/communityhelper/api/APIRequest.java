package com.communityhelper.api;

public class APIRequest {
    private String version;
    private String channel;
    private String platform;
    private String phonenum;
    private String imei;
    private Integer communityId;
    public Integer getCommunityId() {
        return communityId;
    }
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }
    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
    
}
