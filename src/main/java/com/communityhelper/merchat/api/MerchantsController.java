package com.communityhelper.merchat.api;

import static com.communityhelper.api.APIResponse.response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.merchat.Merchant;

@Controller
@RequestMapping("/merchant")
public class MerchantsController {
    /**
     * 商户详情
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse detail(@PathVariable("merchantId") Integer merchantId,
            @RequestBody APIRequest request){
        Merchant merchant = Merchant.findMerchant(merchantId);
        return response().success("查询成功").result(merchant);
    }
}
