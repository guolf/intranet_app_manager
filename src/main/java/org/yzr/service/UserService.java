package org.yzr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.yzr.dao.UserDao;
import org.yzr.model.User;

import javax.annotation.PostConstruct;

/**
 * 用户服务
 *
 * @author guolf
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @PostConstruct
    public void initUser() {
        long count = userDao.count();
        if (count == 0) {
            String defaultPassword = "123456";
            User user = new User();
            user.setLoginName("admin");
            user.setPassword(DigestUtils.md5DigestAsHex(defaultPassword.getBytes()));
            userDao.save(user);
            log.info("初始化用户成功，账号:{},密码:{}",user.getLoginName(),defaultPassword);
        }
    }

    public User login(String loginName, String password) {
        User user = userDao.findByLoginName(loginName);
        if(user == null) {
            throw new RuntimeException("账号不存在");
        }
        if(DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return user;
        }
        throw new RuntimeException("密码错误");
    }
}
