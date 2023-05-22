package com.fairing.service;

import com.fairing.dao.UserDao;
import com.fairing.dao.UserRepository;
import com.fairing.po.User;
import com.fairing.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by flanker on 2022/05/04.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Resource
    private UserDao userDao;

//  根据传入的用户名与密码查表返回用户信息，同时检查用户名密码是否正确
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

//  用户注册添加用户
    public int addUser(User user){
        int nums = userDao.addUser(user);
        return nums;
    }
}
