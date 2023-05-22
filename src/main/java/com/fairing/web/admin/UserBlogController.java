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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//非管理员博客管理页面，只能在博客管理页中查看、修改、删除自己发布的博客，并发布新博客
@Controller
public class UserBlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

//    非管理员用户查看自己发布的博客
    @GetMapping("/userBlogs")
    public String userBlogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                            BlogQuery blog, Model model, HttpSession session) throws ParseException {
        User user = (User) session.getAttribute("user");
//        session对象中获取用户id
        Integer id = Math.toIntExact(user.getId());
//        根据用户id查询该id发布的博客列表
        List<Blog> blogList = blogService.blogListById(id);
        model.addAttribute("bloglist",blogList);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "/admin/userBlogs";
    }
      /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        for (Blog bl:blogList
             ) {
            Date d = bl.getUpdateTime();
            String str = sdf.format(d);
            Date t = sdf.parse(str);
            bl.setUpdateTime(t);
            System.out.println(bl.getUpdateTime());
        }*/

    /*@PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }*/

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/userBlogsInput")
    public String userBlogsInput(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "/admin/userBlogs-input";
    }

    @GetMapping("/userBlogsInput/{id}")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "/admin/userBlogs-input";
    }

    @PostMapping("/userBlogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败!");
        } else {
            attributes.addFlashAttribute("message", "操作成功!");
        }
        return "redirect:/userBlogs";
    }

    @GetMapping("/userBlogsDelete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功!");
        return "redirect:/userBlogs";
    }
}
