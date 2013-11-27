package com.communityhelper.merchat.api;

import static com.communityhelper.api.APIResponse.response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.api.Page;
import com.communityhelper.category.MerchantStatus;
import com.communityhelper.feedback.Feedback;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.api.representation.MerchantDTO;
import com.communityhelper.merchat.api.representation.MerchantsDetailDTO;

@Controller
@RequestMapping(value = "/merchant", method = RequestMethod.POST)
public class MerchantsController {
    /**
     * 商户详情
     */
    @RequestMapping(value = "/{merchantId}")
    public 
    @ResponseBody
    APIResponse detail(@PathVariable("merchantId") Integer merchantId,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "size", defaultValue = "4") Integer size,
            @RequestBody APIRequest request){
        MerchantsDetailDTO detailDTO = new MerchantsDetailDTO();
        Page<Feedback> feedbackPage =Feedback.findFeedbacksByMerchant(merchantId, start, size);
        Merchant merchant = Merchant.findMerchant(merchantId);
        detailDTO.setMerchant(merchant);
        detailDTO.setFeedbackList(feedbackPage);
        return response().success("查询成功").result(detailDTO);
    }
    
    @RequestMapping
    public 
    @ResponseBody
    APIResponse addMerchant(@RequestBody MerchantDTO dto){
        Merchant merchant = dto.toMerchant();
        merchant.setStatus(MerchantStatus.NOT_VALID);
        merchant.persist();
        return response().success("添加商户成功");
    }
    
    @RequestMapping(value = "/{merchantId}/audit")
    public 
    @ResponseBody
    APIResponse audit(@RequestParam("status") String status, @PathVariable Integer merchantId){
        Merchant merchant = Merchant.findMerchant(merchantId);
        merchant.setStatus(MerchantStatus.valueOf(status));
        merchant.merge();
        return response().success("审核成功");
    }
}
