package com.communityhelper.software.api;

import com.communityhelper.api.APIRequest;

public class SoftwareFeedbackRequest extends APIRequest{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
