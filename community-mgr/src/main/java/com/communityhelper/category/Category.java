package com.communityhelper.category;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import com.communityhelper.api.Page;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "category")
public class Category implements Orderable, Comparable<Orderable> {
    public static final Integer DEFAULT_ROOT_ID = 0;
    
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

    public static Page findChildCategories(Integer categoryId, Integer start, Integer size, Integer communityId) {
        TypedQuery<Category> query = entityManager().createQuery(
                "select c from Category c where c.categoryId = :categoryId and c.communityId = :communityId order by c.order asc", Category.class);
        query.setParameter("categoryId", categoryId)
        .setParameter("communityId", communityId)
        .setFirstResult(start)
        .setMaxResults(size);
        List<Category> categories = query.getResultList();
        Page<Category> page = new Page<Category>(start, size);
        page.setList(categories);
        page.setTotalResult(countCategorysByParentId(categoryId, communityId));
        return page;
    }
    
    public static Integer countCategorysByParentId(Integer categoryId, Integer communityId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Category o where o.categoryId = :categoryId " +
        		" and o.communityId = :communityId", Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("communityId", communityId)
                .getSingleResult().toString());
    }

    @Override
    public int compareTo(Orderable o) {
        return this.getOrder().compareTo(o.getOrder());
    }

    public static List<Category> findAllCategories(Integer communityId) {
        TypedQuery<Category> query = entityManager().createQuery(
                "select c from Category c where c.communityId = :communityId", Category.class)
                .setParameter("communityId", communityId);
        
        List<Category> categories = query.getResultList();
        return categories;
    }
}
