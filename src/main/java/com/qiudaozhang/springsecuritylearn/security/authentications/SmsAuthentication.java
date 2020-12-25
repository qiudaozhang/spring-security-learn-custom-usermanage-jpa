package com.qiudaozhang.springsecuritylearn.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author 邱道长
 * 2020/12/25
 * 短信验证方式
 */
public class SmsAuthentication  extends UsernamePasswordAuthenticationToken {


    public SmsAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SmsAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
