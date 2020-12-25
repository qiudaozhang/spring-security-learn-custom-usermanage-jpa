package com.qiudaozhang.springsecuritylearn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 邱道长
 * 2020/12/25
 * 手机短信验证码
 */
@Entity
@Setter
@Getter
public class Sms implements Serializable {


    private static final long serialVersionUID = 6377133793561661448L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String phone;

    private String code;

    // 用途
    private Integer purpose;

    // 0 可用 非 0不可用
    private Integer status;
}
