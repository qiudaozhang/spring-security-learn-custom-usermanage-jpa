package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.commom.SequenceGenerator;
import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.dao.UserAuthDao;
import com.qiudaozhang.springsecuritylearn.dto.UserTokenDto;
import com.qiudaozhang.springsecuritylearn.entity.Authority;
import com.qiudaozhang.springsecuritylearn.entity.UserAuth;
import com.qiudaozhang.springsecuritylearn.entity.wrap.UserDetailsWrap;
import com.qiudaozhang.springsecuritylearn.req.Loginup;
import com.qiudaozhang.springsecuritylearn.req.PwdChangeReq;
import com.qiudaozhang.springsecuritylearn.req.UserReq;
import com.qiudaozhang.springsecuritylearn.service.AuthorityService;
import com.qiudaozhang.springsecuritylearn.service.UserService;
import com.qiudaozhang.springsecuritylearn.util.TimeUtil;
import com.qiudaozhang.springsecuritylearn.util.TokenUtil;
import com.qiudaozhang.springsecuritylearn.vo.UserVo;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 邱道长
 * 2020/12/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {


    @Value("${jwt.key}")
    private String jwtKey;

    @Resource
    private UserDetailsManager udm;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthorityService authorityService;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void createUser(UserReq req) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        System.out.println("名称：" + name);
        if(!name.equalsIgnoreCase("admin")) {
            throw new RuntimeException("非管理者，无法使用！");
        }
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
    public ServerResponse login( Loginup req) {
        UserDetails ud = udm.loadUserByUsername(req.getUsername());
        if(ud == null) {
            return ServerResponse.error("用户名或密码错误！");
        } else {
            if(passwordEncoder.matches(req.getPassword(),ud.getPassword())) {
                // 得到它的UID

                // 返回一个token todo
                Long uid = userAuthDao.findUserAuthByUsername(req.getUsername()).get().getUid();
                String token = TokenUtil.token
                        (uid,
                        TimeUtil.ONE_DAY_SECONDS,
                                SignatureAlgorithm.HS512,
                                jwtKey);
                UserTokenDto dto = new UserTokenDto();
                dto.setUid(uid);
                redisTemplate.opsForValue().set("USER_TOKEN:" + token,
                        dto,
                        TimeUtil.ONE_DAY_SECONDS,
                        TimeUnit.MILLISECONDS);

                return ServerResponse.success(token);
            }
        }
        return ServerResponse.error("用户名或密码错误！");
    }
}
