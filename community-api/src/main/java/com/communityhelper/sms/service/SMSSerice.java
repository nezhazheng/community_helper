package com.communityhelper.sms.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.communityhelper.util.HttpUtils;
import com.communityhelper.util.MD5;

@Service
public class SMSSerice {
    private static final Logger logger=Logger.getLogger(SMSSerice.class);
    @Value("${sms_username}")
    private String username;
    @Value("${sms_passowrd}")
    private String password;
    
    public boolean sendValidCode(String validCode, String... mobileArray) {
        try {
            String target=StringUtils.join(mobileArray,",");//多个号码已逗号分隔
            String text = String.format("欢迎使用手机验证，验证码为%s，如非本人操作，请忽略本短信。【宠物圈】", validCode);
            
            return sendMessage(text, target);
        } catch (Exception e) {
            logger.error("乐信发送短信异常",e);
            return false;
        }
    }
    
    public boolean sendFindPassword(String newPassword, String phonenum){
        String text = String.format("您的随机密码密码为%s，建议您登录后及时修改密码。【宠物圈】", newPassword);
        try {
            return sendMessage(text, phonenum);
        }
        catch (UnsupportedEncodingException e) {
            logger.error("乐信发送短信异常",e);
            return false;
        }
    }
    
    public boolean sendMessage(String text, String target) throws UnsupportedEncodingException{
        logger.info("使用乐信给"+target+"发送短信:"+text);
        String url="http://www.lx198.com/sdk/send";
        StringBuffer params = new StringBuffer();
        params.append("accName="+username); 
        params.append("&accPwd="+MD5.getMd5String(password)); 
        params.append("&aimcodes="+target);
        params.append("&content="+URLEncoder.encode(text,"UTF-8"));
        params.append("&bizId="+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        params.append("&dataType=string");
        
        String result = HttpUtils.sendRequestByPost(url, params.toString(), true);
        String code=result.split(";")[0];
        logger.info("使用乐信给"+target+"发送短信:"+text+",result:"+result);
        return "1".equals(code);
    }
}
