package com.communityhelper.feedback;

import javax.persistence.Column;

import org.springframework.roo.addon.entity.RooIdentifier;
import org.springframework.roo.addon.tostring.RooToString;

@RooIdentifier
@RooToString
public class FeedbackPK {
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "merchant_id")
    private Integer merchantId;
}
