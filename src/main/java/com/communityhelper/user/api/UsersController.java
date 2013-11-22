package com.communityhelper.user.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.api.APIResponse;
import com.communityhelper.api.APIResponse.Status;
import com.communityhelper.user.User;

import static com.communityhelper.api.APIResponse.*;
@Controller
@RequestMapping("/user")
public class UsersController {
    private Logger logger = LoggerFactory.getLogger(UsersController.class);
    
    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public
    @ResponseBody
    APIResponse autheticate(@RequestBody UserDTO user) {
        User tryUser = User.findUserByMobile(user.getUsername());
        if(tryUser == null){
            return response().status(Status.USER_NOT_FOUND);
        }
        if(!tryUser.getPassword().equals(user.getPassword())){
            return response().status(Status.PASSWORD_ERROR);
        }
        return success("用户登录成功").result(user);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    APIResponse register(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setMobile(userDTO.getPhonenum());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setRealName(userDTO.getRealName());
        
        if(user.persist()){
            
        }
        return success("注册成功");
    }
}
