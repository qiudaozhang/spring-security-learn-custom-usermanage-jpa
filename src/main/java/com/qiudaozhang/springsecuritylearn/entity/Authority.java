package com.qiudaozhang.springsecuritylearn.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * @author 邱道长
 * 2020/12/24
 */
@Entity
@Table(name = "authority")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    private String description;

    private String uri;

    @ManyToMany
    @JoinTable(name = "user_auth_authority",
            joinColumns = {
                    @JoinColumn(
                            name = "authority_id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_auth_id")
            })
    private List<UserAuth> userAuth;


}
