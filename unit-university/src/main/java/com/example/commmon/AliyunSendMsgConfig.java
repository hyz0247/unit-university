package com.example.commmon;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 发送短信配置
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun.send-msg")
@Data
public class AliyunSendMsgConfig {
    private String accessKeyId;

    private String regionId;

    private  String accessKeySecret;
    ///短信签名名称
    private String SignName;
    //短信模板ID
    private String TemplateCode;
}
