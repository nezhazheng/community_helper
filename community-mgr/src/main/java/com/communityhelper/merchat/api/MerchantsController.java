package com.communityhelper.merchat.api;

import java.util.List;
import static com.communityhelper.api.APIResponse.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.category.MerchantStatus;
import com.communityhelper.merchat.Merchant;

@Controller
@RequestMapping(value = "/merchant", method = RequestMethod.GET)
public class MerchantsController {
    /**
     * 商户详情
     */
    @RequestMapping(value = "/waittoauth")
    public 
    @ResponseBody
    APIResponse waitToAuthList(@RequestParam("communityId") Integer communityId) {
        List<Merchant> merchants = Merchant.findMerchantsByAuthStatus(MerchantStatus.NOT_VALID);
        return success("成功").result(merchants);
    }
    
}
