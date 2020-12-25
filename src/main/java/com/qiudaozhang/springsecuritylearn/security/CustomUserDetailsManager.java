package com.qiudaozhang.springsecuritylearn.security;

import com.qiudaozhang.springsecuritylearn.dao.UserAuthDao;
import com.qiudaozhang.springsecuritylearn.entity.UserAuth;
import com.qiudaozhang.springsecuritylearn.entity.wrap.UserDetailsWrap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 邱道长
 * 2020/12/24
 * 自定义用户管理
 */
@Component(value = "customUserDetailsManager")
public class CustomUserDetailsManager implements UserDetailsManager {
//
//    @Resource
//    private Authentication authentication;

    @Resource
    private UserAuthDao userAuthDao;

    @Override
    public void createUser(UserDetails user) {
        if(userExists(user.getUsername())) {
            throw new RuntimeException("user has exists!");
        }

        if(user instanceof UserDetailsWrap) {
            UserDetailsWrap udw = (UserDetailsWrap) user;
            userAuthDao.save(udw.getUserAuth());
        }
//        List<String> collect = user.getAuthorities().stream().map(c -> c.getAuthority()).collect(Collectors.toList());
//        UserAuth.UserAuthBuilder builder = UserAuth.builder();
//        UserAuth ua = builder.username(user.getUsername())
//                .password(user.getPassword())
//                .build();

    }

    @Override
    public void updateUser(UserDetails user) {

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        if(userDetails != null) {
            UserAuth.UserAuthBuilder builder = UserAuth.builder();
            UserAuth ua = builder
                    .password(user.getPassword())
                    .build();
            userAuthDao.save(ua);
        }
    }

    @Override
    public void deleteUser(String username) {
        userAuthDao.deleteUserAuthByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
            Optional<UserAuth> op = userAuthDao.findUserAuthByUsername(name);
        if(op.isPresent()) {
            UserAuth userAuth = op.get();
            if(oldPassword.equals(userAuth.getPassword())) {
                userAuth.setPassword(newPassword);
                userAuthDao.save(userAuth);
            } else {
                throw new RuntimeException("原始密码错误，非法修改！");
            }
        } else {
            throw new RuntimeException("非法访问！");

        }

    }

    @Override
    public boolean userExists(String username) {
        Optional<UserAuth> op = userAuthDao.findUserAuthByUsername(username);
        if(op.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuth> op = userAuthDao.findUserAuthByUsername(username);
        if(op.isPresent()) {
            UserAuth u = op.get();
            List<String> collect = u.getAuthorities().stream().map(c -> c.getAuthority()).collect(Collectors.toList());

            String[] strs = collect.toArray(new String[0]);
            UserDetails userDetails = User.withUsername(u.getUsername())
                    .password(u.getPassword())
                    .authorities(strs)
                    .build();
            return userDetails;
        }
        return null;
    }

}
