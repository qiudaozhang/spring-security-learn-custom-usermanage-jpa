package com.qiudaozhang.springsecuritylearn.security.filter;

import com.qiudaozhang.springsecuritylearn.security.authentications.SmsAuthentication;
import com.qiudaozhang.springsecuritylearn.security.authentications.TokenAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

//    @Resource
//    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if(!StringUtils.hasLength(token)  ) {
            System.out.println("非法凭证！");
        } else {
            // 这是基于手机号和验证码请求
            System.out.println(".... ");
//            Authentication authentication = new SmsAuthentication(phone,code);
//            // 校验
//            Authentication authenticate = authenticationManager.authenticate(authentication);
            Authentication a = new TokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(a);
        }
        filterChain.doFilter(request,response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
