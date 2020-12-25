package com.qiudaozhang.springsecuritylearn;

import com.qiudaozhang.springsecuritylearn.commom.SequenceGenerator;
import com.qiudaozhang.springsecuritylearn.util.TokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

/**
 * @author 邱道长
 * 2020/12/25
 */
public class TestToken {

    @Test
    public void m1() {
        String token = TokenUtil.token(791915576020873216L, 86400, SignatureAlgorithm.HS512,
                "pKUdmdtI6Sh3JUwr7fbX1Bku1EjEFqFgnic8jPTVWUejAu0fXAv0SL5zvjOI");

        System.out.println(token);
    }
}
