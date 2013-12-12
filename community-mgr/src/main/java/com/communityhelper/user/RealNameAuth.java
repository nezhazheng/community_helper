package com.communityhelper.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooEntity(versionField = "", table = "realnameauth")
@RooJson
public class RealNameAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "real_name")
    private String realName;
    @Column(name = "contract_address")
    private String contractAddress;
    @Column(name = "create_date")
    private Date createDate;
}
