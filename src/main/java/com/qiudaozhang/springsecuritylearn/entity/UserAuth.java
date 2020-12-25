package com.qiudaozhang.springsecuritylearn.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class UserAuth implements Serializable {

    private static final long serialVersionUID = -3807834420333342002L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长
    private Long id;


    private Long uid;// 分配一个统一的UID，方便后续所有和用户有关的数据进行关联

    private String username;

    private String password;

    private String phone;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // 用急切加载
    @JoinTable(name = "user_auth_authority",
            joinColumns = {
                    @JoinColumn(name = "user_auth_id",
                    referencedColumnName = "uid") // 指定引用的列，这样不会使用主键
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id")
            }
    )
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<Authority> authorities;

}
