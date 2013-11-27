package com.communityhelper.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.APIResponse.Status;
import com.communityhelper.category.MerchantStatus;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.api.representation.MerchantDTO;
import com.communityhelper.security.TokenService;
import com.communityhelper.user.User;

import static com.communityhelper.api.APIResponse.*;

@Controller
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private TokenService service;
    
    private static final StandardPasswordEncoder PASSWORD_ENCODER = new StandardPasswordEncoder("community");
    
    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public
    @ResponseBody
    APIResponse autheticate(@RequestBody UserDTO user) {
        User tryUser = User.findUserByMobile(user.getPhonenum());
        if(tryUser == null){
            return response().status(Status.USER_NOT_FOUND);
        }
        if(!PASSWORD_ENCODER.matches(user.getPassword(), tryUser.getPassword())){
            return response().status(Status.PASSWORD_ERROR);
        }
        String token = service.getToken(tryUser);
        user.setToken(token);
        user.setAddress(tryUser.getAddress());
        user.setRealName(tryUser.getRealName());
        user.setId(tryUser.getId());
        return success("用户登录成功").result(user);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    APIResponse register(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setPhonenum(userDTO.getPhonenum());
        user.setPassword(PASSWORD_ENCODER.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setRealName(userDTO.getRealName());
        
        if(user.persist()) {
            return success("注册成功").result(user);
        } else {
            return response().status(Status.USER_ALREADY_EXISTS);
        }
    }
    
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public
    @ResponseBody
    APIResponse updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        User user = User.findUser(id);
        if(StringUtils.hasLength(userDTO.getAddress())) {
            user.setAddress(userDTO.getAddress());
        }
        if(StringUtils.hasLength(userDTO.getRealName())) {
            user.setRealName(userDTO.getRealName());
        }
        if(StringUtils.hasLength(userDTO.getPassword())) {
            user.setPassword(userDTO.getPassword());
        }
        user.merge();
        return success("完善成功");
    }
    
    @RequestMapping(value = "/{userId}/merchant/auth", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse merchantAuth(@PathVariable Integer userId, @RequestBody MerchantDTO dto) {
        Merchant merchant = dto.toMerchant();
        merchant.setUserId(userId);
        merchant.setStatus(MerchantStatus.NOT_VALID);
        merchant.persist();
        return success("商户添加认证成功");
    }
}
