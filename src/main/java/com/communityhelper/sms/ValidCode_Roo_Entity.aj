// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.sms;

import com.communityhelper.sms.ValidCode;
import java.lang.Integer;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ValidCode_Roo_Entity {
    
    declare @type: ValidCode: @Entity;
    
    declare @type: ValidCode: @Table(name = "validcode");
    
    @PersistenceContext
    transient EntityManager ValidCode.entityManager;
    
    @Transactional
    public void ValidCode.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ValidCode.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ValidCode attached = ValidCode.findValidCode(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ValidCode.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ValidCode.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ValidCode ValidCode.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ValidCode merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ValidCode.entityManager() {
        EntityManager em = new ValidCode().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ValidCode.countValidCodes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ValidCode o", Long.class).getSingleResult();
    }
    
    public static List<ValidCode> ValidCode.findAllValidCodes() {
        return entityManager().createQuery("SELECT o FROM ValidCode o", ValidCode.class).getResultList();
    }
    
    public static ValidCode ValidCode.findValidCode(Integer id) {
        if (id == null) return null;
        return entityManager().find(ValidCode.class, id);
    }
    
    public static List<ValidCode> ValidCode.findValidCodeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ValidCode o", ValidCode.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
