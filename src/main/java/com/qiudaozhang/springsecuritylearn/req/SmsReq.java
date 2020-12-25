package com.qiudaozhang.springsecuritylearn.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Getter
@Setter
public class SmsReq {

    private String phone;

    // 目的 1: 注册， 2: 登录
    private Integer type;
}
