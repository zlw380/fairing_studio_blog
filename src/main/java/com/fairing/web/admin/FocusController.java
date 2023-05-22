package com.fairing.web.admin;

import com.fairing.model.Blog;
import com.fairing.model.User;
import com.fairing.service.BlogService;
import com.fairing.service.FocusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FocusController {

    @Resource
    private FocusService focusService;

    @Resource
    private BlogService blogService;

//    关注人列表
    @GetMapping("/admin/focus/{id}")
    public String focusList(@PathVariable Integer id, Model model){
        List<User> focusList = focusService.focusListById(id);
        model.addAttribute("focusList",focusList);
        return "admin/focus";
    }

//    关注人的博客列表
    @GetMapping("/admin/focusBlogById/{id}")
    public String focusBlogById(@PathVariable Integer id,Model model){
        List<Blog> focusBlogListById = focusService.focusBlogListById(id);
        model.addAttribute("focusBlogListById",focusBlogListById);
        return "admin/focusBlog";
    }

//    点击关注并刷新页面
    @GetMapping("/admin/addFocus/{id}")
    public String addFocus(@PathVariable Long id, Model model, HttpSession session){
        model.addAttribute("blog", blogService.getAndConvert(id));
//        先行检查是否添加关注，若已加关注直接返回视图
        Boolean preIfFocus = focusService.ifFocus(session,id);
        if(preIfFocus == true){
            Boolean ifFocus1 = focusService.ifFocus(session,id);
            model.addAttribute("ifFocus",ifFocus1);
            return "blog";
        }
//        添加关注操作
        Integer num = focusService.addFocus(session,id);
        if(num < 1){
            System.out.println("添加关注失败");
            return "error/error";
        }
        com.fairing.po.User user = (com.fairing.po.User) session.getAttribute("user");
        //若已登录则判断是否关注博客作者
        if(user!=null) {
            Boolean ifFocus = focusService.ifFocus(session,id);
            model.addAttribute("ifFocus",ifFocus);
        }
        return "blog";
    }

//    查询粉丝列表
    @GetMapping("/admin/fans/{id}")
    public String selectFans(@PathVariable Integer id,Model model){
        List<User> fansList = focusService.selectFans(id);
        model.addAttribute("fansList",fansList);
        return "admin/fans";
    }
}
