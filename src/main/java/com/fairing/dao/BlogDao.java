package com.fairing.dao;

import com.fairing.po.Blog;

import java.util.List;

public interface BlogDao {
    //根据id查询博客列表
    List<Blog> blogListById(Integer id);
}
