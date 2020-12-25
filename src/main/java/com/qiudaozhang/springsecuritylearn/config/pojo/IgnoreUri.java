package com.qiudaozhang.springsecuritylearn.config.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 邱道长
 * 2020/12/25
 */
@Component
@ConfigurationProperties(value = "ignore")
@Getter
@Setter
public class IgnoreUri {

    private List<String> uri;
}
