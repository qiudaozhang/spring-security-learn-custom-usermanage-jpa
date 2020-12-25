package com.qiudaozhang.springsecuritylearn.security.provider;

import com.qiudaozhang.springsecuritylearn.security.authentications.TokenAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Component
public class TokenProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("TokenProvider 1 ");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentication.class);
    }
}
