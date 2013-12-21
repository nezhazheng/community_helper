package com.communityhelper.feedback;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.communityhelper.TestEnviroment;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/Feedback.yml")
public class FeedbackTest extends TestEnviroment {
    @Test
    public void should_got_feedback_list_with_phonenum() {
        List<Feedback> page = Feedback.findFeedbacksByMerchant(1, 0, 2);
        assertEquals(1, page.size());
        assertEquals("13311008877", page.get(0).getPhonenum());
    }
}
