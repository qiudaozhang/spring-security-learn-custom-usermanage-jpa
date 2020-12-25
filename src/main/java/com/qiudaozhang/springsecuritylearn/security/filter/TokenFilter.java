package com.qiudaozhang.springsecuritylearn.security.filter;

import com.qiudaozhang.springsecuritylearn.config.pojo.IgnoreUri;
import com.qiudaozhang.springsecuritylearn.security.authentications.SmsAuthentication;
import com.qiudaozhang.springsecuritylearn.security.authentications.TokenAuthentication;
import com.qiudaozhang.springsecuritylearn.service.UserTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import java.nio.charset.StandardCharsets;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    //    @Resource
//    private AuthenticationManager authenticationManager;
    @Resource
    private IgnoreUri ignoreUri;

    @Resource
    private UserTokenService userTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (!StringUtils.hasLength(token)) {
            throw new BadCredentialsException("bad");
        } else {
            // 需要校验这个token是否合法

            boolean b = userTokenService.checkAccessToken(token);
            if(!b) {
                throw new BadCredentialsException("bad");
            }
            Authentication a = new TokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(a);
        }
        filterChain.doFilter(request, response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getServletPath();
        boolean b = ignoreUri.getUri().stream().anyMatch(c -> c.equals(uri));
        return b;
    }
}
