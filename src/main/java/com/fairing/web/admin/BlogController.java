package com.fairing.web.admin;

import com.fairing.po.Blog;
import com.fairing.po.User;
import com.fairing.service.BlogService;
import com.fairing.service.TagService;
import com.fairing.service.TypeService;
import com.fairing.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by flanker on 2022/05/04.
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

//  展示全部博客列表及分页
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

//  多条件查询博客
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
//        多条件查询到的博客列表放入request作用域
        model.addAttribute("page", blogService.listBlog(pageable, blog));
//        博客列表局部刷新
        return "admin/blogs :: blogList";
    }

//  新建文章
    @GetMapping("/blogs/input")
    public String input(Model model) {
//        将分类与标签列表放入request作用域传入博客发布页
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
//        转发到博客发布页
        return INPUT;
    }

//  工具类，将分类与标签列表放入request作用域
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }


//  博客管理页中点击编辑博客
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
//        将分类与标签列表放入request作用域传入博客发布页
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
//        转发到博客发布页
        return INPUT;
    }


//  添加修改博客
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
//        设置博客作者
        blog.setUser((User) session.getAttribute("user"));
//        设置博客分类
        blog.setType(typeService.getType(blog.getType().getId()));
//        设置博客标签
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
//        若未获得博客id则为新发布博客执行添加操作
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
//        若博客对象为空则添加或修改失败，反之则成功
        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败!");
        } else {
            attributes.addFlashAttribute("message", "操作成功!");
        }
//        重定向到博客管理页
        return REDIRECT_LIST;
    }

//  删除博客
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功!");
        return REDIRECT_LIST;
    }



}
