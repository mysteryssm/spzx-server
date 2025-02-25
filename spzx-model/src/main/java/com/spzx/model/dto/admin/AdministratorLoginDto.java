package com.spzx.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录请求参数")
public class AdministratorLoginDto {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(description = "验证码key")
    private String codeKey;

}
