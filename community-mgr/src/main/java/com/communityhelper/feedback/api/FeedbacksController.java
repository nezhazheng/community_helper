package com.communityhelper.feedback.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.communityhelper.mgr.feedback.Feedback;
import com.communityhelper.page.Page;

@Controller
@RequestMapping("/merchant/feedback")
public class FeedbacksController {
    
    @RequestMapping(method = RequestMethod.GET)
    public 
    @ResponseBody
    Page<Feedback> feedbacks(@RequestParam Integer page, 
            @RequestParam Integer start, 
            @RequestParam Integer limit){
        
        return Feedback.findAllFeedbacks(start, limit);
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public 
    @ResponseBody
    String delete(@RequestParam("id") Integer id){
        Feedback.findFeedback(id).remove();
        return "success";
    }
}
