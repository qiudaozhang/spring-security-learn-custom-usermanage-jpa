package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.commom.SequenceGenerator;
import com.qiudaozhang.springsecuritylearn.commom.ServerResponse;
import com.qiudaozhang.springsecuritylearn.consts.RedisKeys;
import com.qiudaozhang.springsecuritylearn.dao.UserAuthDao;
import com.qiudaozhang.springsecuritylearn.dto.UserTokenDto;
import com.qiudaozhang.springsecuritylearn.entity.Authority;
import com.qiudaozhang.springsecuritylearn.entity.UserAuth;
import com.qiudaozhang.springsecuritylearn.entity.wrap.UserDetailsWrap;
import com.qiudaozhang.springsecuritylearn.req.LoginPhoneCode;
import com.qiudaozhang.springsecuritylearn.req.Loginup;
import com.qiudaozhang.springsecuritylearn.req.PwdChangeReq;
import com.qiudaozhang.springsecuritylearn.req.UserReq;
import com.qiudaozhang.springsecuritylearn.service.AuthorityService;
import com.qiudaozhang.springsecuritylearn.service.UserService;
import com.qiudaozhang.springsecuritylearn.service.UserTokenService;
import com.qiudaozhang.springsecuritylearn.util.TimeUtil;
import com.qiudaozhang.springsecuritylearn.vo.UserVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
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
    private UserTokenService userTokenService;

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
                String token = userTokenService.token(uid);
                loginTokenHandle(uid, token);
                return ServerResponse.success(token);
            }
        }
        return ServerResponse.error("用户名或密码错误！");
    }

    private void loginTokenHandle(Long uid, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUid(uid);
        deleteLastToken(uid);
        redisTemplate.opsForValue().set("USER_TOKEN:" + token,
                dto,
                TimeUtil.ONE_DAY_SECONDS,
                TimeUnit.MILLISECONDS);
        // 存储新映射
        redisTemplate.opsForValue().set("UID:"+ uid,"USER_TOKEN:" + token,TimeUtil.ONE_DAY_SECONDS,TimeUnit.SECONDS);
    }

    @Override
    public ServerResponse login(LoginPhoneCode req) {
        System.out.println("手机号和验证码登录");
//
        boolean b = checkLoginCode(req);
        if(!b)  {
            return ServerResponse.error("验证码错误或已过期!");
        }
        // 如果OK，完成登录
        // 根据 手机号查询用户
        // todo
        Optional<UserAuth> op = userAuthDao.findUserAuthByPhone(req.getPhone());

        if(op.isPresent()) {
            // 已经存在用户
            UserAuth u = op.get();
            Long uid = u.getUid();
            String token = userTokenService.token(uid);
            loginTokenHandle(uid,token);
            // 删除登录验证码
            redisTemplate.delete(RedisKeys.PHONE_LOGIN_CODE + req.getPhone());
            return ServerResponse.success(token);

        } else {
            // 未注册的用户 todo
        }

        return ServerResponse.error("未知错误！");
    }

    private boolean checkLoginCode(LoginPhoneCode req) {
        String phone = req.getPhone();
        String key = RedisKeys.PHONE_LOGIN_CODE + phone;
        Object o = redisTemplate.opsForValue().get(key);
        if(o == null) {
            return false;
        }
        if(req.getCode().equals(o.toString())) {
            return true;
        }
        return false;
    }

    private void deleteLastToken(Long uid) {
        String key = "UID:" + uid;
        Object o = redisTemplate.opsForValue().get(key);
        if(o != null) {
            redisTemplate.delete(o.toString());
        }

    }
}
