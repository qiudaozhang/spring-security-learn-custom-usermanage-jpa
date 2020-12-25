package com.qiudaozhang.springsecuritylearn.controller;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.req.Loginup;
import com.qiudaozhang.springsecuritylearn.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 邱道长
 * 2020/12/25
 */
@RestController
@RequestMapping("api/v1/user")
public class AuthController {

    @Resource
    private UserService userService;

    // 用户名密码登录
    @PostMapping("login/up")
    public ServerResponse login(Loginup req) {
       return userService.login(req);
    }

}
