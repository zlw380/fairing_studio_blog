package com.fairing.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by flanker on 2022/05/04.
 */
@Controller
public class AboutShowController {

//    静态页面，纯转发
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
