package com.qiudaozhang.springsecuritylearn.security;

import com.qiudaozhang.springsecuritylearn.dao.UserAuthDao;
import com.qiudaozhang.springsecuritylearn.entity.UserAuth;
import com.qiudaozhang.springsecuritylearn.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

///**
// * @author 邱道长
// * 2020/12/25
// */
//@Service
public class JpaUserDetailsService  implements UserDetailsService {
    
//    @Resource
    private UserAuthDao userAuthDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuth> op = userAuthDao.findUserAuthByUsername(username);
        UserAuth ua = op.orElseThrow(() -> new UsernameNotFoundException("User not exists!"));
        return new SecurityUser(ua);
    }
}
