package com.fairing.web.admin;

import com.fairing.po.User;
import com.fairing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by flanker on 2022/05/04.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "/admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
//        检查用户名与密码是否正确
        User user = userService.checkUser(username, password);
//        查询结果非空，密码正确
        if (user != null) {
            user.setPassword(null);
//            登录用户信息放入session对象
            session.setAttribute("user",user);
//            是管理员则进入管理员后台页面，非管理员进入非管理员后台
            if(user.getType() == 1){
                return "admin/index";
            }else {
                return "admin/user";
            }
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

//    注销用户
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
