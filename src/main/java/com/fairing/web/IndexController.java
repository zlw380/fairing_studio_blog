package com.fairing.web;

import com.fairing.po.User;
import com.fairing.service.BlogService;
import com.fairing.service.FocusService;
import com.fairing.service.TagService;
import com.fairing.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by flanker on 2022/05/04.
 */
@Controller
public class IndexController {

    @Resource
    private FocusService focusService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

//    首页展示
    @GetMapping("/")
    public String index(@PageableDefault(size = 12, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//        首页博客列表，每页12篇博客
        model.addAttribute("page",blogService.listBlog(pageable));
//        首页分类列表，展示博客数最多的6个分类
        model.addAttribute("types", typeService.listTypeTop(6));
//        首页标签列表，展示博客数最多的10个标签
        model.addAttribute("tags", tagService.listTagTop(10));
//        首页推荐列表，展示最新的8篇推荐博客
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

//    关键字全局搜索
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
//        查询结果放入request作用域
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
//        查询的关键字传入查询结果页面保留在文本框中
        model.addAttribute("query", query);
        return "search";
    }

//    博客详情页展示并渲染关注按钮
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        User user = (User) session.getAttribute("user");
        //若已登录则判断是否已经关注博客作者
        if(user!=null) {
            Boolean ifFocus = focusService.ifFocus(session,id);
            model.addAttribute("ifFocus",ifFocus);
        }
        return "blog";
    }

//    底部栏展示最新3篇推荐博客，该功能暂未启用
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
