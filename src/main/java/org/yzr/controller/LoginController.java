package org.yzr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzr.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *
 * @author guolf
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping
    public Map<String, Object> login(String loginName, String password, HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap(2);
        try {
            userService.login(loginName, password);
            map.put("success", true);
            map.put("msg", "登录成功");
            request.getSession().setAttribute("hasLogin", true);
        } catch (RuntimeException ex) {
            map.put("success", false);
            map.put("msg", ex.getMessage());
        }
        return map;
    }
}
