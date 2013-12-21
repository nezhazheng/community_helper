package com.communityhelper.mgr.feedback;

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

import com.communityhelper.feedback.api.FeedbackDTO;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.page.Page;
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
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    
    @Column(name = "message", length = 500)
    private String message;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "score")
    private Integer score;
    @Transient
    private String phonenum;
    @Transient
    private String merchantName;
    /** 查询使用 */
    public Feedback(Integer id, String merchantName, String message, Date createDate,
            Integer score, String phonenum) {
        super();
        this.id = id;
        this.merchantName = merchantName;
        this.message = message;
        this.createDate = createDate;
        this.score = score;
        this.phonenum = phonenum;
    }
    
    /** 创建使用 */
    public Feedback(FeedbackDTO feedbackDTO, Integer merchantId) {
        this.setCreateDate(new Date());
        this.setMessage(feedbackDTO.getMessage());
        this.setScore(feedbackDTO.getScore());
        this.setUser(new User(feedbackDTO.getUserId()));
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
                "select new com.communityhelper.mgr.feedback.Feedback(c.merchantId,c.message,c.createDate,c.score,u.phonenum) " +
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
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM com.communityhelper.mgr.feedback.Feedback o where o.merchantId = :merchantId ", Long.class)
                .setParameter("merchantId", merchantId).getSingleResult().toString());
    }
    
    public static Page<Feedback> findAllFeedbacks(Integer start, Integer limit) {
        TypedQuery<Feedback> query = entityManager().createQuery(
                "select new com.communityhelper.mgr.feedback.Feedback(c.id, m.name,c.message,c.createDate,c.score,u.phonenum) " +
                "from Feedback c inner join c.user u inner join c.merchant m where m.id = c.merchant.id and c.user.id = u.id", Feedback.class);
        List<Feedback> feedbacks = query.setFirstResult(start)
        .setMaxResults(limit).getResultList();
        
        Page<Feedback> page = new Page<Feedback>();
        page.setList(feedbacks);
        page.setTotalResult(Integer.parseInt((Feedback.countFeedbacks() + "")));
        return page;
    }
}
