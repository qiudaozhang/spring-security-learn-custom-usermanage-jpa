package com.qiudaozhang.springsecuritylearn.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author 邱道长
 * 2020/12/25
 * token 生成
 */
public class TokenUtil {


    /**
     * 随机生成token凭证，后期用户要携带这个信息过来
     * @param uid
     * @param expire
     * @param signatureAlgorithm
     * @param jwtKey
     * @return
     */
    public static String token(long uid, long expire,
                               SignatureAlgorithm signatureAlgorithm,
                               String jwtKey) {
        String token  = Jwts.builder()
                .setSubject(String.valueOf(uid))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(signatureAlgorithm,jwtKey)
                .compact();
        return token;
    }

}
