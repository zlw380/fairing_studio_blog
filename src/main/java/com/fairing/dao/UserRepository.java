package com.fairing.dao;

import com.fairing.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by flanker on 2022/05/04.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}
