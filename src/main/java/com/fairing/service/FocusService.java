package com.fairing.service;

import com.fairing.model.Blog;
import com.fairing.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FocusService {

    List<User> focusListById(Integer id);

    List<Blog> focusBlogListById(Integer id);

    boolean ifFocus(HttpSession session,Long id);

    Integer addFocus(HttpSession session,Long id);

    List<User> selectFans(Integer id);
}
