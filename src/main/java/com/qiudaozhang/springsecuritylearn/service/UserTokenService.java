package com.qiudaozhang.springsecuritylearn.service;

import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;

/**
 * @author 邱道长
 * 2020/12/25
 */
public interface UserTokenService {


    ServerResponse currentUid();

    /*
             String token = TokenUtil.token
                        (uid,
                        TimeUtil.ONE_DAY_SECONDS,
                                SignatureAlgorithm.HS512,
                                jwtKey);
     */
    String token(long uid);

    boolean checkAccessToken(String token);

//    void save(String key,String value);
}
