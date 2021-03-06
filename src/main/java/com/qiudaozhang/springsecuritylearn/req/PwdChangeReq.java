package com.qiudaozhang.springsecuritylearn.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 邱道长
 * 2020/12/24
 * 密码修改请求
 */
@Setter
@Getter
public class PwdChangeReq {

    private String oldPassword;

    private String newPassword;
}
