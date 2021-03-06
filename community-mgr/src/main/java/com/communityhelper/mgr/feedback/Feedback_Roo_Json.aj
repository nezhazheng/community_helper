// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.mgr.feedback;

import com.communityhelper.mgr.feedback.Feedback;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Feedback_Roo_Json {
    
    public String Feedback.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Feedback Feedback.fromJsonToFeedback(String json) {
        return new JSONDeserializer<Feedback>().use(null, Feedback.class).deserialize(json);
    }
    
    public static String Feedback.toJsonArray(Collection<Feedback> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Feedback> Feedback.fromJsonArrayToFeedbacks(String json) {
        return new JSONDeserializer<List<Feedback>>().use(null, ArrayList.class).use("values", Feedback.class).deserialize(json);
    }
    
}
