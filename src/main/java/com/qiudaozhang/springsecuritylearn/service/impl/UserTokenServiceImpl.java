package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.dto.UserTokenDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public long currentUid() {
        // 得到当前用户的token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);
        String token = authentication.getCredentials().toString();
        String key = "USER_TOKEN:" + token;
        Object obj = redisTemplate.opsForValue().get(key);
//        System.out.println(obj);
        UserTokenDto dto = (UserTokenDto) obj;
        return dto.getUid();
    }
}
