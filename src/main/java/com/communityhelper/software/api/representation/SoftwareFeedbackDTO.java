package com.communityhelper.software.api.representation;

import com.communityhelper.api.APIRequest;

public class SoftwareFeedbackDTO extends APIRequest{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
