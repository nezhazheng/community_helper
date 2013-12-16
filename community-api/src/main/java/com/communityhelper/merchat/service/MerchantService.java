package com.communityhelper.merchat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MyMerchantCollection;
import com.communityhelper.merchat.api.representation.UserMerchantDTO;
import com.communityhelper.user.User;
import com.communityhelper.user.UserServiceStatus;

@Service
public class MerchantService {
    public static UserMerchantDTO findMerchant(Integer merchantId, Integer userId) {
        Merchant merchant = Merchant.findMerchant(merchantId);
        return creaetUserMerchantDTO(merchant, userId);
    }
    
    public static Boolean isMerchantCollected(Integer merchantId, Integer userId){
        MyMerchantCollection collection = MyMerchantCollection.find(userId, merchantId);
        return collection != null;
    }
    
    public static UserMerchantDTO creaetUserMerchantDTO(Merchant merchant, Integer userId){
        UserMerchantDTO userMerchant = new UserMerchantDTO(merchant);
        userMerchant.setCollected(isMerchantCollected(merchant.getId(), userId));
        if(merchant.getUserId() != null && 0 != merchant.getUserId()) {
            userMerchant.setUserServiceStatus(User.findUser(merchant.getUserId()).getUserServiceStatus());
        } else {
            userMerchant.setUserServiceStatus(UserServiceStatus.DO_BUSINESS);
        }
        return userMerchant;
    }
    
    public static List<UserMerchantDTO> findUserMerchantsByUserId(Integer userId) {
        List<Merchant> merchants = Merchant.findMerchantsByUserId(userId);
        return toUserMerchantDTO(userId, merchants);
    }
    
    public static List<UserMerchantDTO> toUserMerchantDTO(Integer userId, List<Merchant> merchants) {
        List<UserMerchantDTO> userMerchants = new ArrayList<UserMerchantDTO>();
        for(Merchant merchant : merchants){
            UserMerchantDTO userMerchant = creaetUserMerchantDTO(merchant, userId);
            userMerchants.add(userMerchant);
        }
        return userMerchants;
    }
}
