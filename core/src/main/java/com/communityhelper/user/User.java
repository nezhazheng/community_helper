package com.communityhelper.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;

@RooJavaBean
@RooEntity(versionField = "", table = "user", finders = "findUsersByPhonenumEquals")
@RooJson
public class User {
    @Transient
    public static final StandardPasswordEncoder PASSWORD_ENCODER = new StandardPasswordEncoder("community");
    
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
    
    @Enumerated(EnumType.STRING)
    @Column(name = "real_name_auth")
    private RealNameAuthStatus realNameAuthStatus;

    @Column(name = "create_date")
    private Date createDate;
    
    public User(Integer userId) {
        this.id = userId;
    }
    
    public User(){}

    @Transactional
    public boolean persist(){
        if (notPresent()) {
            entityManager.persist(this);
            return true;
        }
        return false;
    }
    
    public boolean notPresent() {
        User tryUser = findUserByPhonenum(getPhonenum());
        return tryUser == null;
    }
    
    public static User findUserByPhonenum(String mobile) {
        TypedQuery<User> userQuery = User.findUsersByPhonenumEquals(mobile);
        try {
            return userQuery.getSingleResult();
        }
        catch (EmptyResultDataAccessException empty) {
            return null;
        }
    }

    public static Page<User> findUsersPage(Integer start, Integer limit) {
        List<User> users = entityManager().createQuery("from User", User.class)
        .setFirstResult(start)
        .setMaxResults(limit)
        .getResultList();
        Page<User> page = new Page<User>();
        page.setList(users);
        page.setTotalResult(Integer.parseInt((User.countUsers() + "")));
        return page;
    }
}
