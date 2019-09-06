package org.yzr.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.yzr.model.User;

/**
 * 用户持久层
 *
 * @author guolf
 */
public interface UserDao extends CrudRepository<User, String> {

    /**
     * 根据登录名查找用户
     *
     * @param loginName
     * @return
     */
    @Query("select a from User a where a.loginName=:loginName")
    User findByLoginName(@Param("loginName") String loginName);

}
