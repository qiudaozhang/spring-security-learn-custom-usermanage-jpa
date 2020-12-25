package com.qiudaozhang.springsecuritylearn.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author 邱道长
 * 2020/12/24
 */
@Entity
@Table(name = "user_auth")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长
    private Long id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // 用急切加载
    @JoinTable(name = "user_auth_authority",
            joinColumns = {
                    @JoinColumn(name = "user_auth_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id")
            }
    )
    private List<Authority> authorities;

}
