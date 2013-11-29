package com.communityhelper.sms;

import static com.communityhelper.api.APIResponse.success;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.sms.service.SMSSerice;

import static com.communityhelper.api.APIResponse.*;

@Controller
@RequestMapping(value = "/sms")
public class SMSController {
    @Autowired
    private SMSSerice smsService;
    
    @RequestMapping(value = "/{phonenum}/sendvalidcode")
    public 
    @ResponseBody
    APIResponse sendValidCode(@PathVariable String phonenum, @RequestBody APIRequest device) {
        List<ValidCode> validCodes = ValidCode.findValidCodeTodayByPhonenum(phonenum, "register_valid_code");
        // 验证码发送超出上限
        if(validCodes.size() >= 3){
            return response().status(Status.TOO_MANY_VALID_CODE);
        }
        String validCode = ValidCode.generate(phonenum, "register_valid_code");
        
        
        if(!smsService.sendValidCode(validCode, phonenum)){
            return response().status(Status.SEND_SMS_ERROR);
        }
        return success("验证码发送成功"); 
    }
    
    @RequestMapping(value = "/{phonenum}/valid/{validCodeStr}")
    public 
    @ResponseBody
    APIResponse validCode(@PathVariable String validCodeStr, 
            @PathVariable String phonenum,
           @RequestBody APIRequest device){
        List<ValidCode> validCodes = ValidCode.findValidCodeTodayByPhonenum(phonenum, "register_valid_code");
        // recently valid code
        ValidCode validCode = validCodes.get(0);
        if(! StringUtils.equals(validCode.getSecuritycode(), validCodeStr)){
            return response().status(Status.VALID_CODE_NOT_VALID);
        }
        return success("验证通过"); 
    }
}
