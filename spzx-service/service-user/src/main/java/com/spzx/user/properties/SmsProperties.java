package com.spzx.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: SMSProperties
 * @author: yck
 * @create: 2024-03-13
 */

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    private String host;
    private String path;
    private String method;
    private String appcode;
    private String smsSignId;
    private String templateId;
}
