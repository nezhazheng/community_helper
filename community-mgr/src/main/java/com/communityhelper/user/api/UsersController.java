package com.communityhelper.user.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.communityhelper.page.Page;
import com.communityhelper.page.PageService;
import com.communityhelper.user.RealNameAuth;
import com.communityhelper.user.User;
import com.communityhelper.user.RealNameAuthStatus;

@Controller
@RequestMapping("/user")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    
    @Autowired
    private PageService pageService;
    
    /**
     * 实名认证
     * @param id
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/realnameauth", method = RequestMethod.GET)
    public 
    @ResponseBody
    List<RealNameAuth> realNameAuthList(){
        List<RealNameAuth> realNameAuths = RealNameAuth.findAllRealNameAuths();
        return realNameAuths;
    }
    
    @Transactional
    @RequestMapping(value = "/realnameauth", method = RequestMethod.POST)
    public 
    @ResponseBody
    String realNameAuth(@RequestParam Integer authId, 
            @RequestParam Integer userId, 
            @RequestParam("status") RealNameAuthStatus status) {
        RealNameAuth realNameAuth = RealNameAuth.findRealNameAuth(authId);
        User user = User.findUser(userId);
        user.setRealNameAuthStatus(status);
        user.setRealName(realNameAuth.getRealName());
        user.setAddress(realNameAuth.getContractAddress());
        user.merge();
        
        realNameAuth.remove();
        return "success";
    }
    
    @RequestMapping
    public
    @ResponseBody
    Page<User> users(@RequestParam Integer page, 
            @RequestParam Integer start, 
            @RequestParam Integer limit){
        List<User> users =  User.findOrderableUsersPage(start, limit);
        Page<User> userPage = pageService.createPage(users, Integer.parseInt(User.countUsers() + ""), start, limit);
        return userPage;
    }
}
