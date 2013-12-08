package com.communityhelper.merchat;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import com.communityhelper.user.User;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "my_merchant_collection")
public class MyMerchantCollection {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** foreign keys */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    
    public static List<Merchant> findMyMerchantCollectionByUserId(Integer userId) {
        TypedQuery<Merchant> query = entityManager().createQuery("select m from MyMerchantCollection c inner join c.merchant m " +
        		" where c.user.id = :userId and c.merchant.id = m.id ", Merchant.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    public static MyMerchantCollection find(Integer userId, Integer merchantId) {
        TypedQuery<MyMerchantCollection> query = entityManager().createQuery("from MyMerchantCollection m " +
                " where m.user.id = :userId and m.merchant.id = :merchantId", MyMerchantCollection.class);
        query.setParameter("userId", userId)
        .setParameter("merchantId", merchantId);
        List<MyMerchantCollection> tryCollections = query.getResultList();
        if(null != tryCollections && tryCollections.size() > 0){
            return tryCollections.get(0);
        }
        return null;
    }
    
    public static boolean setting(Integer userId, Integer merchantId) {
        MyMerchantCollection collection = find(userId, merchantId);
        if(collection != null){
            collection.remove();
            return false;
        }
        MyMerchantCollection my = new MyMerchantCollection();
        my.setUser(new User(userId));
        my.setMerchant(new Merchant(merchantId));
        my.persist();
        return true;
    }
}
