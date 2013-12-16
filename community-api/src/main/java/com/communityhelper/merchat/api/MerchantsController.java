package com.communityhelper.merchat.api;

import static com.communityhelper.api.APIResponse.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.Page;
import com.communityhelper.feedback.Feedback;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantErrorReport;
import com.communityhelper.merchant.MyMerchantCollection;
import com.communityhelper.merchat.api.representation.MerchantsDetailDTO;
import com.communityhelper.merchat.api.representation.UserMerchantDTO;
import com.communityhelper.merchat.service.MerchantService;

@Controller
@RequestMapping(value = "/merchant", method = RequestMethod.POST)
public class MerchantsController {
    @Autowired
    private MerchantService merchantService;
    
    /**
     * 商户详情
     */
    @RequestMapping(value = "/{merchantId}")
    public 
    @ResponseBody
    APIResponse detail(@PathVariable("merchantId") Integer merchantId,
            @RequestBody MerchantRequest request){
        MerchantsDetailDTO detailDTO = new MerchantsDetailDTO();
        Page<Feedback> feedbackPage =Feedback.findFeedbacksByMerchant(
                merchantId, request.getStart(), request.getSize());
        UserMerchantDTO merchant = merchantService.findMerchant(merchantId, request.getUserId());
        detailDTO.setMerchant(merchant);
        detailDTO.setFeedbackList(feedbackPage);
        return success("查询成功").result(detailDTO);
    }
    
    /**
     * 修改商户
     * @return
     */
    @RequestMapping(value = "/{merchantId}/update")
    public 
    @ResponseBody
    APIResponse update(@PathVariable("merchantId") Integer merchantId, 
            @RequestBody MerchantRequest request){
        Merchant merchant = Merchant.findMerchant(merchantId);
        if(StringUtils.hasLength(request.getContactAddress())) {
            merchant.setContactAddress(request.getContactAddress());
        }
        if(StringUtils.hasLength(request.getContactPhoneNumber())) {
            merchant.setContactPhoneNumber(request.getContactPhoneNumber());
        }
        if(StringUtils.hasLength(request.getDesc())) {
            merchant.setDescription(request.getDesc());
        }
        if(StringUtils.hasLength(request.getName())) {
            merchant.setName(request.getName());
        }
        if(null != request.getServiceEnable()) {
            merchant.setServiceEnable(request.getServiceEnable());
        }
        if(null != request.getStandardCategoryId() && request.getStandardCategoryId() != 0) {
            merchant.setStandardCategoryId(request.getStandardCategoryId());
        }
        merchant.merge();
        return success("修改成功");
    }
    
    /**
     * 普通用户添加商户
     * @param dto
     * @return
     */
    @RequestMapping
    public 
    @ResponseBody
    APIResponse addMerchant(@RequestBody MerchantRequest dto){
        Merchant merchant = dto.createMerchant();
        merchant.persist();
        return success("添加商户成功");
    }
    

    /**
     * 用户实名申请商户
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse merchantAuth(@RequestBody MerchantRequest request) {
        Merchant merchant = request.createMerchant();
        merchant.setUserId(request.getUserId());
        merchant.persist();
        return success("商户添加认证成功");
    }
    
    /**
     * 商户报错
     */
    @RequestMapping("/{merchantId}/reporterror")
    public 
    @ResponseBody
    APIResponse reportError(@PathVariable("merchantId") Integer merchantId, 
            @RequestBody MerchantRequest request){
        MerchantErrorReport errorReport = new MerchantErrorReport();
        errorReport.setUserId(request.getUserId());
        errorReport.setMerchantId(merchantId);
        errorReport.setErrorCategory(request.getErrorCategory());
        errorReport.persist();
        return success("商户报错操作成功");
    }
    
    /**
     * 添加或取消收藏
     */
    @RequestMapping("/{merchantId}/collection")
    public 
    @ResponseBody
    APIResponse addMyMerchantCollection(@PathVariable("merchantId") Integer merchantId, 
            @RequestBody MerchantRequest request){
        boolean success = MyMerchantCollection.addCollection(request.getUserId(), merchantId);
        if(success) {
            return success("添加收藏成功");
        }
        return success("取消收藏成功");
    }
}
