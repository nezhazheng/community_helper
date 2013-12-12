package com.communityhelper.sms;

import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "validcode")
public class ValidCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name = "phonenum")
    private String phonenum;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "securitycode")
    private String securitycode;
    
    @Column(name = "createtime")
    private String createtime;
    
    public static List<ValidCode> findValidCodeTodayByPhonenum(String phonenum, String type){
        String today = new DateTime().toString("yyyy-MM-dd");
        List<ValidCode> validCodes = entityManager().createQuery("from ValidCode o where o.phonenum = :phonenum" +
        		" and o.type = :type and o.createtime = :createtime order by o.id desc", ValidCode.class)
            .setParameter("phonenum", phonenum)
            .setParameter("type", type)
            .setParameter("createtime", today)
            .getResultList();
        return validCodes;
    }

    public static String generate(String phonenum, String type) {
        String today = new DateTime().toString("yyyy-MM-dd");
        String validCodeStr = generateRandomNum(5);
        
        ValidCode validCode = new ValidCode();
        validCode.setPhonenum(phonenum);
        validCode.setType(type);
        validCode.setCreatetime(today);
        validCode.setSecuritycode(validCodeStr);
        validCode.persist();
        return validCodeStr;
    }
    
    public static String generateRandomNum(int num) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < num; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(10);
            sBuffer.append(nextInt);
        }
        return sBuffer.toString();
    }
}
