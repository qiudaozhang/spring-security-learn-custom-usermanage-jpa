package com.qiudaozhang.springsecuritylearn.security.filter;

import com.qiudaozhang.springsecuritylearn.config.pojo.IgnoreUri;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author 邱道长
 * 2020/12/25
 */

@Component
@Setter
@Getter
public class UsernamePasswordFilter extends OncePerRequestFilter {

    @Resource
    private IgnoreUri ignoreUri;

//    @Autowired
//    // 它使用用户名和密码认证管理器
//    @Qualifier(value = "usernamePasswordUserDetailsManager")
//    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println(request.getHeader("username"));
        System.out.println(request.getHeader("password"));
        System.out.println(headerNames);
        filterChain.doFilter(request,response);



    }


    // 不应该被过滤的
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getServletPath();
        System.out.println("ignore uri : " + ignoreUri );
        boolean b = ignoreUri.getUri().stream().anyMatch(c -> c.equals(uri));
        return b;
//        return request.getServletPath().equals("/login");
    }
}
