package com.communityhelper.feedback.api;

import static com.communityhelper.api.APIResponse.response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.APIResponse.Status;
import com.communityhelper.feedback.Feedback;

@Controller
@RequestMapping("/merchant/{merchantId}/feedback")
public class FeedbacksController {
    
    @RequestMapping(method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse publishFeedback(@PathVariable Integer merchantId,
            @RequestBody FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback(feedbackDTO, merchantId);
        if(!feedback.persist()){
            return response().status(Status.ALREADY_FEEDBACK);
        }
        return response().success("留言成功");
    }
}
