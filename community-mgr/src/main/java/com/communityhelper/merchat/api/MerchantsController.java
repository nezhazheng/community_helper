package com.communityhelper.merchat.api;

import java.util.ArrayList;
import java.util.List;
import static com.communityhelper.api.APIResponse.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.Page;
import com.communityhelper.category.service.CategoryService;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MerchantErrorReport;
import com.communityhelper.merchant.MerchantStatus;
import com.communityhelper.merchat.api.representation.MerchantErrorReportDTO;

@Controller
@RequestMapping(value = "/merchant", method = RequestMethod.GET)
public class MerchantsController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 商户列表
     */
    @RequestMapping(value = "/all")
    public 
    @ResponseBody
    Page<Merchant> all(@RequestParam Integer page, 
            @RequestParam Integer start, 
            @RequestParam Integer limit) {
        List<Merchant> merchants = Merchant.findOrderableMerchants(start, limit);
        Page<Merchant> pageMerchants = new Page<Merchant>();
        pageMerchants.setList(merchants);
        pageMerchants.setTotalResult(Integer.parseInt((Merchant.countMerchants() + "")));
        return pageMerchants;
    }
    
    /**
     * 修改商户
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse modify(@RequestParam("status") String status,
            @RequestParam("serviceEnable") Boolean serviceEnable,
            @RequestParam("merchantId") Integer merchantId,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("order") Integer order,
            @RequestParam("contactAddress") String contactAddress,
            @RequestParam("name") String name,
            @RequestParam("contactPhoneNumber") String contactPhoneNumber){
        Merchant merchant = Merchant.findMerchant(merchantId);
        
        categoryService.updateRelatedOrder(merchant, order, categoryId);
        
        merchant.setOrder(order);
        merchant.setServiceEnable(serviceEnable);
        merchant.setContactAddress(contactAddress);
        merchant.setContactPhoneNumber(contactPhoneNumber);
        merchant.setName(name);
        merchant.setAuthStatus(MerchantStatus.valueOf(status));
        merchant.setCategoryId(categoryId);
        merchant.merge();
        return success("审核成功");
    }
    
    /**
     * 商户详情
     */
    @RequestMapping(value = "/errorreport")
    public 
    @ResponseBody
    Page<MerchantErrorReportDTO> errorReportList(@RequestParam Integer page, 
            @RequestParam Integer start, 
            @RequestParam Integer limit) {
        List<MerchantErrorReport> merchants = MerchantErrorReport.findMerchantErrorReportEntries(start, limit);
        List<MerchantErrorReportDTO> dtos = new ArrayList<MerchantErrorReportDTO>();
        for(MerchantErrorReport report: merchants) {
            MerchantErrorReportDTO dto = new MerchantErrorReportDTO();
            dto.setMerchantName(Merchant.findMerchant(report.getMerchantId()).getName());
            dto.setMerchantReport(report);
            dtos.add(dto);
        }
        Page<MerchantErrorReportDTO> pageMerchants = new Page<MerchantErrorReportDTO>(start, limit);
        pageMerchants.setList(dtos);
        pageMerchants.setTotalResult(Integer.parseInt((MerchantErrorReport.countMerchantErrorReports() + "")));
        return pageMerchants;
    }
}