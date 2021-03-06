// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.merchant;

import com.communityhelper.merchant.MyMerchantCollection;
import java.lang.Integer;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect MyMerchantCollection_Roo_Entity {
    
    declare @type: MyMerchantCollection: @Entity;
    
    declare @type: MyMerchantCollection: @Table(name = "my_merchant_collection");
    
    @PersistenceContext
    transient EntityManager MyMerchantCollection.entityManager;
    
    @Transactional
    public void MyMerchantCollection.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void MyMerchantCollection.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            MyMerchantCollection attached = MyMerchantCollection.findMyMerchantCollection(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void MyMerchantCollection.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void MyMerchantCollection.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public MyMerchantCollection MyMerchantCollection.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        MyMerchantCollection merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager MyMerchantCollection.entityManager() {
        EntityManager em = new MyMerchantCollection().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long MyMerchantCollection.countMyMerchantCollections() {
        return entityManager().createQuery("SELECT COUNT(o) FROM MyMerchantCollection o", Long.class).getSingleResult();
    }
    
    public static List<MyMerchantCollection> MyMerchantCollection.findAllMyMerchantCollections() {
        return entityManager().createQuery("SELECT o FROM MyMerchantCollection o", MyMerchantCollection.class).getResultList();
    }
    
    public static MyMerchantCollection MyMerchantCollection.findMyMerchantCollection(Integer id) {
        if (id == null) return null;
        return entityManager().find(MyMerchantCollection.class, id);
    }
    
    public static List<MyMerchantCollection> MyMerchantCollection.findMyMerchantCollectionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM MyMerchantCollection o", MyMerchantCollection.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
