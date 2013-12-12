package com.communityhelper.feedback;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;
import com.communityhelper.user.User;
@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "feedback")
public class Feedback {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** foreign keys */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "merchant_id")
    private Integer merchantId;
    
    @Column(name = "message", length = 500)
    private String message;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "score")
    private Integer score;
    @Transient
    private String phonenum;
    /** 查询使用 */
    public Feedback(Integer merchantId, String message, Date createDate,
            Integer score, String phonenum) {
        super();
        this.merchantId = merchantId;
        this.message = message;
        this.createDate = createDate;
        this.score = score;
        this.phonenum = phonenum;
    }

    @Transactional
    public boolean persist(){
        if (notPresent()) {
            entityManager.persist(this);
            return true;
        }
        return false;
    }
    
    public boolean notPresent() {
        Feedback tryFeedback = findFeedback(getId());
        return tryFeedback == null;
    }

    public static Page<Feedback> findFeedbacksByMerchant(Integer merchantId,
            Integer start, Integer size) {
        TypedQuery<Feedback> query = entityManager().createQuery(
                "select new com.communityhelper.feedback.Feedback(c.merchantId,c.message,c.createDate,c.score,u.phonenum) " +
                "from Feedback c inner join c.user u where c.user.id = u.id and c.merchantId = :merchantId ", Feedback.class);
        query.setParameter("merchantId", merchantId)
        .setFirstResult(start)
        .setMaxResults(size);
        List<Feedback> feedbacks = query.getResultList();
        Page<Feedback> page = new Page<Feedback>(start, size);
        page.setList(feedbacks);
        page.setTotalResult(countFeedbacksByMerchantId(merchantId));
        return page;
    }

    private static Integer countFeedbacksByMerchantId(Integer merchantId) {
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Feedback o where o.merchantId = :merchantId ", Long.class)
                .setParameter("merchantId", merchantId).getSingleResult().toString());
    }
}
