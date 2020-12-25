package com.qiudaozhang.springsecuritylearn.config;

import com.qiudaozhang.springsecuritylearn.security.filter.TokenFilter;
import com.qiudaozhang.springsecuritylearn.security.provider.SmsProvider;
import com.qiudaozhang.springsecuritylearn.security.provider.TokenProvider;
import com.qiudaozhang.springsecuritylearn.security.provider.UsernamePasswordProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author 邱道长
 * 2020/12/24
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Resource
//    private DataSource dataSource;

    @Resource
    private UsernamePasswordProvider passwordProvider;
    @Resource
    private SmsProvider smsProvider;
    @Resource
    private TokenProvider tokenProvider;


    // filter
    @Resource
    private TokenFilter tokenFilter;


    // 定义一个密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 重写该方法之后必须指定认证方式，否则请求的时候 -u user:pwd 提交了数据也无法知道用户是谁
        http.addFilterAfter(tokenFilter,BasicAuthenticationFilter.class);
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(passwordProvider);
//        auth.authenticationProvider(smsProvider);
        auth.authenticationProvider(tokenProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        System.out.println(authenticationManager);
        return authenticationManager;
    }
}
