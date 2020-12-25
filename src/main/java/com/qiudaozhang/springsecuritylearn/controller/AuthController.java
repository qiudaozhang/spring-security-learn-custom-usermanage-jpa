package com.qiudaozhang.springsecuritylearn.controller;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.req.LoginPhoneCode;
import com.qiudaozhang.springsecuritylearn.req.Loginup;
import com.qiudaozhang.springsecuritylearn.service.UserService;
import com.qiudaozhang.springsecuritylearn.service.UserTokenService;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private UserTokenService userTokenService;

    // 用户名密码登录
    @PostMapping("login/up")
    public ServerResponse login(@RequestBody Loginup req) {
       return userService.login(req);
    }

    @PostMapping("login/sms")
    public ServerResponse login(@RequestBody LoginPhoneCode req) {
       return userService.login(req);
    }

    @GetMapping("uid")
    public ServerResponse uid () {
        return  userTokenService.currentUid();
    }

}
