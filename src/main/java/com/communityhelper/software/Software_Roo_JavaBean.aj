// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.software;

import java.lang.Integer;
import java.lang.String;

privileged aspect Software_Roo_JavaBean {
    
    public Integer Software.getId() {
        return this.id;
    }
    
    public void Software.setId(Integer id) {
        this.id = id;
    }
    
    public String Software.getPlatform() {
        return this.platform;
    }
    
    public void Software.setPlatform(String platform) {
        this.platform = platform;
    }
    
    public String Software.getVersion() {
        return this.version;
    }
    
    public void Software.setVersion(String version) {
        this.version = version;
    }
    
    public String Software.getChannel() {
        return this.channel;
    }
    
    public void Software.setChannel(String channel) {
        this.channel = channel;
    }
    
    public boolean Software.isEnableUpgrade() {
        return this.enableUpgrade;
    }
    
    public void Software.setEnableUpgrade(boolean enableUpgrade) {
        this.enableUpgrade = enableUpgrade;
    }
    
    public String Software.getUpdateURL() {
        return this.updateURL;
    }
    
    public void Software.setUpdateURL(String updateURL) {
        this.updateURL = updateURL;
    }
    
    public String Software.getUpgradeDesc() {
        return this.upgradeDesc;
    }
    
    public void Software.setUpgradeDesc(String upgradeDesc) {
        this.upgradeDesc = upgradeDesc;
    }
    
}
