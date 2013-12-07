package com.communityhelper.feedback;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.communityhelper.TestEnviroment;
import com.communityhelper.api.Page;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/Feedback.yml")
public class FeedbackTest extends TestEnviroment {
    @Test
    public void should_got_feedback_list_with_phonenum(){
        Page<Feedback> page = Feedback.findFeedbacksByMerchant(1, 0, 2);
        assertEquals(1, page.getList().size());
        assertEquals("13311008877", page.getList().get(0).getPhonenum());
    }
}
