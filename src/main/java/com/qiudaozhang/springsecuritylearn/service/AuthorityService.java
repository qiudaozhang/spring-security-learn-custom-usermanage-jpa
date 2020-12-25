package com.qiudaozhang.springsecuritylearn.service;

import com.qiudaozhang.springsecuritylearn.entity.Authority;

import java.util.List;

/**
 * @author 邱道长
 * 2020/12/25
 */
public interface AuthorityService {


    List<Authority> findByIds(List<Long> ids);
}
