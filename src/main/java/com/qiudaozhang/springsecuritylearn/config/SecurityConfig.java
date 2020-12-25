package com.qiudaozhang.springsecuritylearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 邱道长
 * 2020/12/24
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Resource
//    private DataSource dataSource;




    // 定义一个密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 重写该方法之后必须指定认证方式，否则请求的时候 -u user:pwd 提交了数据也无法知道用户是谁
        http.httpBasic();
    }
}
