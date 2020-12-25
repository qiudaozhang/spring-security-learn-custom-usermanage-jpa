package com.qiudaozhang.springsecuritylearn.dao;

import com.qiudaozhang.springsecuritylearn.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 邱道长
 * 2020/12/25
 */
public interface AuthorityDao extends JpaRepository<Authority,Long> {

    List<Authority> findAuthoritiesByIdIn(List<Long> ids);
}
