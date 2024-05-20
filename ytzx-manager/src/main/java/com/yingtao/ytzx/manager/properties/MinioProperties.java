package com.yingtao.ytzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Adam
 * @create 2024-04-16 19:17
 */
@Data
@ConfigurationProperties(prefix = "ytzx.minio")
public class MinioProperties {

    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
