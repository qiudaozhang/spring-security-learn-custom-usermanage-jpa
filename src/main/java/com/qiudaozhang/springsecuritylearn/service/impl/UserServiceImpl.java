package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.commom.SequenceGenerator;
import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.entity.Authority;
import com.qiudaozhang.springsecuritylearn.entity.UserAuth;
import com.qiudaozhang.springsecuritylearn.entity.wrap.UserDetailsWrap;
import com.qiudaozhang.springsecuritylearn.req.Loginup;
import com.qiudaozhang.springsecuritylearn.req.PwdChangeReq;
import com.qiudaozhang.springsecuritylearn.req.UserReq;
import com.qiudaozhang.springsecuritylearn.service.AuthorityService;
import com.qiudaozhang.springsecuritylearn.service.UserService;
import com.qiudaozhang.springsecuritylearn.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邱道长
 * 2020/12/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {


    @Resource
    private UserDetailsManager udm;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthorityService authorityService;

    @Override
    public void createUser(UserReq req) {

        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        long id = sequenceGenerator.nextId();
        UserDetailsWrap.UserDetailsWrapBuilder builder = UserDetailsWrap.builder();
        UserAuth.UserAuthBuilder uab = UserAuth.builder();
        UserAuth ua = uab.username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .uid(id)
                .build();


        // 需要查询角色
        List<Authority> authorities = authorityService.findByIds(req.getAuthorities());
        ua.setAuthorities(authorities);
        builder.userAuth(ua);
        UserDetailsWrap udw = builder.build();
        udm.createUser(udw);
    }

    @Override
    public ServerResponse<UserVo> find(String username) {

        boolean b = udm.userExists(username);
        if(b) {
            UserDetails u = udm.loadUserByUsername(username);
            UserVo.UserVoBuilder builder = UserVo.builder();

            builder.username(u.getUsername())
                    .password(u.getPassword());
            List<String> authorities = u.getAuthorities().stream().map(c -> c.getAuthority()).collect(Collectors.toList());
            builder.authorities(authorities);
            return ServerResponse.success(builder.build());
        } else {
            return ServerResponse.error("用户不存在！");
        }
    }

    @Override
    public ServerResponse changePassword(PwdChangeReq req) {
        try {
            udm.changePassword(req.getOldPassword(),req.getNewPassword());
            return ServerResponse.success();
        } catch (Exception e) {
            return ServerResponse.error("error");
        }

    }

    @Override
    public ServerResponse delete(String username) {
        boolean b = udm.userExists(username);
        if(b) {
            udm.deleteUser(username);
            return ServerResponse.success();
        } else {
            return ServerResponse.error("用户不存在！");
        }

    }

    @Override
    public ServerResponse login(Loginup req) {
        UserDetails ud = udm.loadUserByUsername(req.getUsername());
        if(ud == null) {
            return ServerResponse.error("用户名或密码错误！");
        } else {
            if(passwordEncoder.matches(req.getPassword(),ud.getPassword())) {
                // 返回一个token

                return ServerResponse.success();
            }
        }
        return null;
    }
}
