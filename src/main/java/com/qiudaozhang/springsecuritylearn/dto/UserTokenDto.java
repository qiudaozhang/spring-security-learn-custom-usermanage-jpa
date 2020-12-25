package com.qiudaozhang.springsecuritylearn.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Setter
@Getter
public class UserTokenDto implements Serializable {

    private static final long serialVersionUID = 2149547798168791382L;
    private Long uid;
}
