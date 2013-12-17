// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.category;

import com.communityhelper.category.StandardCategory;
import java.lang.Integer;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect StandardCategory_Roo_Entity {
    
    declare @type: StandardCategory: @Entity;
    
    declare @type: StandardCategory: @Table(name = "standard_category");
    
    @PersistenceContext
    transient EntityManager StandardCategory.entityManager;
    
    @Transactional
    public void StandardCategory.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void StandardCategory.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            StandardCategory attached = StandardCategory.findStandardCategory(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void StandardCategory.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void StandardCategory.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public StandardCategory StandardCategory.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        StandardCategory merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager StandardCategory.entityManager() {
        EntityManager em = new StandardCategory().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long StandardCategory.countStandardCategorys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM StandardCategory o", Long.class).getSingleResult();
    }
    
    public static List<StandardCategory> StandardCategory.findAllStandardCategorys() {
        return entityManager().createQuery("SELECT o FROM StandardCategory o", StandardCategory.class).getResultList();
    }
    
    public static StandardCategory StandardCategory.findStandardCategory(Integer id) {
        if (id == null) return null;
        return entityManager().find(StandardCategory.class, id);
    }
    
    public static List<StandardCategory> StandardCategory.findStandardCategoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM StandardCategory o", StandardCategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}