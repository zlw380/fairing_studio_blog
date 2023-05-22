package com.fairing.service;

import com.fairing.dao.FocusDao;
import com.fairing.model.Blog;
import com.fairing.model.Focus;
import com.fairing.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class FocusServiceImpl implements FocusService {

    @Resource
    private FocusDao focusDao;

//    关注人列表
    @Override
    @Transactional
    public List<User> focusListById(Integer id) {
        List<User> focusList = focusDao.focusListById(id);
        return focusList;
    }

//    关注人博客列表
    @Override
    @Transactional
    public List<Blog> focusBlogListById(Integer id) {
        List<Blog> focusBlogList = focusDao.focusBlogListById(id);
        return focusBlogList;
    }

//    判断是否已经关注
    @Override
    @Transactional
    public boolean ifFocus(HttpSession session,Long id) {
        //通过博客id查询发表博客的用户id
        Long userId = focusDao.selectUserIdByBlogId(id);
        //以session对象中用户id与博客作者id为参数判断是否关注
        //从session对象中取出登录的user对象
        com.fairing.po.User user = (com.fairing.po.User) session.getAttribute("user");
        //获得user对象中的用户id并以此id查询所有关注的记录
        List<Focus> focusList = focusDao.selectFocusListById(user.getId());
        //查询不到关注记录直接返回false
        if(focusList == null) {
            return false;
        }
        //遍历记录，如果作者id与被关注者id相同则返回true
        for (Focus fcs:focusList
             ) {
            if(userId == fcs.getFocusId()){
                return true;
            }
        }
        return false;
    }

//    添加关注
    @Override
    @Transactional
    public Integer addFocus(HttpSession session, Long id) {
        //通过博客id查询发表博客的用户id
        Long userId = focusDao.selectUserIdByBlogId(id);
        //获取登录用户对象
        com.fairing.po.User user = (com.fairing.po.User) session.getAttribute("user");
        //获取登录用户id
        Long loginId = user.getId();
        Integer num = focusDao.addFocusById(userId,loginId,new Date());
        return num;
    }

//    查询粉丝列表
    @Override
    @Transactional
    public List<User> selectFans(Integer id) {
        List<User> fansList = focusDao.selectFans(id);
        return fansList;
    }
}
