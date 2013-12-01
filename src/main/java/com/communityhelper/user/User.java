package com.communityhelper.user;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooEntity(versionField = "", table = "user", finders = "findUsersByPhonenumEquals")
@RooJson
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "phonenum", unique=true)
    private String phonenum;
    @Column(name = "password")
    private String password;
    @Column(name = "real_name")
    private String realName;
    @Column(name = "address")
    private String address; 
    @Column
    private String channel;
    @Column
    private String imei;
    @Column(name = "real_name_auth")
    private Boolean realNameAuth;
    
    @Transactional
    public boolean persist(){
        if (notPresent()) {
            entityManager.persist(this);
            return true;
        }
        return false;
    }
    
    public boolean notPresent() {
        User tryUser = findUserByMobile(getPhonenum());
        return tryUser == null;
    }
    
    public static User findUserByMobile(String mobile) {
        TypedQuery<User> userQuery = User.findUsersByPhonenumEquals(mobile);
        try {
            return userQuery.getSingleResult();
        }
        catch (EmptyResultDataAccessException empty) {
            return null;
        }
    }
}
