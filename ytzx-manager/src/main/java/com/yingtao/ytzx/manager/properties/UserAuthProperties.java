package com.yingtao.ytzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-14 20:22
 */
@Data
@ConfigurationProperties(prefix = "ytzx.auth")
public class UserAuthProperties {
    private List<String> noAuthUrls;
}
