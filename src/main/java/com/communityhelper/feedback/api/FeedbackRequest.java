package com.communityhelper.feedback.api;

import com.communityhelper.api.APIRequest;

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
    
    
}
