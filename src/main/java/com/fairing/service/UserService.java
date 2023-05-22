package com.fairing.service;

import com.fairing.po.User;

/**
 * Created by flanker on 2022/05/04.
 */
public interface UserService {

    User checkUser(String username, String password);
    int addUser(User user);
}
