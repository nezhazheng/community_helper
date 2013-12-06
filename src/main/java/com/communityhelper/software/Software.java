package com.communityhelper.software;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "software")
public class Software {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String platform;
    @Column
    private String version;
    @Column
    private String channel;
    @Column(name = "enable_upgrade")
    private boolean enableUpgrade;
    @Column(name = "update_url")
    private String updateURL;
    @Column(name = "upgrade_desc")
    private String upgradeDesc;
    
    public static Software findCurrentUpdateSoftware(String channel,
            String platform) {
        Software software = null;
        try {
            software = entityManager().createQuery("select o from Software o where o.platform = :platform" +
            		" and o.channel = :channel and o.enableUpgrade = true", Software.class)
            		.setParameter("platform", platform)
            		.setParameter("channel", channel)
            		.getSingleResult();
        } catch (EmptyResultDataAccessException empty) {
            return null;
        }  
        return software;
    }
    
    public static Software findSoftwareByPlatformAndVersionAndChannel(
            String platform, String version, String channel){
        Software software = null;
        try {
            software = entityManager().createQuery("select o from Software o where o.platform = :platform" +
                    " and o.channel = :channel and o.version = :version", Software.class)
                    .setParameter("platform", platform)
                    .setParameter("channel", channel)
                    .setParameter("version", version)
                    .getSingleResult();
        } catch (EmptyResultDataAccessException empty) {
            return null;
        }  
        return software;
    }
    
    public boolean upgrade(String originVersion){
        String[] target = this.getVersion().split("\\.");
        String[] origin = originVersion.split("\\.");
        for(int i = 0; i < origin.length; i++){
            int originVersionNum = Integer.parseInt(origin[i]);
            int targetVersionNum = 0;
            if(i < target.length){
                targetVersionNum = Integer.parseInt(target[i]);
            }
            if(targetVersionNum > originVersionNum){
                return true;
            }
        }
        if(target.length > origin.length){
            return true;
        }
        return false;
    }
}
