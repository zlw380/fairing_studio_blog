package com.fairing.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

//    转发到注册页面
    @GetMapping("/register")
    public String register(){
        return "/admin/register";
    }
}
