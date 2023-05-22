package com.fairing.dao;

import com.fairing.model.Blog;
import com.fairing.model.Focus;
import com.fairing.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FocusDao {
    List<User> focusListById(Integer id);

    List<Blog> focusBlogListById(Integer id);

    Long selectUserIdByBlogId(Long id);

    List<Focus> selectFocusListById(Long id);

    Integer addFocusById(@Param("userId") Long userId,
                         @Param("loginId") Long loginId,
                         @Param("date") Date date);

    List<User> selectFans(Integer id);
}
