package com.qiudaozhang.springsecuritylearn.service;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.req.SmsReq;

/**
 * @author 邱道长
 * 2020/12/25
 * 实际开发，可自行接入对应的短信服务商，这里只是 模拟
 */
public interface SmsService {

    ServerResponse send(SmsReq smsReq);

}
