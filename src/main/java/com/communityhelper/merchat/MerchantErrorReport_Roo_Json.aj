// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.communityhelper.merchat;

import com.communityhelper.merchat.MerchantErrorReport;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect MerchantErrorReport_Roo_Json {
    
    public String MerchantErrorReport.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static MerchantErrorReport MerchantErrorReport.fromJsonToMerchantErrorReport(String json) {
        return new JSONDeserializer<MerchantErrorReport>().use(null, MerchantErrorReport.class).deserialize(json);
    }
    
    public static String MerchantErrorReport.toJsonArray(Collection<MerchantErrorReport> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<MerchantErrorReport> MerchantErrorReport.fromJsonArrayToMerchantErrorReports(String json) {
        return new JSONDeserializer<List<MerchantErrorReport>>().use(null, ArrayList.class).use("values", MerchantErrorReport.class).deserialize(json);
    }
    
}