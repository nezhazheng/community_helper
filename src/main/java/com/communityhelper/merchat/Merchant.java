package com.communityhelper.merchat;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;
import com.communityhelper.category.MerchantStatus;
import com.communityhelper.category.Orderable;
import com.communityhelper.feedback.Feedback;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "merchant")
public class Merchant implements Orderable, Comparable<Orderable> {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** foreign keys */
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "name")
    private String name;
    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;
    @Column(name = "contract_address")
    private String contactAddress;
    @Column(name = "description")
    private String description;
    
    /** 用户评分 */
    @Column(name = "score")
    private Double score;
    /** 评分人数 */
    @Column(name = "score_user_count")
    private Integer scoreUserCount;
    /** 商户当前状态 */
    @Enumerated(EnumType.STRING)
    private MerchantStatus status;
    @Column(name = "morder")
    private Integer order;
    
    public static Page findValidMerchantsByCategoryId(Integer categoryId,
            Integer start, Integer size) {
        TypedQuery<Merchant> query = entityManager().createQuery(
                "from Merchant c where c.categoryId = :categoryId and c.status = :status", Merchant.class);
        query.setParameter("categoryId", categoryId)
        .setParameter("status", MerchantStatus.VALID)
        .setFirstResult(start)
        .setMaxResults(size);
        List<Merchant> merchants = query.getResultList();
        Page<Merchant> page = new Page<Merchant>(start, size);
        page.setList(merchants);
        page.setTotalResult(countValidMerchantsByCategoryId(categoryId));
        return page;
    }
    
    public static Integer countMerchantsByCategoryId(Integer parentId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Merchant o where o.categoryId = :categoryId ", Long.class)
                .setParameter("categoryId", parentId).getSingleResult().toString());
    }
    
    public static Integer countValidMerchantsByCategoryId(Integer parentId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Merchant o where o.categoryId = :categoryId  and o.status = :status", Long.class)
                .setParameter("categoryId", parentId)
                .setParameter("status", MerchantStatus.VALID)
                .getSingleResult().toString());
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

    public void setDefault() {
        this.setScore(0.0);
        this.setScoreUserCount(0);
        this.setOrder(0);
    }
    
    @Override
    public int compareTo(Orderable o) {
        return this.getOrder().compareTo(o.getOrder());
    }
}
