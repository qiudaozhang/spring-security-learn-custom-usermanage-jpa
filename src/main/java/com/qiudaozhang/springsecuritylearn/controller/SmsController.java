package com.qiudaozhang.springsecuritylearn.controller;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.req.SmsReq;
import com.qiudaozhang.springsecuritylearn.service.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 邱道长
 * 2020/12/25
 */
@RestController
@RequestMapping("api/v1/sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    @PostMapping("send")
    public ServerResponse send(@RequestBody SmsReq smsReq) {
        return smsService.send(smsReq);
    }
}
