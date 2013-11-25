// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.feedback;

import com.communityhelper.feedback.FeedbackPK;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect FeedbackPK_Roo_Json {
    
    public String FeedbackPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static FeedbackPK FeedbackPK.fromJsonToFeedbackPK(String json) {
        return new JSONDeserializer<FeedbackPK>().use(null, FeedbackPK.class).deserialize(json);
    }
    
    public static String FeedbackPK.toJsonArray(Collection<FeedbackPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<FeedbackPK> FeedbackPK.fromJsonArrayToFeedbackPKs(String json) {
        return new JSONDeserializer<List<FeedbackPK>>().use(null, ArrayList.class).use("values", FeedbackPK.class).deserialize(json);
    }
    
}
