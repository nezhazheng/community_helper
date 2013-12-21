package com.communityhelper.merchat.api.representation;

import com.communityhelper.feedback.Feedback;
import com.communityhelper.page.Page;

public class MerchantsDetailDTO {
    private UserMerchantDTO merchant;
    private Page<Feedback> feedbackList;
    public UserMerchantDTO getMerchant() {
        return merchant;
    }
    public void setMerchant(UserMerchantDTO merchant) {
        this.merchant = merchant;
    }
    public Page<Feedback> getFeedbackList() {
        return feedbackList;
    }
    public void setFeedbackList(Page<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
