package com.communityhelper.merchat.api;

import java.util.List;
import static com.communityhelper.api.APIResponse.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.Page;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantStatus;
import com.communityhelper.user.User;

@Controller
@RequestMapping(value = "/merchant", method = RequestMethod.GET)
public class MerchantsController {
    /**
     * 商户详情
     */
    @RequestMapping(value = "/all")
    public 
    @ResponseBody
    Page<Merchant> all(@RequestParam Integer page, 
            @RequestParam Integer start, 
            @RequestParam Integer limit) {
        List<Merchant> merchants = Merchant.findMerchantEntries(start, limit);
        Page<Merchant> pageMerchants = new Page<Merchant>();
        pageMerchants.setList(merchants);
        pageMerchants.setTotalResult(Integer.parseInt((Merchant.countMerchants() + "")));
        return pageMerchants;
    }
    
}
