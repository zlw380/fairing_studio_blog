package com.fairing.web;

import com.fairing.po.User;
import com.fairing.service.UserService;
import com.fairing.util.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class UserController {

    @Resource
    private UserService userService;

//  注册用户，输入的密码使用md5加密
    @PostMapping("/addUser")
    public String doRegister(User user){
        user.setAvatar("/images/fairingclub.jpg");
        user.setNickname(user.getUsername());
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setCreateTime(new Date());
        int nums = userService.addUser(user);
//        注册成功转发到注册成功页面，反之则转发到注册失败页面
        if(nums > 0){
            return "/admin/registerSuccess";
        }else {
            return "/admin/registerFail";
        }
    }
}
