package com.qiudaozhang.springsecuritylearn.security.provider;

import com.qiudaozhang.springsecuritylearn.dao.SmsDao;
import com.qiudaozhang.springsecuritylearn.entity.Sms;
import com.qiudaozhang.springsecuritylearn.security.authentications.SmsAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 邱道长
 * 2020/12/25
 * 短信验证提供者
 */
@Component
public class SmsProvider implements AuthenticationProvider {

    @Resource
    private SmsDao smsDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 拿到手机号
        String phone = authentication.getName();
        String code = authentication.getCredentials().toString();
        // purpose 1 为登录验证码
        Optional<Sms> op = smsDao.findSmsByPhoneAndCodeAndPurposeAndStatus(phone, code, 1, 0);
        //  根据手机号去查询，
        Sms sms = op.orElseThrow(() -> new BadCredentialsException("手机号或验证码错误！"));
        return new SmsAuthentication(sms.getPhone(),sms.getCode());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(SmsAuthentication.class);
    }
}
