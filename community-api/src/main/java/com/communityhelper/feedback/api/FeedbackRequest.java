package com.communityhelper.feedback.api;

import java.util.Date;

import com.communityhelper.api.APIRequest;
import com.communityhelper.feedback.Feedback;
import com.communityhelper.user.User;

public class FeedbackRequest extends APIRequest{
    private Integer userId;
    private String message;
    private Integer score;
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public Feedback toFeedback() {
        Feedback feedback = new Feedback();
        feedback.setCreateDate(new Date());
        feedback.setMessage(this.getMessage());
        feedback.setScore(this.getScore());
        feedback.setUser(new User(this.getUserId()));
        return feedback;
    }
    
    
}
