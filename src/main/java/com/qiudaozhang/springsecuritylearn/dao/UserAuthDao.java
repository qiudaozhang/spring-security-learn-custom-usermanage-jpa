package com.qiudaozhang.springsecuritylearn.dao;

import com.qiudaozhang.springsecuritylearn.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author 邱道长
 * 2020/12/24
 */
public interface UserAuthDao  extends JpaRepository<UserAuth,Long> {

    Optional<UserAuth> findUserAuthByUsername(String username);

    Optional<UserAuth> findUserAuthByPhone(String phone);

    int  deleteUserAuthByUsername(String username);


}
