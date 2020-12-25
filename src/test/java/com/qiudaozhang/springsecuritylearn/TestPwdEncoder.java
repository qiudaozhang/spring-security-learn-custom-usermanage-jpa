package com.qiudaozhang.springsecuritylearn;

import com.qiudaozhang.springsecuritylearn.commom.SequenceGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 邱道长
 * 2020/12/25
 */
public class TestPwdEncoder {

    @Test
    public void m1() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        System.out.println(pwd);
        System.out.println(encoder.matches("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",pwd));
    }
}
