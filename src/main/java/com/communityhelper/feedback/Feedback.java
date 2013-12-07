package com.communityhelper.feedback;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

import com.communityhelper.api.Page;
import com.communityhelper.feedback.api.FeedbackDTO;
@RooJson
@RooJavaBean
@RooEntity(versionField = "", table = "feedback", identifierField = "id")
public class Feedback {
    public Feedback(FeedbackDTO feedbackDTO, Integer merchantId) {
        this.setCreateDate(new Date());
        this.setMessage(feedbackDTO.getMessage());
        this.setScore(feedbackDTO.getScore());
        this.setId(new FeedbackPK(feedbackDTO.getUserId(), merchantId));
    }
    
    @EmbeddedId
    private FeedbackPK id;
    @Column(name = "message", length = 500)
    private String message;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "score")
    private Integer score;
    @Transient
    private String phonenum;
    public Feedback(FeedbackPK id, String message, Date createDate,
            Integer score, String phonenum) {
        super();
        this.id = id;
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
                "select new com.communityhelper.feedback.Feedback(c.id,c.message,c.createDate,c.score,u.phonenum) " +
                "from Feedback c, User u where c.id.userId = u.id and c.id.merchantId = :merchantId ", Feedback.class);
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
        return Integer.parseInt(entityManager().createQuery("SELECT COUNT(o) FROM Feedback o where o.id.merchantId = :merchantId ", Long.class)
                .setParameter("merchantId", merchantId).getSingleResult().toString());
    }
}
