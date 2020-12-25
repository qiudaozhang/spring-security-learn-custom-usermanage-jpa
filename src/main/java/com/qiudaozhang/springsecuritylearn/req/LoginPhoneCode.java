package com.qiudaozhang.springsecuritylearn.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 邱道长
 * 2020/12/25
 * 手机和验证码登录
 */
@Setter
@Getter
public class LoginPhoneCode {

    private String phone;

    private String code;

}
