package com.qiudaozhang.springsecuritylearn.security.provider;

import com.qiudaozhang.springsecuritylearn.security.authentications.PasswordAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Component
public class UsernamePasswordProvider implements AuthenticationProvider {


    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("进来...");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails ud = userDetailsService.loadUserByUsername(username);
        if(ud == null) {
            throw new RuntimeException("用户名或密码错误！");
        } else {
            if(passwordEncoder.matches(password,ud.getPassword())) {
                UsernamePasswordAuthenticationToken token = new
                        UsernamePasswordAuthenticationToken(ud.getUsername(),ud.getPassword(),ud.getAuthorities());
                return token;
            } else {
                throw new RuntimeException("用户名或密码错误！");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
