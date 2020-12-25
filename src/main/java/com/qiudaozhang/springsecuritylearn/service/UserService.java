package com.qiudaozhang.springsecuritylearn.service;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.req.LoginPhoneCode;
import com.qiudaozhang.springsecuritylearn.req.Loginup;
import com.qiudaozhang.springsecuritylearn.req.PwdChangeReq;
import com.qiudaozhang.springsecuritylearn.req.UserReq;
import com.qiudaozhang.springsecuritylearn.vo.UserVo;

/**
 * @author 邱道长
 * 2020/12/24
 */
public interface UserService {

    void createUser(UserReq req);

    ServerResponse<UserVo> find(String username);

    ServerResponse changePassword( PwdChangeReq req);

    ServerResponse  delete(String username);

    /**
     * 处理登录请求
     * 用户名密码模式
     * @param req
     * @return
     */
    ServerResponse login(Loginup req);

    ServerResponse login(LoginPhoneCode req);
}
