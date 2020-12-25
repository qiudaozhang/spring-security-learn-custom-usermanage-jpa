package com.qiudaozhang.springsecuritylearn.dao;

import com.qiudaozhang.springsecuritylearn.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author 邱道长
 * 2020/12/25
 */
public interface SmsDao extends JpaRepository<Sms,Integer> {

    Optional<Sms> findSmsByPhoneAndCodeAndPurposeAndStatus(
            String phone,
            String code,
            Integer purpose,
            Integer status
    );
}
