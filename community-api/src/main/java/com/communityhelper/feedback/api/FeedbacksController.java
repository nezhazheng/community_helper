package com.communityhelper.feedback.api;

import static com.communityhelper.api.APIResponse.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.APIResponse.Status;
import com.communityhelper.feedback.Feedback;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.user.RealNameAuthStatus;
import com.communityhelper.user.User;

@Controller
@RequestMapping("/merchant/{merchantId}/feedback")
public class FeedbacksController {
    
    @RequestMapping(method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse publishFeedback(@PathVariable Integer merchantId,
            @RequestBody FeedbackRequest feedbackDTO){
        User user = User.findUser(feedbackDTO.getUserId());
        if(!RealNameAuthStatus.ALREADY_AUTH.equals(user.getRealNameAuthStatus())) {
            return response().status(Status.DOESNOT_REALNAMEAUTH);
        }
        Feedback feedback = feedbackDTO.toFeedback();;
        feedback.setMerchantId(merchantId);
        if(!feedback.persist()){
            return response().status(Status.ALREADY_FEEDBACK);
        }
        Merchant.findMerchant(merchantId).updateScore(feedback);
        
        return success("留言成功");
    }
}
