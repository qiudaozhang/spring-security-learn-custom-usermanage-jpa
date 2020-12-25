package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.consts.RedisKeys;
import com.qiudaozhang.springsecuritylearn.req.SmsReq;
import com.qiudaozhang.springsecuritylearn.service.SmsService;
import com.qiudaozhang.springsecuritylearn.util.CodeGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author 邱道长
 * 2020/12/25
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public ServerResponse send(SmsReq smsReq) {

        String key = null;
        switch (smsReq.getType()) {
            case 1:
                break;
            case 2:
                // 登录
                key = RedisKeys.PHONE_LOGIN_CODE + smsReq.getPhone();
                redisTemplate.opsForValue().set(key, CodeGenerator.codeNumber(),60 * 3, TimeUnit.SECONDS);
                return ServerResponse.success("登录验证码已发送，请在3分钟内使用。");
        }
        return null;
    }
}
