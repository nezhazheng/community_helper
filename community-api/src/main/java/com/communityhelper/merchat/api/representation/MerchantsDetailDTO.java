package com.communityhelper.merchat.api.representation;

import com.communityhelper.api.Page;
import com.communityhelper.feedback.Feedback;
import com.communityhelper.merchant.Merchant;

public class MerchantsDetailDTO {
    private Merchant merchant;
    private Page<Feedback> feedbackList;
    public Merchant getMerchant() {
        return merchant;
    }
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
    public Page<Feedback> getFeedbackList() {
        return feedbackList;
    }
    public void setFeedbackList(Page<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
    
}
