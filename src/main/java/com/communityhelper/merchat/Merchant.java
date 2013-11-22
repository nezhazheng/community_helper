package com.communityhelper.merchat;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import com.communityhelper.category.Category;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "merchant")
public class Merchant {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "name")
    private String name;
    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;
    @Column(name = "contract_address")
    private String contactAddress;
    /** 用户评分 */
    @Column(name = "score")
    private Integer score;
    
    
    public static List<Merchant> findRootMerchants() {
        TypedQuery<Merchant> query = entityManager().createQuery(
                "select c from Merchant c where c.categoryId = :categoryId ", Merchant.class);
        query.setParameter("categoryId", Category.DEFAULT_ROOT_ID);
        return query.getResultList();
    }
}
