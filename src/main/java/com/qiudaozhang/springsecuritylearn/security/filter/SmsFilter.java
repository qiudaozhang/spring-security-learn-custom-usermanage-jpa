package com.qiudaozhang.springsecuritylearn.security.filter;

import com.qiudaozhang.springsecuritylearn.config.pojo.IgnoreUri;
import com.qiudaozhang.springsecuritylearn.security.authentications.SmsAuthentication;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class SmsFilter extends OncePerRequestFilter {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private IgnoreUri ignoreUri;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String code = request.getParameter("code");


        if(!StringUtils.hasLength(phone) || StringUtils.hasLength(code)) {
            System.out.println("非手机号登录");
        } else {
            // 这是基于手机号和验证码请求
            Authentication authentication = new SmsAuthentication(phone,code);
            // 校验
            Authentication authenticate = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getServletPath();
        boolean b = ignoreUri.getUri().stream().anyMatch(c -> c.equals(uri));
        return b;
    }
}
