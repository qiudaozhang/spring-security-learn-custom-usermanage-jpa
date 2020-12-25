package com.qiudaozhang.springsecuritylearn.service.impl;

import com.qiudaozhang.springsecuritylearn.dao.AuthorityDao;
import com.qiudaozhang.springsecuritylearn.entity.Authority;
import com.qiudaozhang.springsecuritylearn.service.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityDao authorityDao;

    @Override
    public List<Authority> findByIds(List<Long> ids) {
        return authorityDao.findAuthoritiesByIdIn(ids);
    }
}
