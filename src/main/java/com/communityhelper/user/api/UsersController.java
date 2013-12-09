package com.communityhelper.user.api;

import static com.communityhelper.api.APIResponse.response;
import static com.communityhelper.api.APIResponse.success;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIRequest;
import com.communityhelper.api.APIResponse;
import com.communityhelper.api.APIResponse.Status;
import com.communityhelper.category.MerchantStatus;
import com.communityhelper.merchat.Merchant;
import com.communityhelper.merchat.MyMerchantCollection;
import com.communityhelper.merchat.api.representation.MerchantRequest;
import com.communityhelper.security.TokenService;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.User;
import com.communityhelper.user.User.UserAuthStatus;
import com.communityhelper.user.api.representation.MyDTO;
import com.communityhelper.user.api.representation.UserDTO;

@Controller
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private TokenService service;
    
    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public
    @ResponseBody
    APIResponse autheticate(@RequestBody UserDTO user) {
        User tryUser = User.findUserByPhonenum(user.getPhonenum());
        if(tryUser == null){
            return response().status(Status.USER_NOT_FOUND);
        }
        if(!User.PASSWORD_ENCODER.matches(user.getPassword(), tryUser.getPassword())){
            return response().status(Status.PASSWORD_ERROR);
        }
        String token = service.getToken(tryUser);
        user.setToken(token);
        user.setAddress(tryUser.getAddress());
        user.setRealName(tryUser.getRealName());
        user.setUserAuthStatus(tryUser.getRealNameAuth());
        user.setId(tryUser.getId());
        return success("用户登录成功").result(user);
    }
    
    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    APIResponse register(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setPhonenum(userDTO.getPhonenum());
        user.setPassword(User.PASSWORD_ENCODER.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setRealName(userDTO.getRealName());
        user.setImei(userDTO.getImei());
        user.setChannel(userDTO.getChannel());
        user.setRealNameAuth(UserAuthStatus.HAS_NOT_AUTH);
        
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
        user.merge();
        return success("完善成功");
    }
    
    
    /**
     * 修改密码
     * @param id
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/{id}/modifypassowrd", method = RequestMethod.POST)
    public
    @ResponseBody
    APIResponse modifyPassword(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        User user = User.findUser(id);
        if(!User.PASSWORD_ENCODER.matches(userDTO.getOldPassword(), user.getPassword())){
            return response().status(Status.PASSWORD_ERROR);
        }
        user.setPassword(User.PASSWORD_ENCODER.encode(userDTO.getPassword()));
        user.merge();
        String token = service.getToken(user);
        userDTO.setToken(token);
        return success("修改密码成功").result(userDTO);
    }
    
    /**
     * 实名认证
     * @param id
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/{id}/realnameauth", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse realNameAuth(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        User user = User.findUser(id);
        if(UserAuthStatus.WAIT_TO_AUTH.equals(user.getRealNameAuth())){
            return response().status(Status.WAIT_TO_AUTH);
        }
        if(UserAuthStatus.ALREADY_AUTH.equals(user.getRealNameAuth())){
            return response().status(Status.ALREADY_AUTH);
        }
        user.setRealNameAuth(UserAuthStatus.WAIT_TO_AUTH);
        user.merge();
        RealNameAuth auth = userDTO.toRealNameAuth(id);
        auth.persist();
        return success("实名认证成功");
    }
    
    /**
     * 用户实名申请商户
     * @param userId
     * @param dto
     * @return
     */
    @RequestMapping(value = "/{userId}/merchant/auth", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse merchantAuth(@PathVariable Integer userId, @RequestBody MerchantRequest dto) {
        Merchant merchant = dto.toMerchant();
        merchant.setUserId(userId);
        merchant.setStatus(MerchantStatus.NOT_VALID);
        merchant.persist();
        return success("商户添加认证成功");
    }
    
    /**
     * 我的信息（含商户）
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/{userId}/my", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse myMerchants(@PathVariable Integer userId, @RequestBody MerchantRequest request) {
        List<Merchant> merchants = Merchant.findMerchantsByUserId(userId);
        MyDTO my = new MyDTO();
        my.setMerchants(merchants);
        User user = User.findUser(userId);
        my.setUser(user);
        return success("我的信息查询成功").result(my);
    }
    
    /**
     * 我的收藏
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/{userId}/merchantcollection", method = RequestMethod.POST)
    public 
    @ResponseBody
    APIResponse myMerchants(@PathVariable Integer userId, @RequestBody APIRequest request) {
        List<Merchant> merchants = MyMerchantCollection.findMyMerchantCollectionByUserId(userId);
        return success("我的收藏查询成功").result(merchants);
    }
}
