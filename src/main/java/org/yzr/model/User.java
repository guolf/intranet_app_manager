package org.yzr.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户表
 *
 * @author guolf
 */
@Entity
@Table(name="tb_user")
@Setter
@Getter
public class User {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    /**
     * 登录账号
     */
    @Column(unique = true)
    private String loginName;

    /**
     * 密码
     */
    private String password;
}
