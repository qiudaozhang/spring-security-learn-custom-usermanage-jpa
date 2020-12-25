package com.qiudaozhang.springsecuritylearn.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author 邱道长
 * 2020/12/25
 * 密码验证方式
 */
public class PasswordAuthentication extends UsernamePasswordAuthenticationToken {


    public PasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
