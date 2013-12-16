package com.communityhelper.user.api;

import static com.communityhelper.api.APIResponse.response;
import static com.communityhelper.api.APIResponse.success;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.communityhelper.merchant.Merchant;
import com.communityhelper.merchant.MyMerchantCollection;
import com.communityhelper.merchat.api.MerchantRequest;
import com.communityhelper.merchat.api.representation.UserMerchantDTO;
import com.communityhelper.merchat.service.MerchantService;
import com.communityhelper.security.TokenService;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.User;
import com.communityhelper.user.RealNameAuthStatus;
import com.communityhelper.user.UserServiceStatus;
import com.communityhelper.user.api.representation.MyDTO;
import com.communityhelper.user.api.representation.UserDTO;

@Controller
@RequestMapping("/user")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private TokenService service;
    
    @Autowired
    private MerchantService merchantService;
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
        user.setRealNameAuthStatus(tryUser.getRealNameAuthStatus());
        user.setId(tryUser.getId());
        logger.info("用户登陆成功，phonenum:"+user.getPhonenum());
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
        user.setCreateDate(new Date());
        user.setUserServiceStatus(UserServiceStatus.DO_BUSINESS);
        user.setRealNameAuthStatus(RealNameAuthStatus.HAS_NOT_AUTH);
        
        if(user.persist()) {
            return success("注册成功").result(user);
        } else {
            return response().status(Status.USER_ALREADY_EXISTS);
        }
    }
    
    /**
     * 更新用户信息
     * @param id
     * @param userDTO
     * @return
     */
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
        if(null != userDTO.getUserServiceStatus()) {
            user.setUserServiceStatus(userDTO.getUserServiceStatus());
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
        if(RealNameAuthStatus.WAIT_TO_AUTH.equals(user.getRealNameAuthStatus())){
            return response().status(Status.WAIT_TO_AUTH);
        }
        if(RealNameAuthStatus.ALREADY_AUTH.equals(user.getRealNameAuthStatus())){
            return response().status(Status.ALREADY_AUTH);
        }
        user.setRealNameAuthStatus(RealNameAuthStatus.WAIT_TO_AUTH);
        user.merge();
        RealNameAuth auth = userDTO.toRealNameAuth(id);
        auth.persist();
        return success("实名认证成功");
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
        List<UserMerchantDTO> userMerchants = merchantService.toUserMerchantDTO(userId, merchants);
        MyDTO my = new MyDTO();
        my.setMerchants(userMerchants);
        User user = User.findUser(userId);
        my.setUser(user);
        RealNameAuth realNameAuth = RealNameAuth.findRealNameAuthByUserId(userId);
        my.setRealNameAuth(realNameAuth);
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
    APIResponse myMerchantCollection(@PathVariable Integer userId, @RequestBody APIRequest request) {
        List<Merchant> merchants = MyMerchantCollection.findMyMerchantCollectionByUserId(userId);
        List<UserMerchantDTO> userMerchants = merchantService.toUserMerchantDTO(userId, merchants);
        return success("我的收藏查询成功").result(userMerchants);
    }
}
