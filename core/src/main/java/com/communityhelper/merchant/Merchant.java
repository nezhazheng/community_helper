package com.communityhelper.merchant;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.feedback.Feedback;

@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "merchant")
public class Merchant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** foreign keys */
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "community_id")
    private Integer communityId;
    @Column(name = "standard_category_id")
    private Integer standardCategoryId;
    
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
    /** 商户当前审核状态 */
    @Column(name = "auth_status")
    @Enumerated(EnumType.STRING)
    private MerchantStatus authStatus;
    @Column(name = "morder")
    private Integer order;
    @Column(name = "create_date")
    private Date createDate;
    /** 商户服务状态 - 是否可用 */
    @Column(name = "service_enable")
    private Boolean serviceEnable;
    
    public Merchant(Integer merchantId) {
        this.id = merchantId;
    }
    
    public Merchant() {}

    public static List<Merchant> findAllValidMerchantsByCategoryId(Integer categoryId, Integer communityId) {
        String hql = "from Merchant o where o.categoryId = :categoryId and " +
        		"o.communityId = :communityId and o.authStatus = :status order by o.order asc";
        TypedQuery<Merchant> query = entityManager().createQuery(hql, Merchant.class);
        query.setParameter("categoryId", categoryId)
        .setParameter("communityId", communityId)
        .setParameter("status", MerchantStatus.VALID);
        List<Merchant> merchants = query.getResultList();
        return merchants;
    }
    
    public static Integer countValidMerchantsByCategoryId(Integer parentId, Integer communityId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Merchant o where o.categoryId = :categoryId " +
        		" and o.communityId = :communityId and o.authStatus = :status and o.serviceEnable = :serviceEnable", Long.class)
                .setParameter("communityId", communityId)
                .setParameter("categoryId", parentId)
                .setParameter("status", MerchantStatus.VALID)
                .setParameter("serviceEnable", true)
                .getSingleResult().toString());
    }

    @Transactional
    public void updateScore(Feedback feedback) {
        // update score avg
        double total = feedback.getScore().doubleValue()+ (this.getScore() * this.getScoreUserCount());
        double score = total / (this.getScoreUserCount() + 1);
        this.setScore(score);
        this.setScoreUserCount(this.getScoreUserCount() + 1);
        this.merge();
    }

    public void setDefault() {
        this.setScore(0.0);
        this.setScoreUserCount(0);
        this.setCreateDate(new Date());
    }

    public static List<Merchant> findMerchantsByUserId(Integer userId) {
        TypedQuery<Merchant> query = entityManager().createQuery("from Merchant o where o.userId = :userId", Merchant.class);
        query.setParameter("userId", userId);
        List<Merchant> merchants = query.getResultList();
        return merchants;
    }
    
    public static List<Merchant> findMerchantsByAuthStatus(MerchantStatus notValid) {
        TypedQuery<Merchant> query = entityManager().createQuery("from Merchant o where o.authStatus = :status", Merchant.class);
        query.setParameter("status", notValid);
        return query.getResultList();
    }

    public static Merchant findMerchantByOrderAndCategoryId(Integer order,
            Integer categoryId) {
        try {
            Merchant merchant = entityManager().createQuery("from Merchant o where o.categoryId = :categoryId and o.order = :order"
                    , Merchant.class).setParameter("categoryId", categoryId)
                    .setParameter("order", order).getSingleResult();
            return merchant;
        }
        catch (EmptyResultDataAccessException empty) {
            return null;
        }
    }
    
    public static List<Merchant> findOrderableMerchants(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Merchant o order by o.createDate desc", Merchant.class)
                .setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
