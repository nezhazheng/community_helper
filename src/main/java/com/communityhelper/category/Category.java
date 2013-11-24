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
public class Category {
    public static final Integer DEFAULT_ROOT_ID = 0;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "name")
    private String name;
    
    public static List<Category> findRootCategories() {
        TypedQuery<Category> query = entityManager().createQuery(
                "select c from Category c where c.parentId = :parentId ", Category.class);
        query.setParameter("parentId", DEFAULT_ROOT_ID);
        return query.getResultList();
    }

    public static Page findChildCategories(Integer categoryId, Integer start, Integer size) {
        TypedQuery<Category> query = entityManager().createQuery(
                "select c from Category c where c.parentId = :parentId ", Category.class);
        query.setParameter("parentId", categoryId)
        .setFirstResult(start)
        .setMaxResults(size);
        List<Category> categories = query.getResultList();
        Page<Category> page = new Page<Category>(start, size);
        page.setList(categories);
        page.setTotalResult(countCategorysByParentId(categoryId));
        return page;
    }
    
    public static Integer countCategorysByParentId(Integer parentId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Category o where o.parentId = :parentId ", Long.class)
                .setParameter("parentId", parentId).getSingleResult().toString());
    }
}
