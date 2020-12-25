package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.dto.UserTokenDto;
import com.qiudaozhang.springsecuritylearn.service.UserTokenService;
import com.qiudaozhang.springsecuritylearn.util.TimeUtil;
import com.qiudaozhang.springsecuritylearn.util.TokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${jwt.key}")
    private String jwtKey;

    @Override
    public ServerResponse currentUid() {
        // 得到当前用户的token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);
        String token = authentication.getCredentials().toString();
        String key = "USER_TOKEN:" + token;
        Object obj = redisTemplate.opsForValue().get(key);
        if(obj == null) {
            return ServerResponse.error("非法凭证！");
        }
        UserTokenDto dto = (UserTokenDto) obj;
        return ServerResponse.success(dto.getUid());
    }

    @Override
    public String token(long uid) {
        String token = TokenUtil.token
                (uid,
                        TimeUtil.ONE_DAY_SECONDS,
                        SignatureAlgorithm.HS512,
                        jwtKey);
        return token;
    }
}
