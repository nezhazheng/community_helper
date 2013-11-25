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
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.feedback.Feedback;

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
    private Double score;
    /** 评分人数 */
    @Column(name = "score_user_count")
    private Integer scoreUserCount;
    
    
    public static List<Merchant> findRootMerchants() {
        TypedQuery<Merchant> query = entityManager().createQuery(
                "select c from Merchant c where c.categoryId = :categoryId ", Merchant.class);
        query.setParameter("categoryId", Category.DEFAULT_ROOT_ID);
        return query.getResultList();
    }


    public static Page findMerchantsByCategoryId(Integer categoryId,
            Integer start, Integer size) {
        TypedQuery<Merchant> query = entityManager().createQuery(
                "select c from Merchant c where c.categoryId = :categoryId ", Merchant.class);
        query.setParameter("categoryId", categoryId)
        .setFirstResult(start)
        .setMaxResults(size);
        List<Merchant> merchants = query.getResultList();
        Page<Merchant> page = new Page<Merchant>(start, size);
        page.setList(merchants);
        page.setTotalResult(countMerchantsByCategoryId(categoryId));
        return page;
    }
    
    public static Integer countMerchantsByCategoryId(Integer parentId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Merchant o where o.categoryId = :categoryId ", Long.class)
                .setParameter("categoryId", parentId).getSingleResult().toString());
    }

    @Transactional
    public void updateScore(Feedback feedback) {
        // update score avg
        double total = feedback.getScore().doubleValue()+ this.getScore();
        double score = total / (this.getScoreUserCount() + 1);
        this.setScore(score);
        this.setScoreUserCount(this.getScoreUserCount() + 1);
        this.merge();
    }
}
