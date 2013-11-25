package com.communityhelper.feedback;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.transaction.annotation.Transactional;

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
    private Date createDate;
    @Column(name = "score")
    private Integer score;
    
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
}
