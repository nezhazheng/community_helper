package com.communityhelper.category;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "category")
public class Category implements Orderable, Comparable<Orderable> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** foreign keys */
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "community_id")
    private Integer communityId;
    @Column(name = "icon_id")
    private Integer iconId;
    
    @Column(name = "name")
    private String name;
    @Column(name = "corder")
    private Integer order;

    @Override
    public int compareTo(Orderable o) {
        return this.getOrder().compareTo(o.getOrder());
    }
    
    public static List<Category> findAllChildCategories(Integer categoryId, Integer communityId) {
        String hql = "select c from Category c where c.categoryId = :categoryId and c.communityId = :communityId " +
                "order by c.order asc";
        TypedQuery<Category> query = entityManager().createQuery(hql , Category.class);
        query.setParameter("categoryId", categoryId)
        .setParameter("communityId", communityId);
        List<Category> categories = query.getResultList();
        return categories;
    }
    
    public static Integer countCategorysByParentCategoryId(Integer categoryId, Integer communityId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Category o where o.categoryId = :categoryId " +
        		" and o.communityId = :communityId", Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("communityId", communityId)
                .getSingleResult().toString());
    }

    public static List<Category> findAllCategories(Integer communityId) {
        TypedQuery<Category> query = entityManager().createQuery(
                "select c from Category c where c.communityId = :communityId", Category.class)
                .setParameter("communityId", communityId);
        
        List<Category> categories = query.getResultList();
        return categories;
    }

    public static Category findCategoryByOrderAndCategoryId(Integer order,
            Integer categoryId) {
        try {
            Category category = entityManager().createQuery("from Category o where o.categoryId = :categoryId and o.order = :order"
                    , Category.class).setParameter("categoryId", categoryId)
                    .setParameter("order", order).getSingleResult();
            return category;
        }
        catch (EmptyResultDataAccessException empty) {
            return null;
        }
    }
}
