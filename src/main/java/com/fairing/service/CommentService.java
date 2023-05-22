package com.fairing.service;

import com.fairing.po.Comment;

import java.util.List;

/**
 * Created by flanker on 2022/05/04.
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
