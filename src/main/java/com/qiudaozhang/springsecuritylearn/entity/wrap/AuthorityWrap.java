package com.qiudaozhang.springsecuritylearn.entity.wrap;

import com.qiudaozhang.springsecuritylearn.entity.Authority;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 邱道长
 * 2020/12/25
 */
public class AuthorityWrap implements GrantedAuthority {

    private Authority authority;

    @Override
    public String getAuthority() {
        return authority.getAuthority();
    }
}
